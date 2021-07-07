package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.LagouSecurityForMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jingjiejiang
 * @history
 * 1. add annotation @LagouSecurityForMethod
 * Jul 7, 2021
 *
 */
@LagouController
@LagouRequestMapping("/demo")
public class DemoController {

    @LagouAutowired
    private IDemoService demoService;

    /**
     * URL: /demo/query?name=lisi
     * @param request
     * @param response
     * @param name
     * @return
     */
    @LagouRequestMapping("/query")
    @LagouSecurityForMethod(usernames={"lilei", "hanmeimei"})
    public String query(HttpServletRequest request, HttpServletResponse response,String name) {
        return demoService.get(name);
    }
}
