package com.skill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skill.entity.Seckill;

/**
 * ����spring��junit���ϣ�����junit������ʱ�ͻ����spring����
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�������ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//ע��Daoʵ��������
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long seckillId=1000;
        Seckill seckill=seckillDao.queryById(seckillId);
        System.out.println("###################################:"+seckill.getName());
        System.out.println("###################################:"+seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills=seckillDao.queryAll(0,100);
        for (Seckill seckill : seckills)
        {
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() throws Exception {
        long seckillId=1000;
        Date date=new Date();
        int updateCount=seckillDao.reduceNumber(seckillId,date);
        System.out.println(updateCount);
    }

    @Test
    public void addNumber() throws Exception {
        Seckill seckill = new Seckill();
        seckill.setName("����");
        seckill.setNumber(100);
        seckill.setEndTime(new Date());
        int insertCount=seckillDao.addNumber(seckill);
        System.out.println(seckill.getSeckillId());
    }

}