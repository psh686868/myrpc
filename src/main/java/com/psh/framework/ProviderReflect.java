package com.psh.framework;

/**
 * Created by shitou on 17-8-14.
 */
public class ProviderReflect {
    private static final int initPort = 8000;
    public static <T>T provider(Class service,int port){
        if(service==null){
            throw new RuntimeException("服务接口未传参");
        }
        //首先进行new对象，再根据传过来的方法进行调用，将调用的结果通过scoket返回

        if(!(port>0)){
            port = ProviderReflect.initPort;
        }



        return null;
    }


}
