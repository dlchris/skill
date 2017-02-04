package com.skill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.skill.entity.Seckill;

public interface SeckillDao {

	/**
     * �����
     * @param seckillId
     * @param killTime
     * @return ���Ӱ������>1����ʾ���¿��ļ�¼����
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

	/**
     * �ӿ��
     * @param seckill
     * @return 
     */
    int addNumber(Seckill seckill);

    /**
     * ����id��ѯ��ɱ����Ʒ��Ϣ
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * ����ƫ������ѯ��ɱ��Ʒ�б�
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * ʹ�ô洢����ִ����ɱ
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);
}