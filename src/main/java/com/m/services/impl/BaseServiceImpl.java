package com.m.services.impl;

import com.m.dao.BaseDao;
import com.m.dao.impl.BaseDaoImpl;
import com.m.services.BaseService;

public class BaseServiceImpl implements BaseService {
    private BaseDao dao = new BaseDaoImpl();

    @Override
    public void test() {
        dao.test();
    }

    public BaseDao getDao() {
        return dao;
    }

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }
}
