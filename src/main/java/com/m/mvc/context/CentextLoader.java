package com.m.mvc.context;

import com.m.entity.CentextApplication;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;

// 负责加载并解析配置文件
public abstract class CentextLoader {

    public static Map<String , CentextApplication> centextApplicationMap;

    // 针对web工程
    public void init(String webPath) {
        try {
            this.checkPath(webPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 针对Java工程
    public void init() {

    }

    private Document document;

    private void checkPath(String webPath) throws Exception {
        webPath += "\\WEB-INF\\application.xml";
        File f = new File(webPath);
        if (f.exists()) {
            throw new Exception("webPath未发现");
        } else {
            // xml解析器
            SAXReader sax = new SAXReader();
            this.document = sax.read(f);

        }
    }
    // 解析配置文件


}
