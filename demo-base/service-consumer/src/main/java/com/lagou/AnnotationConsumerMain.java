package com.lagou;

import com.lagou.bean.ComsumerComponent;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AnnotationConsumerMain  {
    public static void main(String[] args) throws  Exception {

        System.out.println("-------------");
        AnnotationConfigApplicationContext   context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        // 获取消费者组件
        ComsumerComponent service = context.getBean(ComsumerComponent.class);
        while(true){
             System.in.read();
             String  hello = service.sayHello1("world");
             System.out.println("result:"+hello);
        }

//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100,
//                0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
//        long startTime = System.currentTimeMillis();
//        int minutes = 3;
//        int count = 0;
//
//        while (true) {
//
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                threadPoolExecutor.execute(() -> {
//                    service.sayHello1("word1");
//                });
//
//                threadPoolExecutor.execute(() -> {
//                    service.sayHello2("word2");
//                });
//
//                threadPoolExecutor.execute(() -> {
//                    service.sayHello3("word3");
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            count ++;
//
//            if (count % 100 == 0) System.out.println("running.... : " + count);
//
//            // only execute for 3 minutes
//            if ((System.currentTimeMillis() - startTime) > minutes * 60 * 1000) break;;
//        }
//
//        System.out.println("Every minutes the function is invoked " + count / minutes + " times");
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan(basePackages = "com.lagou.bean")
    @EnableDubbo
    static  class  ConsumerConfiguration{

    }
}
