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
		int count = new BoardRepository().selectCnt();
		String tempStart = request.getParameter("page");
		String kwd = request.getParameter("kwd");
		
		int startPage = 0;
		int onePageCnt = 10;
		
		count = (int)Math.ceil((double)count/(double)onePageCnt);
		
		if(tempStart != null) {
			startPage = (Integer.parseInt(tempStart)-1) * onePageCnt;
		}
		
		List<BoardVo> list = new BoardRepository().selectPage(startPage, onePageCnt, kwd);
				
		//2. request범위에 데이터(객체) 저장
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		MvcUtils.forward("board/list", request, response);
	}

}
