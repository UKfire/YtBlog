package com.ytying.ytblog.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by UKfire on 15/11/21.
 */
public class ThreadUtil {

    private static final int MAX_THREAD_COUNT = 10;
    private static ExecutorService threadCache = new ThreadPoolExecutor(5,MAX_THREAD_COUNT,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());

    public static void execute(Runnable runnable){
        threadCache.submit(runnable);
    }

    public static void sleep(int mills){
        try {
            Thread.sleep(mills);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
