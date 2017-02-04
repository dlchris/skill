package com.skill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.skill.dao.SeckillDao;
import com.skill.dao.SuccessKilledDao;
import com.skill.dao.cache.RedisDao;
import com.skill.dto.Exposer;
import com.skill.dto.SeckillExecution;
import com.skill.entity.Seckill;
import com.skill.entity.SuccessKilled;
import com.skill.enums.SeckillStatEnum;
import com.skill.exception.RepeatKillException;
import com.skill.exception.SeckillCloseException;
import com.skill.exception.SeckillException;
import com.skill.service.SeckillService;

//@Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {
	// ��־����
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// ����һ�������ַ���(��ɱ�ӿ�)��salt��Ϊ���ұ����û��³����ǵ�md5ֵ��ֵ�������Խ����Խ��
	private final String salt = "shsdssljdd'l.";

	// ע��Service����
	@Autowired // @Resource,@inject
	private SeckillDao seckillDao;

	@Autowired // @Resource
	private SuccessKilledDao successKilledDao;

	@Autowired
	private RedisDao redisDao;
	
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		// �Ż��㣺�����Ż�
		//1������Redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			// 2���������ݿ�
			seckill = seckillDao.queryById(seckillId);
			// ˵���鲻�������ɱ��Ʒ�ļ�¼
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				// 3������redis
				redisDao.putSeckill(seckill);
			}
		}

		// ������ɱδ����
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// ϵͳ��ǰʱ��
		Date nowTime = new Date();
		if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}

		// ��ɱ������������ɱ��Ʒ��id���ø��ӿڼ��ܵ�md5 ������
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/**
	 * ʹ��ע��������񷽷����ŵ�: 
	 * 1.�����ŶӴ��һ��Լ������ȷ��ע���񷽷��ı�̷��
	 * 2.��֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ���������������RPC/HTTP������߰��뵽���񷽷��ⲿ
	 * 3.�������еķ�������Ҫ������ֻ��һ���޸Ĳ�����ֻ��������Ҫ�������
	 */
	// ��ɱ�Ƿ�ɹ����ɹ�:����棬������ϸ��ʧ��:�׳��쳣������ع�
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");// ��ɱ���ݱ���д��
		}
		// ִ����ɱ�߼�:�����+���ӹ�����ϸ
		Date nowTime = new Date();

		try {
			// �����
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				// û�и��¿���¼��˵����ɱ����
				throw new SeckillCloseException("seckill is closed");
			} else {
				// ��������˿�棬��ɱ�ɹ�,������ϸ
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				// ���Ƿ����ϸ���ظ����룬���û��Ƿ��ظ���ɱ
				if (insertCount <= 0) {
					throw new RepeatKillException("seckill repeated");
				} else {
					// ��ɱ�ɹ�,�õ��ɹ��������ϸ��¼,�����سɹ���ɱ����Ϣ
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// ���Ա������쳣ת��Ϊ�������쳣
			throw new SeckillException("seckill inner error :" + e.getMessage());
		}
	}

	@Override
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			return new SeckillExecution(seckillId, SeckillStatEnum.DATE_REWRITE);// ��ɱ���ݱ���д��
		}
		
		Date killTime = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seckillId", seckillId);
		paramMap.put("phone", userPhone);
		paramMap.put("killTime", killTime);
		paramMap.put("result", null);
		try {
			seckillDao.killByProcedure(paramMap);
			// ��ȡresult
			int result = MapUtils.getInteger(paramMap,"result",-2);
			
			if (result == 1) {
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
			} else {
				return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}
}