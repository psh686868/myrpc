package com.psh.service;

/**
 * Created by shitou on 17-8-14.
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello(String content) {
        return "调用say："+content;
    }
}
