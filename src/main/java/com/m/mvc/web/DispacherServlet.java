package com.m.mvc.web;

import com.m.mvc.context.XMLApplicatContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DispacherServlet implements Servlet {


    private List<HanderMapping> handerMappingList;

    private void initHanderMapping() {
        // 1.在请求到达以后，所有的HanderMapping已经映射完毕
        this.handerMappingList = BeanFacotry.getBeans();
    }

    @Override
    public void init(ServletConfig conf) throws ServletException {
        initHanderMapping();
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
//        HttpSession session = request.getSession();
//        ServletContext application = session.getServletContext();

//        String uri = request.getRequestURI();
//        String[] uris = uri.split("/");
//        String controllerName = uris[2];
//        String methodName = uris[3].replace(".do", "");
//        XMLApplicatContext xmlac = new XMLApplicatContext();
//        Object obj = xmlac.getBean(controllerName);
//        try {
//            Method method = obj.getClass().getMethod(methodName);
//            method.invoke(obj);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        for (HanderMapping hm : handerMappingList) {
            HanderChain hc = hm.getHander(request,response);
            if (hc != null) {
                ModelAndView mv = hc.chain();

                // 完成相应阶段
                break;
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
