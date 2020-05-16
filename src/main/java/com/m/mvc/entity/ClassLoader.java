package com.m.mvc.entity;

public class ClassLoader {
    private String name;
    private String clazz;
    private ClassLoader patten;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public ClassLoader getPatten() {
        return patten;
    }

    public void setPatten(ClassLoader patten) {
        this.patten = patten;
    }
}
