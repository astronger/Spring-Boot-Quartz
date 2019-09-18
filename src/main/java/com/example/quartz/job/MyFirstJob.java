package com.example.quartz.job;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component //(传入spring容器中，不支持传参数)
public class MyFirstJob {

    public void sayHello(){
        System.out.println("first job say hello:"+new Date());
    }
}
