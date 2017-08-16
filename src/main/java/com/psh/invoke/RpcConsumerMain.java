package com.psh.invoke;

import com.psh.framework.ConsumerProxy;
import com.psh.service.HelloService;

/**
 * Created by shitou on 17-8-14.
 */
public class RpcConsumerMain {
    public static void main(String[] args) {
        HelloService helloService = ConsumerProxy.consume(HelloService.class,"127.0.0.1",8000);
        helloService.sayHello("调用远程方法");
    }
}
