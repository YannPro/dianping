package com.yann.dto;


import com.yann.entity.Comment;

public class CommentDto extends Comment {
    
    /**
     * 隐藏中间4位的手机号
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}