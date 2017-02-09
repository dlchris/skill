package com.skill.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class CustomFromAuthenticationFilter extends FormAuthenticationFilter {
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		// �����������֤���У��
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		// ȡ��session�е���ȷ��֤��
		String validateCode = (String) session.getAttribute("validateCode");

		// ȡ��ҳ�����֤��
		String randomcode = httpServletRequest.getParameter("randomcode");
		if (randomcode != null && validateCode != null && !randomcode.equals(validateCode)) {
			// ���У��ʧ�ܣ�����֤������ʧ����Ϣ��ͨ��shiroLoginFailure���õ�request��
			httpServletRequest.setAttribute("shiroLoginFailure", "randomCodeError");
			// �ܾ����ʣ�����У���˺ź�����
			return true;
		}
		return super.onAccessDenied(request, response);
	}
}