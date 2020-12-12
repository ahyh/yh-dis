package com.yh.global.tx.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorFactory {

    /**
     * corePoolSize 核心线程数，池中所保存的线程数，包括空闲线程。
     */
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * maximumPoolSize - 池中允许的最大线程数(采用LinkedBlockingQueue时没有作用)。
     */
    private static final int maximumPoolSize = corePoolSize;
    /**
     * keepAliveTime -当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间，线程池维护线程所允许的空闲时间
     */
    private static final int keepAliveTime = 30;
    /**
     * 执行前用于保持任务的队列（缓冲队列）
     */
    private static final int capacity = corePoolSize * 20;

    private ThreadPoolExecutorFactory() {
    }

    private static class ThreadPoolExecutorHolder {
        private final static ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(capacity), new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public static ExecutorService getThreadPoolExecutor() {
        return ThreadPoolExecutorHolder.EXECUTOR_SERVICE;
    }

}