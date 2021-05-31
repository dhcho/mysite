package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// firstPageNo = 3
		// lastPageNo = 7
		// nextpageNo = 8
		// prevPageNo = 2
		// currentPage = 4
		
		// map = new.request..
		// map.put("lastPageNo", lastPageNo)
		// request.setAttribute("pageInfo", map);
		
		//1. 요청처리
		List<BoardVo> list = new BoardRepository().findAll();
				
		//2. request범위에 데이터(객체) 저장
		request.setAttribute("list", list);
		
		MvcUtils.forward("board/list", request, response);
	}

}
