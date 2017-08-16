package com.psh.framework;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * Created by shitou on 17-8-14.
 * 服务消费代理接口，因为是代理接口，1.  泛型代替工厂 2 选择通信机制为scoket
 */
public class ConsumerProxy {
    public static <T>T consume(final Class<T> tClass,final String ip,final int port){
        //通过scoket传入接口对象，方法名和参数，返回远程端返回的值
        return (T)Proxy.newProxyInstance(tClass.getClassLoader(),tClass.getInterfaces(),(proxy,methods,arguments)->{
            //方法执行器
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                new Socket(ip,port);
                inputStream = socket.getInputStream();

                //写入方法名
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeUTF(methods.getName());
                //写入方法参数
                objectOutputStream.writeObject(arguments);

                //处理 远程调用返回的结果
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object result = objectInputStream.readObject();
                if(result instanceof Throwable){
                    throw (Throwable) result;
                }
                return result;
            }finally {
                if(inputStream!=null){
                    inputStream.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
                if(socket!=null){
                    socket.close();
                }
            }

        });

    }
}
