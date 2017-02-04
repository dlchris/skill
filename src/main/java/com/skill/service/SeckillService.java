package com.skill.service;

import java.util.List;

import com.skill.dto.Exposer;
import com.skill.dto.SeckillExecution;
import com.skill.entity.Seckill;
import com.skill.exception.RepeatKillException;
import com.skill.exception.SeckillCloseException;
import com.skill.exception.SeckillException;

/**ҵ��ӿ�:վ��ʹ����(����Ա)�ĽǶ���ƽӿ�
 * ��������:
 * 1.�����������ȣ����������Ҫ�ǳ����
 * 2.������ҪԽ����Խ��
 * 3.��������(return ����һ��Ҫ�Ѻ�/����return�쳣������������쳣)
 */
public interface SeckillService {

    /**
     * ��ѯȫ������ɱ��¼
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     *��ѯ������ɱ��¼
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);


    //�����£�����������Ҫ����Ϊ��һЩ�ӿ�

    /**
     * ����ɱ����ʱ�����ɱ�ӿڵĵ�ַ���������ϵͳʱ�����ɱʱ��
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * ִ����ɱ�������п���ʧ�ܣ��п��ܳɹ�������Ҫ�׳�����������쳣
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return SeckillExecution ��װִ����ɱ��Ľ��:�Ƿ���ɱ�ɹ�
     */
    SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
    
    /**
     * ִ����ɱ���������ô洢����
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return SeckillExecution ��װִ����ɱ��Ľ��:�Ƿ���ɱ�ɹ�
     */
    SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
