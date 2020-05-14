package com.m.controller;

import com.m.services.BaseService;
import com.m.services.impl.BaseServiceImpl;

public class BaseController {
    private BaseService service = new BaseServiceImpl();

    public void test() {
        service.test();
    }

    public BaseService getService() {
        return service;
    }

    public void setService(BaseService service) {
        this.service = service;
    }
}
