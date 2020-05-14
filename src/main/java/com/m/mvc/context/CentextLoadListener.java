package com.m.mvc.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CentextLoadListener extends CentextLoader implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent even) {
        // 当监听启动的时候能够执行xml解析
        String path = even.getServletContext().getRealPath("");//获取工程目录
        System.out.println(path);
        super.init(path);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
