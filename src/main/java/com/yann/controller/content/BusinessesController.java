package com.yann.controller.content;

import com.yann.constant.DicTypeConst;
import com.yann.constant.PageCodeEnum;
import com.yann.dto.BusinessDto;
import com.yann.service.BusinessService;
import com.yann.service.DicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@RequestMapping("/businesses")
public class BusinessesController {

	@Resource
	private DicService dicService;

	@Resource
	private BusinessService businessService;

	/**
	 * 商户列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, BusinessDto dto) {
		System.out.println(businessService.searchByPage(dto).get(0).toString());
		model.addAttribute("list", businessService.searchByPage(dto));
		model.addAttribute("searchParam", dto);
		return "/content/businessList";
	}

	/**
	 * 删除商户
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable("id") Long id) {
		return "redirect:/businesses";
	}

	/**
	 * 商户新增页初始化
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addInit(Model model) {
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		return "/content/businessAdd";
	}

	/**
	 * 商户新增
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String add(BusinessDto dto,RedirectAttributes attr) {
		if(businessService.add(dto)) {
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
			return "redirect:/businesses";
		} else {
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
			return "redirect:/businesses/addPage";
		}
	}

	/**
	 * 商户修改页初始化
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String modifyInit(Model model, @PathVariable("id") Long id) {
		///System.out.println(dicService.getListByType(DicTypeConst.CITY).get(0).toString());
		//System.out.println(dicService.getListByType(DicTypeConst.CATEGORY).get(0).toString());
		System.out.println("modifyInit:"+businessService.getById(id).toString());
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		model.addAttribute("modifyObj", businessService.getById(id));
		return "/content/businessModify";
	}

	/**
	 * 商户修改
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String modify(@PathVariable("id") Long id, BusinessDto dto,Model model) {
		System.out.println("controller---id:"+id+","+dto.toString());
		model.addAttribute("modifyObj", dto);
		if (businessService.modify(dto)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS);
		} else {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
		}
		return "/content/businessModify";
	}
}