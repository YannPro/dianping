package com.yann.service.impl;


import com.yann.constant.CommentStateConst;
import com.yann.dao.OrdersDao;
import com.yann.dto.OrdersDto;
import com.yann.entity.Orders;
import com.yann.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Resource
	private OrdersDao ordersDao;
	
	@Value("${businessImage.url}")
    private String businessImageUrl;

	public boolean add(OrdersDto ordersDto) {
		Orders orders = new Orders();
		BeanUtils.copyProperties(ordersDto, orders);
		orders.setCommentState(CommentStateConst.NOT_COMMENT);
		ordersDao.insert(orders);
		return true;
	}

	public OrdersDto getById(Long id) {
		OrdersDto result = new OrdersDto();
		Orders orders = ordersDao.selectById(id);
		BeanUtils.copyProperties(orders, result);
		return result;
	}

	public List<OrdersDto> getListByMemberId(Long memberId) {
		List<OrdersDto> result = new ArrayList<OrdersDto>();
		Orders ordersForSelect = new Orders();
		ordersForSelect.setMemberId(memberId);
		List<Orders>  ordersList = ordersDao.select(ordersForSelect);
		for(Orders orders : ordersList) {
			OrdersDto ordersDto = new OrdersDto();
			result.add(ordersDto);
			BeanUtils.copyProperties(orders, ordersDto);
			ordersDto.setImg(businessImageUrl + orders.getBusiness().getImgFileName());
			ordersDto.setTitle(orders.getBusiness().getTitle());
			ordersDto.setCount(orders.getBusiness().getNumber());
		}
		return result;
	}
}