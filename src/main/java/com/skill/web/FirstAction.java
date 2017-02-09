package com.skill.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skill.entity.ActiveUser;


@Controller
public class FirstAction {
	//ϵͳ��ҳ
	@RequestMapping("/first")
	public String first(Model model)throws Exception{

		//��shiro��session��ȡ��activeUser
		Subject subject= SecurityUtils.getSubject();
		//ȡ�������Ϣ
		ActiveUser activeUser= (ActiveUser) subject.getPrincipal();
		//ͨ��model����ҳ��
		model.addAttribute("activeUser",activeUser);
		
		return "/first";
	}
	
	//��ӭҳ��
	@RequestMapping("/welcome")
	public String welcome(Model model)throws Exception{
		
		return "/welcome";
		
	}
}	
