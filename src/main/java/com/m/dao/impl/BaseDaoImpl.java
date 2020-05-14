package com.m.dao.impl;

import com.m.dao.BaseDao;

public class BaseDaoImpl implements BaseDao {
    @Override
    public void test() {
        System.out.println("dao被调用");
    }
}
