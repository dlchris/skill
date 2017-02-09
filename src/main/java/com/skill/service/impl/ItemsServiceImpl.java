package com.skill.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skill.dao.ItemsCustomDao;
import com.skill.dao.ItemsDao;
import com.skill.entity.Items;
import com.skill.entity.ItemsCustom;
import com.skill.entity.ItemsQueryVo;
import com.skill.exception.CustomException;
import com.skill.service.ItemsService;

/**
 * Created by codingBoy on 16/11/15.
 */
@Service
public class ItemsServiceImpl implements ItemsService {

	// ע��mapper
	@Autowired
	private ItemsCustomDao itemsMapperCustom;

	@Autowired
	private ItemsDao itemsMapper;

	// ��Ʒ�Ĳ�ѯ�б�
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {

		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(int id) throws Exception {

		Items items = itemsMapper.selectByPrimaryKey(id);

		// �����ѯ����Ʒ��ϢΪ�գ��׳�ϵͳ�Զ�����쳣
		if (items == null) {
			throw new CustomException("�޸���Ʒ��Ϣ������");
		}

		// �������Ժ���������ı仯����Ҫ��ѯ��Ʒ�����������Ϣ�����ص�controller
		// �������ʱ���õ���չ����ã�����
		ItemsCustom itemsCustom = new ItemsCustom();
		// ��items�����Կ�����itemsCustom
		BeanUtils.copyProperties(items, itemsCustom);

		return itemsCustom;
	}

	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {

		// ��service��һ��Ҫдҵ�����

		// ���ڹؼ�ҵ�����ݵķǿ�У��
		if (id == null) {
			// �׳��쳣����ʾ���ýӿڵ��û���id����Ϊ��
			// ...
		}

		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

	@Override
	public void deleteItems(Integer id) throws Exception {
		// ���ڼ���ɾ��
		// ��������ݿ���������⽨���ڳ�����ɾ�������¼���ӱ������ݿ�mysql�Զ�ɾ����
		// �˷���������ʹ�ã�������ҵ���߼�������serviceά��

		// ͨ���±߳������ɾ��
		// ��ɾ��id�������ӱ������(��Ʒ�����������ӱ�ȫ��ɾ��)
		// ��ɾ������(��Ʒ��)
	}
}
