package com.m.mvc.context;

import java.util.Set;

public class XMLApplicatContext extends CentextLoader {
    public XMLApplicatContext(String path) {
        super.init();
    }

    public XMLApplicatContext() {
    }

    public Object getBean(String name) {
        return applicationContext.get(name).getObj();
    }

    public Set<String> getBeanNames() {
        return applicationContext.keySet();
    }
}
