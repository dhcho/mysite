package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController{
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(Model model) {
		String kwd = "";
		int count = boardService.getBoardCount(kwd);
		int onePageCnt = 10;
		String startPage = "1";
		List<BoardVo> list = boardService.getBoardSelectList(startPage, "");
		
		count = (int)Math.ceil((double)count/(double)onePageCnt);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		
		return "board/list";
	}
	
	@RequestMapping("/list/{pageNo}")
	public String list(@PathVariable("pageNo") String pageNo, Model model) {
		String kwd = "";
		int count = boardService.getBoardCount(kwd);
		int onePageCnt = 10;
		List<BoardVo> list = boardService.getBoardSelectList(pageNo, "");
		
		count = (int)Math.ceil((double)count/(double)onePageCnt);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		
		return "board/list";
	}
	
	@RequestMapping("/listFind")
	public String listFind(@RequestParam("kwd") String kwd, Model model) {
		int count = boardService.getBoardCount(kwd);
		int onePageCnt = 10;
		String pageNo = "1";
		List<BoardVo> list = boardService.getBoardSelectList(pageNo, kwd);
		
		count = (int)Math.ceil((double)count/(double)onePageCnt);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") int no, Model model) {
		BoardVo listDetail = boardService.getBoardView(no);
		model.addAttribute("listDetail", listDetail);
		
		return "board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String add() {
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String add(@AuthUser UserVo authUser, 
			@RequestParam("title") String title,
			@RequestParam("content") String contents, 
			BoardVo vo) {
		vo.setUserNo(authUser.getNo());
		vo.setTitle(title);
		vo.setContents(contents);
		boardService.addBoard(vo);
		return "redirect:/board/";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@PathVariable("no") int no, Model model) {
		BoardVo vo = boardService.getBoardView(no);
		model.addAttribute("no", no);
		model.addAttribute("listDetail", vo);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(@PathVariable("no") long no, @RequestParam("title") String title, 
			@RequestParam("contents") String contents, 
			BoardVo vo) {
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setNo(no);
		boardService.updateBoard(vo);
		
		return "redirect:/board/";
	}
	
	@RequestMapping(value="/delete/{no}/{userNo}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") long no, @PathVariable("userNo") long userNo) {
		boardService.deleteBoard(no, userNo);
		
		return "redirect:/board/";
	}

}
