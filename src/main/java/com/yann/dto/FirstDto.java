package com.yann.dto;

import java.util.Map;

/**
 * @author
 * @create 2017-11-05 13:22
 **/
public class FirstDto {
    private Map<String,SecondDto> map;

    public Map<String, SecondDto> getMap() {
        return map;
    }

    public void setMap(Map<String, SecondDto> map) {
        this.map = map;
    }
}
