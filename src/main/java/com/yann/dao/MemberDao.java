package com.yann.dao;


import com.yann.entity.Member;

import java.util.List;

public interface MemberDao {
    /**
     * 根据查询条件查询会员列表
     * @param member 查询条件
     * @return 会员列表
     */
    List<Member> select(Member member);
}