package com.m.controller;

import com.m.mvc.web.Controller;
import com.m.mvc.web.MyRequestMapping;
import com.m.services.BaseService;
import com.m.services.impl.BaseServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyRequestMapping("/baseController")
public class BaseController implements Controller {
    private BaseService service = new BaseServiceImpl();

    @MyRequestMapping("/test1.do")
    public void test1(String str, Integer id, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("controller被调用");
        service.test();
    }
    @MyRequestMapping("/test2.do")
    public void test2(String str, Integer id, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("controller被调用");
        service.test();
    }
    @MyRequestMapping("/test3.do")
    public void test3(String str, Integer id, HttpServletRequest req, HttpServletResponse resp) {
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
