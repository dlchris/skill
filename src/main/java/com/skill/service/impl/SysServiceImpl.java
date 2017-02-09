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
         * ��֤���̣�
         * �����û���ݣ��˺ţ���ѯ���ݿ⣬�����ѯ�����û�������
         * ����������� �����ݿ����� ���бȶԣ����һ�£���֤ͨ��
         */
        SysUser sysUser=this.findSysUserByUserCode(userCode);

		if (sysUser == null) {
			// �׳��쳣
			throw new CustomException("�û��ʺŲ�����");
		}

        //���ݿ�����(md5����)
        String password_db=sysUser.getPassword();

        //���������������ݿ�������жԱȣ����һ������֤ͨ��
        //��ҳ��������������md5����
        String password_input_md5=new MD5().getMD5ofStr(password);
		if (!password_input_md5.equalsIgnoreCase(password_db)) {
			// �׳��쳣
			throw new CustomException("�û������������");
		}

        //�õ��û�id
        String userId=sysUser.getId();

        //�����û�id��ѯ�˵�
        List<SysPermission> menus=this.findMenuListByUserId(userId);

        //�����û�id��ѯȨ��url
        List<SysPermission> permissions=this.findPermissionListByUserId(userId);

        //��֤ͨ���������û���Ϣ
        ActiveUser activeUser=new ActiveUser();
        activeUser.setUserid(sysUser.getId());
        activeUser.setUsercode(userCode);
        activeUser.setUsername(sysUser.getUsername());

        //����Ȩ�޷�Χ�Ĳ˵���url
        activeUser.setMenus(menus);
        activeUser.setPermissions(permissions);


        return activeUser;
    }

    //�����û��˺Ų�ѯ�û���Ϣ
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