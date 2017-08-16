package com.psh.framework;

import org.apache.commons.lang3.reflect.MethodUtils;



import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shitou on 17-8-14.
 */
public class ProviderReflect {
    private static final int initPort = 8000;

    //来个懒加载进行进行初始化线程池
    private static class ExecutorServiceProd{
        private static ExecutorService executorService = Executors.newCachedThreadPool();
    }

    public static void provider(final Object service, int port){
        if(service==null){
            throw new RuntimeException("服务接口未传参");
        }
        //首先进行new对象，再根据传过来的方法进行调用，将调用的结果通过scoket返回

        if(!(port>0)){
            port = ProviderReflect.initPort;
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ExecutorService executorService = ExecutorServiceProd.executorService;

            while (true){
                final Socket socket = serverSocket.accept();

                executorService.execute(() -> {
                    ObjectInputStream objectInputStream = null;
                    ObjectOutputStream objectOutputStream = null;
                    try {
                        //获取对象流
                        final InputStream inputStream = socket.getInputStream();
                        final OutputStream outputStream = socket.getOutputStream();
                        objectInputStream = new ObjectInputStream(inputStream);
                        //获取方法
                        String method = objectInputStream.readUTF();
                        //获取方法参数
                        ObjectInputStream objectInputStreamArgs = new ObjectInputStream(inputStream);
                        Object args = objectInputStreamArgs.readObject();

                        //执行方法  三个参数 对象target  ，方法名， 参数列表

                        Object result = MethodUtils.invokeExactMethod(service, method, args);

                        //将方法调用返回值返回
                        objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }finally {
                        if(objectOutputStream!=null){
                            try {
                                objectOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if(objectInputStream!=null){
                            try {
                                objectInputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ;
    }


}
