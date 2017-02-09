package com.skill.dao;

import java.util.List;

import com.skill.entity.SysPermission;


/**
 * 
 * <p>Title: SysPermissionMapperCustom</p>
 * @version 1.0
 */
public interface SysPermissionCustomDao {
	public List<SysPermission> findMenuListByUserId(String userid)throws Exception;
	public List<SysPermission> findPermissionListByUserId(String userid)throws Exception;

}
