package com.douzone.mysite.web.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.Action;

public class DeleteAddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 요청처리
		String strNo = request.getParameter("no");
		Long no = Long.parseLong(strNo);
		String password = request.getParameter("password");
		
		GuestBookVo vo = new GuestBookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		new GuestBookRepository().delete(vo);
		
		// 2. redirect 응답 
		response.sendRedirect(request.getContextPath() + "/guestbook");
	}

}
