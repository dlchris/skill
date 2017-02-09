package com.skill.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skill.entity.ActiveUser;
import com.skill.entity.SysPermission;
import com.skill.entity.SysUser;
import com.skill.service.SysService;
@Service
public class CustomRealm extends AuthorizingRealm {
	// ע��service
	@Autowired
	private SysService sysService;

	// ����realm������
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	// realm����֤�����������ݿ��ѯ�û���Ϣ
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// token���û������
		// ��һ��:��token��ȡ�������Ϣ
		String userCode = (String) token.getPrincipal();

		// �ڶ���:�����û������userCode�����ݿ��ѯ
		SysUser sysUser = null;
		try {
			sysUser = sysService.findSysUserByUserCode(userCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// �ж��Ƿ�����ݿ��в�ѯ���û���Ϣ
		if (sysService == null) {
			return null;
		}

		// �����ݿ��ѯ��������
		String password = sysUser.getPassword();

		// ��salt
		String salt = sysUser.getSalt();
		System.out.println(salt);

		// activeUser�����û��������Ϣ
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());

		// �����û�idȡ���˵�
		// ͨ��serviceȡ���˵�
		List<SysPermission> menus = null;
		try {
			menus = sysService.findMenuListByUserId(sysUser.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ���û��˵����õ�activeUser
		activeUser.setMenus(menus);

		// ����鲻������null��

		// �����ѯ����������֤��ϢAuthenticationInfo

		/// ��activeUser���õ�simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password,
				ByteSource.Util.bytes(salt), this.getName());

		return simpleAuthenticationInfo;
	}

	// ������Ȩ
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// ��principals��ȡ�������Ϣ
		// ��getPrimaryPrincipal��������ֵתΪ��ʵ�������(���ϱߵ�goGetAuthenticationInfo��֤ͨ����䵽SimpleAuthenticationInfo)
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

		// ���������Ϣ��ȡȨ����Ϣ,
		// �����ݿ��л�ȡ���Ķ�̬Ȩ������
		List<SysPermission> permissionList = null;
		try {
			permissionList = sysService.findPermissionListByUserId(activeUser.getUserid());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> permissions = new ArrayList<>();

		if (permissionList != null) {
			for (SysPermission sysPermission : permissionList) {
				// �����ݿ��е�Ȩ�ޱ�ǩ�����뼯��
				permissions.add(sysPermission.getPercode());
			}
		}

		// �鵽Ȩ�����ݣ�������Ȩ��Ϣ(�����ϱߵ�permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// ���ϱ߲�ѯ����Ȩ��Ϣ��䵽simpleAuthorizationInfo������
		simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}

	// �������
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}
