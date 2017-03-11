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
			// 抛出异常
			throw new CustomException("用户帐号不存在");
		}

		//数据库密码(md5加密)
        String encryptPassword_input = PasswordHelper.encryptPassword(oldPassword, sysUser.getSalt());
        
        //对输入的密码和数据库密码进行对比，如果一致则认证通过
        //对页面输入的密码进行md5加密
		if (!encryptPassword_input.equalsIgnoreCase(sysUser.getPassword())) {
			// 抛出异常
			throw new CustomException("用户名或密码错误");
		} else {
			// 更新数据中的密码
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
