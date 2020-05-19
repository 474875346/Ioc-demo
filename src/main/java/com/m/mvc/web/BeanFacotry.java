package com.m.mvc.web;

import com.m.mvc.context.XMLApplicatContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用于生产HarderMapping
 */
public class BeanFacotry {
    public static List<HanderMapping> getBeans() {
        List<HanderMapping> hmlist = new ArrayList<HanderMapping>();

        // 1.过滤容器当中的对象
        XMLApplicatContext ac = new XMLApplicatContext();
        Set<String> beanNames = ac.getBeanNames(); // 获取所有name
        for (String beanName : beanNames) {
            Object obj = ac.getBean(beanName);


            Class<?>[] is = obj.getClass().getInterfaces();
            for (Class i : is) {
                if (i.getName().equals(Controller.class.getName())) {
                    MyRequestMapping crm = obj.getClass().getAnnotation(MyRequestMapping.class);
                    String crmValue = crm.value(); // 类注解的name
                    // 有controller接口
                    // 在获取该controller所有的方法
                    Method[] ms = obj.getClass().getMethods();
                    for (Method m : ms) {
                        MyRequestMapping mrm = m.getAnnotation(MyRequestMapping.class);
                        if (mrm != null) { // 方法上有注解的，需要映射成Mapping

                            String mrmValue = mrm.value();

                            HanderMapping hm = new HanderMapping();
                            hm.setName(beanName); // 获取beanName
                            hm.setMethod(m.getName()); // 获取要请求的方法名
                            hm.setPartypes(m.getParameterTypes()); // 该方法的所有参数类型
                            hm.setMapping(crmValue + mrmValue); // 映射的uri

                            hmlist.add(hm);// 所有的HanderMapping加入映射关系
                            System.out.println("往容器中加入映射" + hm.getMapping());
                        }
                    }


                }
            }
        }


        return hmlist;
    }
}
