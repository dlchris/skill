package com.skill.entity;

import java.util.List;

/**
 * 
 * <p>Title: ItemsQueryVo</p>
 * <p>Description: ��Ʒ�İ�װ��</p>
 * <p>Company: www.itcast.com</p> 
 * @author	����.����
 * @date	2015-3-20����2:52:00
 * @version 1.0
 */
public class ItemsQueryVo {
	
	//��Ʒ��Ϣ
	private ItemsCustom itemsCustom;

	//����һ��list
	private List<ItemsCustom> itemsList;

	public List<ItemsCustom> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsCustom> itemsList) {
		this.itemsList = itemsList;
	}

	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}

	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}
}