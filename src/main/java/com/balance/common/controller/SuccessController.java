package com.balance.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.balance.util.config.PubConfig;
import com.balance.util.string.StringUtil;

@Controller
@RequestMapping("/")
public class SuccessController {
	@Autowired
	private PubConfig pubConfig;

	@RequestMapping("success")
	public ModelAndView success(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		mv.addObject("msg", StringUtil.decodeUrl(request.getParameter("msg")));
		String backUrl = request.getParameter("backUrl");
		if (backUrl.indexOf("http") == -1)
			backUrl = pubConfig.getDynamicServer() + backUrl;
		mv.addObject("backUrl", StringUtil.decodeUrl(backUrl));
		return mv;
	}
}
