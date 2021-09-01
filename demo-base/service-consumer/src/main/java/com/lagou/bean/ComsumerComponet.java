package com.lagou.bean;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author jingjiejiang
 * @history Sep 1, 2021
 */
@Component
public class ComsumerComponet {
    @Reference
    private HelloService  helloService;
    public String  sayHello(String name){
        return  helloService.sayHello1(name);
    }

}
