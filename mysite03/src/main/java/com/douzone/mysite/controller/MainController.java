package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	ServletContext application;
	@Autowired
	private SiteService siteService;
	
	@RequestMapping("")
	public String index(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("vo", vo);
		application.setAttribute("title", vo.getTitle());
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg1")
	public String message1() {
		return "안녕~~";
	}
	
	@ResponseBody
	@RequestMapping("/msg2")
	public UserVo message2() {
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setEmail("oop6413@gmail.com");
		vo.setName("조대헌");
		return vo;
	}
}