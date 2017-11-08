package com.yann.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yann.dto.AdDto;
import com.yann.entity.Ad;
import com.yann.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Value("${ad.number}")
    private int adNumber;

    /**
     * 首页 —— 广告（超值特惠）
     */
    @RequestMapping(value = "/homead", method = RequestMethod.GET)
    public List<AdDto> homead() {
        AdDto adDto = new AdDto();
        adDto.getPage().setPageNumber(adNumber);
        return adService.searchByPage(adDto);
    }

/*    @RequestMapping(value = "/homead",method = RequestMethod.GET)
    public Ad homead() {
        Ad ad = new Ad();
        ad.setWeight(new Long(1));
        ad.setTitle("3123");
        ad.setLink("3123");
        ad.setImgFileName("dasdas");
        ad.setId(new Long(312));
        return ad;
    }
    @RequestMapping(value = "/homelist/{city}/{page}",method = RequestMethod.GET)
    public List<Ad> homelist() throws IOException {
        String s = "{\"hasMore\":true,\"data\":[{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201638030-473660627.png\",\"title\":\"汉堡大大\",\"subTitle\":\"叫我汉堡大大，还你多彩口味\",\"price\":\"28\",\"distance\":\"120m\",\"mumber\":\"389\",\"id\":\"5903459746059243\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201645858-1342445625.png\",\"title\":\"北京开源饭店\",\"subTitle\":\"[望京]自助晚餐\",\"price\":\"98\",\"distance\":\"140m\",\"mumber\":\"689\",\"id\":\"49867518055432725\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201652952-1050532278.png\",\"title\":\"服装定制\",\"subTitle\":\"原价xx元，现价xx元，可修改一次\",\"price\":\"1980\",\"distance\":\"160\",\"mumber\":\"106\",\"id\":\"6416820544686455\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201700186-1351787273.png\",\"title\":\"婚纱摄影\",\"subTitle\":\"免费试穿，拍照留念\",\"price\":\"2899\",\"distance\":\"160\",\"mumber\":\"58\",\"id\":\"12224304826826571\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201708124-1116595594.png\",\"title\":\"麻辣串串烧\",\"subTitle\":\"双人免费套餐等你抢购\",\"price\":\"0\",\"distance\":\"160\",\"mumber\":\"1426\",\"id\":\"5883343410691924\"}]}";
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.readValue(s, new TypeReference<List<Ad>>(){}));
        return mapper.readValue(s, new TypeReference<List<Ad>>(){});
    }
    @RequestMapping(value = "/submitComment",method = RequestMethod.POST)
    public Map<String,Object> submitComment() {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("errno",0);
        result.put("msg","ok");
        return result;
    }*/
}

