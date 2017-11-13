package com.yann.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.yann.constant.ApiCodeEnum;
import com.yann.dto.*;
import com.yann.entity.Ad;
import com.yann.entity.Page;
import com.yann.service.AdService;
import com.yann.service.BusinessService;
import com.yann.service.MemberService;
import com.yann.service.OrdersService;
import com.yann.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2017-10-28 13:39
 **/
@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private AdService adService;

    @Resource
    private BusinessService businessService;

    @Resource
    private MemberService memberService;

    @Resource
    private OrdersService ordersService;

    @Value("${ad.number}")
    private int adNumber;

    @Value("${businessHome.number}")
    private int businessHomeNumber;

    @Value("${businessSearch.number}")
    private int businessSearchNumber;

    private final static Logger logger = LoggerFactory
            .getLogger(ApiController.class);

    /**
     * 首页 —— 广告（超值特惠）
     */
    @RequestMapping(value = "/homead", method = RequestMethod.GET)
    public List<AdDto> homead() {
        AdDto adDto = new AdDto();
        adDto.getPage().setPageNumber(adNumber);
        return adService.searchByPage(adDto);
    }

    /**
     * 首页 —— 推荐列表（猜你喜欢）
     */
    @RequestMapping(value = "/homelist/{city}/{page.currentPage}", method = RequestMethod.GET)
    public BusinessListDto homelist(@PathVariable String city, BusinessDto businessDto) {
        System.out.println("@PathVariable:"+city+","+businessDto.getPage().getCurrentPage());
        System.out.println("apihomelist:"+businessDto.toString());
        businessDto.getPage().setPageNumber(businessHomeNumber);
        return businessService.searchByPageForApi(businessDto);
    }
    /**
     * 搜索结果页 - 搜索结果 - 三个参数
     */
    @RequestMapping(value = "/search/{page.currentPage}/{city}/{category}/{keyword}", method = RequestMethod.GET)
    public BusinessListDto searchByKeyword(BusinessDto businessDto) {
        businessDto.getPage().setPageNumber(businessSearchNumber);
        return businessService.searchByPageForApi(businessDto);
    }

    /**
     * 搜索结果页 - 搜索结果 - 两个参数
     */
    @RequestMapping(value = "/search/{page.currentPage}/{city}/{category}", method = RequestMethod.GET)
    public BusinessListDto search(BusinessDto businessDto) {
        businessDto.getPage().setPageNumber(businessSearchNumber);
        return businessService.searchByPageForApi(businessDto);
    }
    /**
     * 详情页 - 商户信息
     */
    @RequestMapping(value = "/detail/info/{id}", method = RequestMethod.GET)
    public BusinessDto detail(@PathVariable("id") Long id) {
        return businessService.getById(id);
    }

    /**
     * 订单列表
     */
    @RequestMapping(value = "/orderlist/{username}", method = RequestMethod.GET)
    public List<OrdersDto> orderlist(@PathVariable("username") Long username) {
        // 根据手机号取出会员ID
        Long memberId = memberService.getIdByPhone(username);
        return ordersService.getListByMemberId(memberId);
    }

    /**
     * 根据手机号下发短信验证码
     */
    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public ApiCodeDto sms(@RequestParam("username") Long username) {
        ApiCodeDto dto;
        // 1、验证用户手机号是否存在（是否注册过）
        if (memberService.exists(username)) {
            // 2、生成6位随机数
            String code = String.valueOf(CommonUtil.random(6));
            // 3、保存手机号与对应的md5(6位随机数)（一般保存1分钟，1分钟后失效）
            if (memberService.saveCode(username, code)) {
                // 4、调用短信通道，将明文6位随机数发送到对应的手机上。
                if (memberService.sendCode(username, code)) {
                    dto = new ApiCodeDto(ApiCodeEnum.SUCCESS.getErrno(), code);
                } else {
                    dto = new ApiCodeDto(ApiCodeEnum.SEND_FAIL);
                }
            } else {
                dto = new ApiCodeDto(ApiCodeEnum.REPEAT_REQUEST);
            }
        } else {
            dto = new ApiCodeDto(ApiCodeEnum.USER_NOT_EXISTS);
        }
        return dto;
    }

    /**
     * 会员登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiCodeDto login(@RequestParam("username") Long username, @RequestParam("code") String code) {
        ApiCodeDto dto;
        logger.debug(username+","+code);
        // 1、用手机号取出保存的md5(6位随机数)，能取到，并且与提交上来的code值相同为校验通过
        String saveCode = memberService.getCode(username);
       logger.debug("saveCode:"+saveCode);
        if (saveCode != null) {
            if (saveCode.equals(code)) {
                // 2、如果校验通过，生成一个32位的token
                String token = CommonUtil.getUUID();
                // 3、保存手机号与对应的token（一般这个手机号中途没有与服务端交互的情况下，保持10分钟）
                memberService.saveToken(token, username);
                // 4、将这个token返回给前端
                dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
                dto.setToken(token);
            } else {
                dto = new ApiCodeDto(ApiCodeEnum.CODE_ERROR);
            }
        } else {
            dto = new ApiCodeDto(ApiCodeEnum.CODE_INVALID);
        }
        return dto;
    }

    /**
     * 买单
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ApiCodeDto order(OrderForBuyDto orderForBuyDto) {
        ApiCodeDto dto;
        // 1、校验token是否有效（缓存中是否存在这样一个token，并且对应存放的会员信息（这里指的是手机号）与提交上来的信息一致）
        Long phone = memberService.getPhone(orderForBuyDto.getToken());
        logger.debug("phone:"+phone);
        if (phone != null && phone.equals(orderForBuyDto.getUsername())) {
            // 2、根据手机号获取会员主键
            Long memberId = memberService.getIdByPhone(phone);
            // 3、保存订单
            OrdersDto ordersDto = new OrdersDto();
            ordersDto.setNum(orderForBuyDto.getNum());
            ordersDto.setPrice(orderForBuyDto.getPrice());
            ordersDto.setBusinessId(orderForBuyDto.getId());
            ordersDto.setMemberId(memberId);
            ordersService.add(ordersDto);
            dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
            // 4、TODO 还有一件重要的事未做
        } else {
            dto = new ApiCodeDto(ApiCodeEnum.NOT_LOGGED);
        }
        return dto;
    }
}
