package com.psh.framework;

/**
 * Created by shitou on 17-8-14.
 * 服务消费代理接口，因为是代理接口，1.  泛型代替工厂 2 选择通信机制为scoket
 */
public class ConsumerProxy {
    public static <T>T consume(final Class<T> tClass,final String ip,final String port){
        //通过scoket传入接口对象，
        return null;
    }
}
