package com.yann.service;

import com.yann.dto.BusinessDto;
import com.yann.dto.BusinessListDto;

import java.util.List;

public interface BusinessService {
	
	/**
	 * 新增
	 * @param BusinessDto 商户dto对象
	 * @return 是否新增成功：true-成功;fale-失败
	 */
	boolean add(BusinessDto businessDto);
    
    /**
     * 根据主键获取商户dto
     * @param id 主键
     * @return 商户dto
     */
    BusinessDto getById(Long id);
    
    /**
     * 分页搜索商户列表
     * @param businessDto 查询条件(包含分页对象)
     * @return 商户列表
     */
    List<BusinessDto> searchByPage(BusinessDto businessDto);
    
    /**
     * 分页搜索商户列表(接口专用)
     * @param businessDto 查询条件(包含分页对象)
     * @return 商户列表Dto对象
     */
    BusinessListDto searchByPageForApi(BusinessDto businessDto);

    /**
     * 修改商户
     * @param businessDto
     * @return 是否修改成功：true-成功;fale-失败
     */
    boolean modify(BusinessDto businessDto);
}
