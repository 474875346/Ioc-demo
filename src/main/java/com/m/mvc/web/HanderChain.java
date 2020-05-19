package com.m.mvc.web;

import com.m.mvc.context.XMLApplicatContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 负责具体的业务调用
 */
public class HanderChain { // 当前访问的执行流程

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

    private Map<String, String[]> requestMap = new HashMap<String, String[]>();

    /**
     * 检查是否是文件上传
     *
     * @return true 是上传 false不是
     */
    private boolean ifFileUpload() {
        return false;
    }

    /**
     * 获取所有get或者post传递来的请求参数
     */
    private void getRequestMap() {
        if (!this.ifFileUpload()) { // 获取非文件上传的请求参数
            requestMap = this.request.getParameterMap(); // 获取所有的请求参数
        } else {

        }
    }

    private ModelAndView sendParametersByFileUpload() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    /**
     * 往hander的方法当中，提交参数，这一部分参数，
     * 有的来自于请求requestMap，
     * 有的来自于服务器本身3大域+response，
     * 有的属于框架规则ModelMapping
     *
     * @return
     */
    private ModelAndView sendParametersByNoFileUpload() {
        ModelAndView mv = new ModelAndView();
        try {
            // 获取要操作的hander
            XMLApplicatContext ac = new XMLApplicatContext();
            this.hander = ac.getBean(name);
            // 获取要操作的方法
            Method method = this.hander.getClass().getMethod(this.method, this.pars);
            Parameter[] parameters = method.getParameters(); // 拿到所有参数
            List pars = new ArrayList(); // 要执行的参数
            ModelMapping model = new ModelMapping(); // 结果model
            for (Parameter p : parameters) {
                Class<?> partype = p.getType();
                String parname = p.getName();
                // 参数当中有可能出现的情况
                // 3大域+response/ModelMapping/UploadFile/参数
                if (partype.getName().equals(HttpServletRequest.class.getName())) {
                    pars.add(this.request);
                } else if (partype.getName().equals(HttpServletResponse.class.getName())) {
                    pars.add(this.response);
                } else if (partype.getName().equals(HttpSession.class.getName())) {
                    pars.add(this.session);
                } else if (partype.getName().equals(ServletContext.class.getName())) {
                    pars.add(this.application);
                } else if (partype.getName().equals(ModelMapping.class.getName())) {
                    pars.add(model);
                } else {
                    if (partype.getName().equals(Byte.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(Byte.class.getConstructor(String.class).newInstance(requestMap.get(parname)[0]));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(Short.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(Short.class.getConstructor(String.class).newInstance(requestMap.get(parname)[0]));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(Integer.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(Integer.class.getConstructor(String.class).newInstance(requestMap.get(parname)[0]));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(Long.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(Long.class.getConstructor(String.class).newInstance(requestMap.get(parname)[0]));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(Float.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(Float.class.getConstructor(String.class).newInstance(requestMap.get(parname)[0]));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(Double.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(Double.class.getConstructor(String.class).newInstance(requestMap.get(parname)[0]));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(Boolean.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(Boolean.class.getConstructor(String.class).newInstance(requestMap.get(parname)[0]));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(String.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(requestMap.get(parname)[0]);
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(String[].class.getName())) {
                        if (requestMap.get(parname) != null) {
                            pars.add(requestMap.get(parname));
                        } else {
                            pars.add(null);
                        }
                    } else if (partype.getName().equals(Date.class.getName())) {
                        if (requestMap.get(parname) != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = sdf.parse(requestMap.get(parname)[0]);
                            pars.add(date);
                        } else {
                            pars.add(null);
                        }
                    }
                }

//                String[] parStrs = requestMap.get(parname); // 获取请求到的参数
            }

            method.invoke(this.hander, pars.toArray());

            System.out.println(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return mv;
    }

    public ModelAndView chain() {
        this.getRequestMap();// 获取所有get或者post的请求参数
        if (this.ifFileUpload()) {
            return this.sendParametersByFileUpload(); // 是文件上传的参数封装
        } else {
            return this.sendParametersByNoFileUpload(); // 不是文件上传的参数封装
        }
    }

}
