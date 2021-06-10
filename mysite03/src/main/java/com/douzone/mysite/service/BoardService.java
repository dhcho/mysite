package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;

	public void addBoard(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public List<BoardVo> getBoardSelectList(String page, String kwd) {
		int startPage = 0;
		int onePageCnt = 10;
		
		if(page != null) {
			startPage = (Integer.parseInt(page)-1) * onePageCnt;
		}
		
		List<BoardVo> list = boardRepository.selectPage(startPage, onePageCnt, kwd);
		
		return list;
	}
	
	public int getBoardCount(String kwd) {
		return boardRepository.selectCnt(kwd);
	}
	
	public BoardVo getBoardView(int index) {
		// 조회수 update
		boardRepository.updateHit(index);
		
		return boardRepository.findView(index);
	}
	
	public void updateBoard(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteBoard(Long no, Long userNo) {
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setUserNo(userNo);
		
		boardRepository.delete(vo);
	}

}
