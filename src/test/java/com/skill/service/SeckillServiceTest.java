package com.skill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skill.dto.Exposer;
import com.skill.dto.SeckillExecution;
import com.skill.entity.Seckill;
import com.skill.exception.RepeatKillException;
import com.skill.exception.SeckillCloseException;

@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring的配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void getSeckillList() throws Exception {
		List<Seckill> seckills = seckillService.getSeckillList();
		System.out.println(seckills);
		logger.info("list={}",seckills);
	}

	@Test
	public void getById() throws Exception {
		long seckillId = 1000;
		Seckill seckill = seckillService.getById(seckillId);
		System.out.println(seckill);
	}

	@Test // 完整逻辑代码测试，注意可重复执行
	public void testSeckillLogic() throws Exception {
		long seckillId = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			System.out.println(exposer);
			long userPhone = 13476191876L;
			String md5 = exposer.getMd5();

			try {
				SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
				System.out.println(seckillExecution);
			} catch (RepeatKillException e) {
				e.printStackTrace();
			} catch (SeckillCloseException e1) {
				e1.printStackTrace();
			}
		} else {
			// 秒杀未开启
			System.out.println(exposer);
		}
	}

	@Test
    public void executeSeckillProcedure() throws Exception {
		long seckillId=1000;
		long phone = 1368011101;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()){
			String md5=exposer.getMd5();
			SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
			logger.info(seckillExecution.getStateInfo());
		}
		
    }
}