package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 요청처리
		String strNo = request.getParameter("no");
		Long no = Long.parseLong(strNo);
		String strUserNo = request.getParameter("userNo");
		Long userNo = Long.parseLong(strUserNo);
		
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setUserNo(userNo);
		
		new BoardRepository().delete(vo);
		
		// 2. redirect 응답 
		MvcUtils.redirect(request.getContextPath() + "/board", request, response);
	}
}
