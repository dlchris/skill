package com.skill.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomExceptionResolver implements HandlerExceptionResolver {
	// ǰ�˿�����DispatcherServlet�ڽ���HandlerMapping��
	// ����HandlerAdapterִ��Handler�����У���������쳣�ͻ�ִ�д˷���
	// �����е�handler������Ҫִ�е�Handler��������ʵ�����HandlerMethod
	// ex���ǽ��ܵ����쳣��Ϣ
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// ����쳣
		ex.printStackTrace();

		// ͳһ�쳣�������
		// ���ϵͳ�Զ����CustomException�쳣���Ϳ���ֱ�Ӵ�һ���л�ȡһ����Ϣ�����쳣�����ڴ���ҳ��չʾ
		// �쳣��Ϣ
		String message = null;
		CustomException customException = null;
		// ���ex��ϵͳ�Զ�����쳣�����Ǿ�ֱ��ȡ���쳣��Ϣ
		if (ex instanceof CustomException) {
			customException = (CustomException) ex;
		} else {
			customException = new CustomException("δ֪����");
		}

		// ������Ϣ
		message = customException.getMessage();

		request.setAttribute("message", message);

		try {
			// ת�򵽴���ҳ��
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView();
	}
}
