package com.m.mvc.context;

public class XMLApplicatContext extends CentextLoader {
    public XMLApplicatContext(String path) {
        super.init();
    }

    public XMLApplicatContext() {
    }

    public Object getBean(String name) {
        return applicationContext.get(name).getObj();
    }

}
