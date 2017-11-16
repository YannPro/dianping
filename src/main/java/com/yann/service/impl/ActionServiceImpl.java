package com.yann.service.impl;

import com.yann.dao.ActionDao;
import com.yann.dto.ActionDto;
import com.yann.entity.Action;
import com.yann.service.ActionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
	
	@Autowired
	private ActionDao actionDao;
	
	public boolean add(ActionDto dto) {
		return actionDao.insert(dto) == 1;
	}

	public boolean remove(Long id) {
		return actionDao.deleteById(id) == 1;
	}

	public boolean modify(ActionDto dto) {
		Action action = new Action();
		BeanUtils.copyProperties(dto,action);
		return actionDao.update(action) == 1;
	}

	public ActionDto getById(Long id) {
		ActionDto result = new ActionDto();
		Action action = actionDao.selectById(id);
		BeanUtils.copyProperties(action, result);
		return result;
	}
}
