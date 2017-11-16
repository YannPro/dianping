package com.yann.controller.system;

import com.yann.constant.PageCodeEnum;
import com.yann.controller.api.ApiController;
import com.yann.dto.MenuDto;
import com.yann.dto.MenuForMoveDto;
import com.yann.dto.MenuForZtreeDto;
import com.yann.dto.PageCodeDto;
import com.yann.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单相关
 */
@RestController
@RequestMapping("/menus")
public class MenusController {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private final static Logger logger = LoggerFactory.getLogger(ApiController.class);
	/**
	 * 获取菜单列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<MenuForZtreeDto> getList() {
		logger.info("进来了");
		return menuService.getZtreeList();
	}
	
	/**
	 * 新增菜单
	 */
	@RequestMapping(method = RequestMethod.POST)
	public PageCodeDto add(MenuDto menuDto) {
		PageCodeDto result;
		if(menuService.add(menuDto)) {
			result = new PageCodeDto(PageCodeEnum.ADD_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.ADD_FAIL);
		}
		return result;
	}
	
	/**
	 * 菜单排序
	 */
	@RequestMapping(value="/{dropNodeId}/{targetNodeId}/{moveType}",method = RequestMethod.PUT)
	public PageCodeDto order(MenuForMoveDto menuForMoveDto) {
		PageCodeDto result;
		if(menuService.order(menuForMoveDto)) {
			result = new PageCodeDto(PageCodeEnum.ORDER_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.ORDER_FAIL);
		}
		return result;
	}
	
	/**
	 * 根据主键获取菜单dto
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public MenuDto getById(@PathVariable("id") Long id) {
		return menuService.getById(id);
	}
	
	/**
	 * 修改菜单
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.PUT)
	public PageCodeDto modify(MenuDto menuDto) {
		PageCodeDto result;
		if(menuService.modify(menuDto)) {
			result = new PageCodeDto(PageCodeEnum.MODIFY_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.MODIFY_FAIL);
		}
		return result;
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public PageCodeDto remove(@PathVariable("id")Long id) {
		PageCodeDto result;
		MenuDto menuDto = new MenuDto();
		menuDto.setParentId(id);
		List<MenuDto> list = menuService.getList(menuDto);
		if(list.size() > 0) {
			result = new PageCodeDto(PageCodeEnum.SUB_MENU_EXISTS);
		} else {
			if(menuService.remove(id)) {
				result = new PageCodeDto(PageCodeEnum.REMOVE_SUCCESS);
			} else {
				result = new PageCodeDto(PageCodeEnum.REMOVE_FAIL);
			}
		}
		return result;
	}
}