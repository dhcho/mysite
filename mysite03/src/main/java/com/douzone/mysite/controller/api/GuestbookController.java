package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller("guestbookControllerApi")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JsonResult list() {
		List<GuestbookVo> list = guestbookService.getMessageList();

		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/listScroll")
	public JsonResult list(@RequestBody GuestbookVo vo) {
		List<GuestbookVo> list = guestbookService.getMessageList(vo.getNo());

		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResult add(@RequestBody GuestbookVo vo) {
		// 등록 작업(GuestbookService)
		guestbookService.addMessage(vo);
		return JsonResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") int no, String password) {
		// 삭제 작업(GuestbookService)
		int data = 0;
		
		Boolean chk = guestbookService.deleteMessage(no, password);
		
		if(!chk) {
			// 1. 삭제가 안된 경우
			data = -1;
		} else {
			// 2. 삭제가 성공한 경우
			data = no;
		}
		
		return JsonResult.success(data);
	}
}
