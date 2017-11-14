package com.yann.controller.content;

import com.yann.dto.AdDto;
import com.yann.dto.CommentDto;
import com.yann.entity.Page;
import com.yann.service.AdService;
import com.yann.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentsController {
	@Autowired
	private CommentService commentService;

	/**
	 * 评论管理页初始化(点广告管理菜单进入的第一个页面)
	 */
	@RequestMapping
	public String init(Model model, CommentDto commentDto,Page page) {
		System.out.println(page.toString());
		//System.out.println(commentDto.toString());
		System.out.println(commentService.getListByBusinessId(null,page).getData().get(0).toString());
		model.addAttribute("list", commentService.getListByBusinessId(null,page).getData());
		model.addAttribute("searchParam", commentDto);
		return "/content/commentList";
	}

}
