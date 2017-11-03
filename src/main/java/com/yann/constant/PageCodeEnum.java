package com.yann.constant;

public enum PageCodeEnum {
    ADD_SUCCESS(1000,"新增成功!"),
    ADD_FAIL(1001,"新增失败!"),;
    public static final String KEY = "pageCode";
    private Integer code;
    private String msg;
    PageCodeEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
