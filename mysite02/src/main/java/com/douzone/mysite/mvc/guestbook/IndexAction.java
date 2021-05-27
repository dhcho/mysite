package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MvcUtils;
import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.GuestBookVo;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 요청처리
		List<GuestBookVo> list = new GuestBookRepository().findAll();
		
		//2. request범위에 데이터(객체) 저장
		request.setAttribute("list", list);
		
		//3. view로 포워딩
		MvcUtils.forward("guestbook/list", request, response);
	}
}
