package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 요청처리
		int index = Integer.parseInt(request.getParameter("no"));

		BoardVo listDetail = new BoardRepository().findView(index);
		
		// 조회수 update
		Boolean hit = new BoardRepository().updateHit(index);

		//2. request범위에 데이터(객체) 저장
		request.setAttribute("listDetail", listDetail);

		MvcUtils.forward("board/view", request, response);
	}

}
