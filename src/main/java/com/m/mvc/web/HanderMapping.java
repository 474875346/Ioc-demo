package com.m.mvc.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 负责映射到具体的hander
 */
public class HanderMapping {
    private String mapping; // uri的映射名
    private String name;
    private String method;
    private Class<?>[] partypes;

    public HanderChain getHander(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getServletContext().getContextPath();
        System.out.println(contextPath + mapping);

        if ((contextPath + mapping).equals(request.getRequestURI())) {
            HanderChain hander = new HanderChain(name,method,partypes,request,response);
            return hander;
        }

        return null;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class<?>[] getPartypes() {
        return partypes;
    }

    public void setPartypes(Class<?>[] partypes) {
        this.partypes = partypes;
    }

    @Override
    public String toString() {
        return "HanderMapping{" +
                "mapping='" + mapping + '\'' +
                ", name='" + name + '\'' +
                ", method='" + method + '\'' +
                ", partypes=" + Arrays.toString(partypes) +
                '}';
    }
}
