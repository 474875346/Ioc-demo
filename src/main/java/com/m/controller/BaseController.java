package com.m.controller;

import com.m.mvc.web.Controller;
import com.m.services.BaseService;
import com.m.services.impl.BaseServiceImpl;

public class BaseController implements Controller {
    private BaseService service = new BaseServiceImpl();

    public void test() {
        System.out.println("controller被调用");
        service.test();
    }

    public BaseService getService() {
        return service;
    }

    public void setService(BaseService service) {
        this.service = service;
    }
}
