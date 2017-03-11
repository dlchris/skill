package com.skill.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skill.entity.SysUser;
import com.skill.exception.CustomException;
import com.skill.service.SysService;
import com.skill.util.PasswordHelper;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private SysService sysService;
	
	@RequestMapping("/updatepwd")
	@ResponseBody
	public int updatepwd(String userId, String oldPassword, String newPassword) throws Exception {
		SysUser sysUser = sysService.findSysUserByUserCode(userId);
		int rows = 0;
		if (sysUser == null) {
			// �׳��쳣
			throw new CustomException("�û��ʺŲ�����");
		}

		//���ݿ�����(md5����)
        String encryptPassword_input = PasswordHelper.encryptPassword(oldPassword, sysUser.getSalt());
        
        //���������������ݿ�������жԱȣ����һ������֤ͨ��
        //��ҳ��������������md5����
		if (!encryptPassword_input.equalsIgnoreCase(sysUser.getPassword())) {
			// �׳��쳣
			throw new CustomException("�û������������");
		} else {
			// ���������е�����
			rows = sysService.updatepwd(userId, newPassword);
		}
		return rows;
	}
	
	@RequestMapping("/initUpdatePwd")
	public String initUpdatePwd(Model model,String userId) throws Exception {
		model.addAttribute("userId", userId);
		return "updpassword";
	}
}
