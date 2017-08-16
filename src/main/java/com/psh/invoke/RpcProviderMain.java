package com.psh.invoke;

import com.psh.framework.ProviderReflect;
import com.psh.service.HelloService;
import com.psh.service.HelloServiceImpl;

/**
 * Created by shitou on 17-8-14.
 */
public class RpcProviderMain {
    public static void main(String[] args) {
        //服务的提供方， 1。 先开启一个服务 2. 服务等待调用
        HelloService helloService = new HelloServiceImpl();

        //服务的提供方，包括具体的实现类，要调用的方法，方法的参数，这里只实现类，端口
        ProviderReflect.provider(helloService,11);
    }

}
