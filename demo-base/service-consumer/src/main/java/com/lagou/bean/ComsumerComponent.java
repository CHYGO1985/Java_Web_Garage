package com.lagou.bean;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 *
 * @author jingjiejiang
 * @history Sep 1, 2021
 *
 */
@Component
public class ComsumerComponent {

    @Reference
    private HelloService helloService;

    public String sayHello1(String word) {

        return  helloService.sayHello1(word);
    }

    public String sayHello2(String word) {

        return helloService.sayHello2(word);
    }

    public String sayHello3(String word) {

        return helloService.sayHello3(word);
    }

}
