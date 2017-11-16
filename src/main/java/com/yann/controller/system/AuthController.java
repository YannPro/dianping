package com.yann.controller.system;

import com.yann.constant.DicTypeConst;
import com.yann.controller.api.ApiController;
import com.yann.service.DicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private final static Logger logger = LoggerFactory
			.getLogger(ApiController.class);

	@Autowired
	private DicService dicService;

	@RequestMapping
	public String index(Model model) {
		logger.debug(dicService.getListByType(DicTypeConst.HTTP_METHOD).get(0).toString());
		model.addAttribute("httpMethodList", dicService.getListByType(DicTypeConst.HTTP_METHOD));
		return "/system/auth";
	}
}