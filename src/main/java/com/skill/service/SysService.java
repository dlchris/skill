package com.skill.service;

import java.util.List;

import com.skill.entity.ActiveUser;
import com.skill.entity.SysPermission;
import com.skill.entity.SysUser;
/**
 * 认证授权服务接口
 */
public interface SysService {
	//根据用户的身份和密码进行认证，如果认证通过，返回用户身份信息
    ActiveUser authenticat(String userCode,String password) throws Exception;


    //根据用户账号查询用户信息
    SysUser findSysUserByUserCode(String userCode) throws Exception;


    //根据用户id查询权限范围的菜单
    List<SysPermission> findMenuListByUserId(String userId) throws Exception;

    //根据用户的id查询权限范围的url
    List<SysPermission> findPermissionListByUserId(String userId) throws Exception;
}
