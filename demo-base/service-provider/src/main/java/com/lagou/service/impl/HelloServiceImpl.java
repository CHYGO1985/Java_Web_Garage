package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Random;

@Service
public class HelloServiceImpl   implements HelloService {

    @Override
    public String sayHello1(String word) {

        Random rand = new Random();

        try {
            Thread.sleep(rand.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "WOW! Hello1: "+ word;
    }

    @Override
    public String sayHello2(String word) {

        Random rand = new Random();

        try {
            Thread.sleep(rand.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "This is Hello2: "+ word;
    }

    @Override
    public String sayHello3(String word) {

        Random rand = new Random();

        try {
            Thread.sleep(rand.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "I am Hello3: "+ word;
    }
}
