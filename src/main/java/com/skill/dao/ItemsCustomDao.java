package com.skill.dao;

import java.util.List;

import com.skill.entity.ItemsCustom;
import com.skill.entity.ItemsQueryVo;


/**
 * <p>
 * Title: ItemsMapperCustom
 * </p>
 */
public interface ItemsCustomDao {
	List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
}
