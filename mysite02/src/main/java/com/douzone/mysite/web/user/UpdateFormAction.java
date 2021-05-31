package com.douzone.mysite.web.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근제어(인증이 필요한 접근에 대한 체크)
		HttpSession session = request.getSession();
		if(session == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		
		Long userNo = authUser.getNo();
		// UserVo userVo = new UserRepository.findByNo(userNo); // userVo를 request에 담아서
		// request.setAttribute("userVo", userVo); // 패스워드는 받아오지 않음
		
		MvcUtils.forward("user/updateform", request, response);
	}
	
}
