package com.yann.controller.test;

import com.yann.dto.FirstDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author
 * @create 2017-11-05 13:25
 **/
@Controller
@RequestMapping
public class MyController {
    @RequestMapping("/test")
    public void test(FirstDto firstDto){
        System.out.println(firstDto.getMap().get("str1").getList().get(0));
    }
    @RequestMapping("/test/index")
    public String init(){
        return "/test";
    }
}
