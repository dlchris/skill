package com.skill.service;

import java.util.List;

import com.skill.entity.ItemsCustom;
import com.skill.entity.ItemsQueryVo;

/**
 * ��Ʒ��service�ӿ�
 */

public interface ItemsService {

    //��Ʒɾ����Ϣ
    void deleteItems(Integer id) throws Exception;

    //��Ʒ�Ĳ�ѯ�б�
    List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
            throws Exception;

    //������Ʒid��ѯ��Ʒ��Ϣ
    ItemsCustom findItemsById(int id) throws Exception;

    //������Ʒ��Ϣ
    /**
     * ����service�ӿڣ���ѭ��һְ�𣬽�ҵ�����ϸ��(��Ҫʹ�ð�װ���ͣ�����map)
     * @param id �޸���Ʒ��id
     * @param itemsCustom �޸���Ʒ����Ϣ
     * @throws Exception
     */
    void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception;

}
