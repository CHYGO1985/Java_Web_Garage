package com.chygo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    // this is for testing purpose
    @RequestMapping("/home")
    public String index() {
        return "index";
    }
}
