package com.samton.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-11
 * 说明:
 * To change
 */
public class ThreadPoolExecutor {

    private ExecutorService mExecutorService = null;
    /** 线程池对象*/
    private static ThreadPoolExecutor instance = null;
    /**
     * 私有构造
     */
    private ThreadPoolExecutor(){
        mExecutorService = Executors.newFixedThreadPool(4);
    }
    /**
     * 单例模式
     */
    public static synchronized ThreadPoolExecutor getInstance(){
        if (instance == null){
            synchronized (ThreadPoolExecutor.class){
                if (instance == null){
                    instance = new ThreadPoolExecutor();
                }
            }
        }
        return instance;
    }
    /**
     * 执行线程操作
     * @param mRunnable 需要执行的线程
     */
    public void execute(Runnable mRunnable){
        mExecutorService.execute(mRunnable);
    }
}
