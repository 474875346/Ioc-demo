package com.m.mvc.context;

import com.m.mvc.entity.CentextApplication;
import com.m.mvc.exception.SpringFrameworkLoadListenerException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 负责加载并解析配置文件
public abstract class CentextLoader {
    // 用于保存对象依存关系的集合 严格意义ClassLoader
    protected static Map<String, CentextApplication> applicationContext = new HashMap<String, CentextApplication>();

    // 针对web工程
    public void init(String webPath) {
        try {
            this.checkPath(webPath);
            this.build();
            int i = 0;
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 针对Java工程
    public void init() {

    }

    private Document document;

    private void checkPath(String webPath) throws Exception {
        webPath += "WEB-INF/application.xml";
        File f = new File(webPath);
        if (!f.exists()) {
            throw new Exception("webPath未发现");
        } else {
            // xml解析器
            SAXReader sax = new SAXReader();
            this.document = sax.read(f);
        }
    }

    // 解析配置文件
    private void build() throws Exception {

        Element root = this.document.getRootElement();
        List<Element> beans = root.elements("bean");

        for (Element bean : beans) {
            String name = bean.attributeValue("name");
            String clazz = bean.attributeValue("class");
            Object pobj = Class.forName(clazz).newInstance(); // 通过反射获取到bean的实例
            CentextApplication pca = new CentextApplication();
            pca.setObj(pobj);

            // 获取需要注入的内容
            List<Element> opts = bean.elements("opt");
            for (Element opt : opts) {
                String cname = opt.attributeValue("name");
                String ref = opt.attributeValue("ref");
                System.out.println(cname);
                CentextApplication ca = this.applicationContext.get(ref);
                if (ca == null) { //没有找到需要注入的内容
                    throw new SpringFrameworkLoadListenerException(ref + "未找到");
                } else {
                    Object cobj = ca.getObj();//需要注入的对象
                    // 获取要注入的属性
                    Field field = pobj.getClass().getDeclaredField(cname);
                    // 获取set方法的方法名
                    String methodName = "set" + String.valueOf(cname.charAt(0)).toUpperCase() +cname.substring(1);
                    // 获取set方法
                    Method method = pobj.getClass().getMethod(methodName, field.getType());
                    // 执行完成注入
                    method.invoke(pobj,cobj);
                }
            }
            // 加入依赖关系集合
            this.applicationContext.put(name,pca);
        }
    }

}
