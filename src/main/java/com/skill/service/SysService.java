package com.skill.service;

import java.util.List;

import com.skill.entity.ActiveUser;
import com.skill.entity.SysPermission;
import com.skill.entity.SysUser;
/**
 * ��֤��Ȩ����ӿ�
 */
public interface SysService {
	//�����û�����ݺ����������֤�������֤ͨ���������û������Ϣ
    ActiveUser authenticat(String userCode,String password) throws Exception;


    //�����û��˺Ų�ѯ�û���Ϣ
    SysUser findSysUserByUserCode(String userCode) throws Exception;


    //�����û�id��ѯȨ�޷�Χ�Ĳ˵�
    List<SysPermission> findMenuListByUserId(String userId) throws Exception;

    //�����û���id��ѯȨ�޷�Χ��url
    List<SysPermission> findPermissionListByUserId(String userId) throws Exception;
}
