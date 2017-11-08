package com.yann.dao;

import com.yann.entity.Dic;

import java.util.List;

public interface DicDao {
    List<Dic> select(Dic dic);
}