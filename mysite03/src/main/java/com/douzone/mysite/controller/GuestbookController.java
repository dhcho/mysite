package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	GuestbookService guestbookService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		
		return "guestbook/index";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") int no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		// request.setCharacterEncoding("utf-8");
		//guestbookService.delete(no, password);
		guestbookService.deleteMessage(no, password);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		// request.setCharacterEncoding("utf-8");
		//guestbookService.insert(vo);
		guestbookService.addMessage(vo);
		return "redirect:/guestbook";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String handlerException() {
//		// 1. logging
//		return "error/exception"; // 2. 사과 페이지 이동...
//	}
	
	@RequestMapping("/spa")
	public String spaRending() {
		return "guestbook/spa-rending";
	}
}
