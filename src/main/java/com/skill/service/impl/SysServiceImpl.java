package com.skill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skill.dao.SysPermissionCustomDao;
import com.skill.dao.SysUserDao;
import com.skill.entity.ActiveUser;
import com.skill.entity.SysPermission;
import com.skill.entity.SysUser;
import com.skill.entity.SysUserExample;
import com.skill.exception.CustomException;
import com.skill.service.SysService;
import com.skill.util.MD5;
@Service
public class SysServiceImpl implements SysService {
	//@Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysPermissionCustomDao sysPermissionMapperCustom;

    @Override
    public ActiveUser authenticat(String userCode, String password) throws Exception {

        /**
         * 认证过程：
         * 根据用户身份（账号）查询数据库，如果查询不到用户不存在
         * 对输入的密码 和数据库密码 进行比对，如果一致，认证通过
         */
        SysUser sysUser=this.findSysUserByUserCode(userCode);

		if (sysUser == null) {
			// 抛出异常
			throw new CustomException("用户帐号不存在");
		}

        //数据库密码(md5加密)
        String password_db=sysUser.getPassword();

        //对输入的密码和数据库密码进行对比，如果一致则认证通过
        //对页面输入的密码进行md5加密
        String password_input_md5=new MD5().getMD5ofStr(password);
		if (!password_input_md5.equalsIgnoreCase(password_db)) {
			// 抛出异常
			throw new CustomException("用户名或密码错误");
		}

        //得到用户id
        String userId=sysUser.getId();

        //根据用户id查询菜单
        List<SysPermission> menus=this.findMenuListByUserId(userId);

        //根据用户id查询权限url
        List<SysPermission> permissions=this.findPermissionListByUserId(userId);

        //认证通过，返回用户信息
        ActiveUser activeUser=new ActiveUser();
        activeUser.setUserid(sysUser.getId());
        activeUser.setUsercode(userCode);
        activeUser.setUsername(sysUser.getUsername());

        //放入权限范围的菜单和url
        activeUser.setMenus(menus);
        activeUser.setPermissions(permissions);


        return activeUser;
    }

    //根据用户账号查询用户信息
    public SysUser findSysUserByUserCode(String userCode) throws Exception{
        SysUserExample sysUserExample=new SysUserExample();
        SysUserExample.Criteria criteria=sysUserExample.createCriteria();
        criteria.andUsercodeEqualTo(userCode);

        List<SysUser> list=sysUserDao.selectByExample(sysUserExample);

		if (list != null && list.size() == 1) {
			return list.get(0);
		}

        return null;
    }

    @Override
    public List<SysPermission> findMenuListByUserId(String userId) throws Exception {
        return sysPermissionMapperCustom.findMenuListByUserId(userId);
    }

    @Override
    public List<SysPermission> findPermissionListByUserId(String userId) throws Exception {
        return sysPermissionMapperCustom.findPermissionListByUserId(userId);
    }
}