package com.yann.controller.content;

import com.yann.constant.PageCodeEnum;
import com.yann.dto.AdDto;
import com.yann.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/ad")
public class AdController {

	@Autowired
	private AdService adService;

	/**
	 * 广告管理页初始化(点广告管理菜单进入的第一个页面)
	 */
	@RequestMapping
	public String init(Model model) {
		AdDto adDto = new AdDto();
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}

	/**
	 * 查询
	 */
	@RequestMapping("/search")
	public String search(Model model, AdDto adDto) {
		model.addAttribute("list", adService.searchByPage(adDto));
		System.out.println("当前页数："+adDto.getPage().getCurrentPage());
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	public String remove(@RequestParam("id") Long id,Model model) {
		if(adService.remove(id)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS);
		} else {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL);
		}
		return "forward:/ad";
	}
	/**
	 * 新增页初始化
	 */
	@RequestMapping("/addInit")
	public String addInit() {
		return "/content/adAdd";
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public String add(AdDto adDto, Model model) {
		boolean flag = adService.add(adDto);
		if(flag){
			model.addAttribute(PageCodeEnum.KEY,PageCodeEnum.ADD_SUCCESS);
		}else {
			model.addAttribute(PageCodeEnum.KEY,PageCodeEnum.ADD_FAIL);
		}
		return "/content/adAdd";
	}

	/**
	 * 修改页初始化
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model, @RequestParam("id") Long id) {
		System.out.println(adService.getById(id).toString());
		model.addAttribute("modifyObj", adService.getById(id));
		return "/content/adModify";
	}

}
