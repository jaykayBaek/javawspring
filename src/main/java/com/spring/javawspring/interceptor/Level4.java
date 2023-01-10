package com.spring.javawspring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Level4 extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel") == null ? 100 : (int)session.getAttribute("sLevel");
		RequestDispatcher dispatcher;
		
		// 비회원인 경우
		if(level > 4) {
			dispatcher = request.getRequestDispatcher("/msg/levelMemberNo");
			dispatcher.forward(request, response);
			return false;
		}
		
		return true;
	}


	
}
