package com.skill.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skill.exception.CustomException;

@Controller
public class LoginController {
//	@Autowired
//	private SysService sysService;

	// �û���½�ύ����
	// @RequestMapping("/login")
	// public String login(HttpSession session,String randomcode,String
	// usercode, String password) throws Exception
	// {
	//
	// //У����֤�룬��ֹ���Թ���
	// //��session�л�ȡ��ȷ��֤��
	// String validateCode= (String) session.getAttribute("validateCode");
	//
	// if (!randomcode.equals(validateCode))
	// {
	// throw new CustomException("��֤�벻��ȷ");
	// }
	//
	//
	// //����serviceУ���û��ʺź��������ȷ��
	// //����������ǽ�shiro��ʱ����д
	// ActiveUser activeUser=sysService.authenticat(usercode,password);
	//
	//
	// //���serviceУ��ͨ�������û���ݼ�¼��session
	// session.setAttribute("activeUser",activeUser);
	//
	// //�ض�����Ʒ��ѯҳ��
	// return "redirect:/first.action";
	// }

	// ��¼�ύ��ַ����application-shiro�����õ�loginurlһ��
	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws Exception {
		// �����¼ʧ�ܴ�request�л�ȡ��֤�쳣��Ϣ,shiroLoginFailure����shiro�쳣���ȫ�޶���
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");

		// ����shiro���ص��쳣��·���жϣ��׳�ָ���쳣��Ϣ
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				// ���ջ��׸��쳣������
				throw new CustomException("�˺Ų�����");
			} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				throw new CustomException("�û���/�������");
			} else if ("randomCodeError".equals(exceptionClassName)) {
				throw new CustomException("��֤�����");
			} else {
				throw new Exception();// �������쳣����������δ֪����
			}
		}
		// �˷����������¼�ɹ���shiro��֤�ɹ����Զ���ת����һ��·��
		// ��¼ʧ�ܷ��ص�loginҳ��
		return "login";
	}
	//
	// //�û��˳�
	// @RequestMapping("/logout")
	// public String logout(HttpSession session) throws Exception
	// {
	// //sessionʧЧ
	// session.invalidate();
	//
	// //�ض�����Ʒ��ѯҳ��
	// return "redirect:/first.action";
	// }
}