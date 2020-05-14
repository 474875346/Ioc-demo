package com.m.controller;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BaseControllerTest {

    @Test
    public void testTest1() {
        BaseController baseController = new BaseController();
        baseController.test();
    }

}