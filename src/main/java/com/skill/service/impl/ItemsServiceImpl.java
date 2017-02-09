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

	// 注入mapper
	@Autowired
	private ItemsCustomDao itemsMapperCustom;

	@Autowired
	private ItemsDao itemsMapper;

	// 商品的查询列表
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {

		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(int id) throws Exception {

		Items items = itemsMapper.selectByPrimaryKey(id);

		// 如果查询的商品信息为空，抛出系统自定义的异常
		if (items == null) {
			throw new CustomException("修改商品信息不存在");
		}

		// 在这里以后随着需求的变化，需要查询商品的其它相关信息，返回到controller
		// 所以这个时候用到扩展类更好，如下
		ItemsCustom itemsCustom = new ItemsCustom();
		// 将items的属性拷贝到itemsCustom
		BeanUtils.copyProperties(items, itemsCustom);

		return itemsCustom;
	}

	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {

		// 在service中一定要写业务代码

		// 对于关键业务数据的非空校验
		if (id == null) {
			// 抛出异常，提示调用接口的用户，id不能为空
			// ...
		}

		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

	@Override
	public void deleteItems(Integer id) throws Exception {
		// 关于级联删除
		// 如果在数据库表中配置外建，在程序中删除父表记录，子表由数据库mysql自动删除，
		// 此方法不建议使用，尽量将业务逻辑嗲吗都在service维护

		// 通过下边程序进行删除
		// 先删除id关联的子表的数据(商品关联的所以子表全部删除)
		// 再删除父表(商品表)
	}
}
