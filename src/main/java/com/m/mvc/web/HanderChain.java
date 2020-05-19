package com.m.mvc.web;

import com.m.mvc.context.XMLApplicatContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 负责具体的业务调用
 */
public class HanderChain { // 当前访问的执行流程

    // 拦截器集合
    private String name;
    private String method;
    private Class<?>[] pars;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletContext application;
    private Object hander;

    public HanderChain() {
    }

    public HanderChain(String name, String method, Class<?>[] pars, HttpServletRequest request, HttpServletResponse response) {
        this.name = name;
        this.method = method;
        this.pars = pars;
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.application = this.session.getServletContext();
    }

    //hander本身

    public ModelAndView chain() {
        ModelAndView mv = new ModelAndView();

        Map<String, String[]> map = this.request.getParameterMap(); // 获取所有的请求参数

        // 获取要操作的hander
        XMLApplicatContext ac = new XMLApplicatContext();
        this.hander = ac.getBean(name);
        // 获取要操作的方法
        try {
            Method method = this.hander.getClass().getMethod(this.method, this.pars);
            Parameter[] parameters = method.getParameters(); // 拿到所有参数
            List pars = new ArrayList(); // 要执行的参数
            ModelMapping model = new ModelMapping(); // 结果model
            for (Parameter p : parameters) {
                Class<?> partype = p.getType();
                String parname = p.getName();
                // 参数当中有可能出现的情况
                // 3大域+response/ModelMapping/UploadFile/参数

            }

            method.invoke(this.hander, pars.toArray());

            System.out.println(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return mv;
    }

}
