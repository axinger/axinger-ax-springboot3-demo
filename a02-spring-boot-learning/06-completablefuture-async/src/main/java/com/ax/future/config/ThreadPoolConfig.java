package com.ax.future.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean(name = "smsExecutor")
    public Executor smsExecutor(
            @Value("${thread.pool.coreSize}") Integer coreSize,
            @Value("${thread.pool.maxSize}") Integer maxSize,
            @Value("${thread.pool.keepalive}") Integer keepalive,
            @Value("${thread.pool.blockQueueSize}") Integer blockQueueSize) {
//        return new ThreadPoolExecutor(coreSize,
//                maxSize,
//                keepalive,
//                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(blockQueueSize),
//                new ThreadPoolExecutor.DiscardPolicy());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(coreSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxSize);
        // 保活时间
        executor.setKeepAliveSeconds(keepalive);
        //配置队列大小
        executor.setQueueCapacity(blockQueueSize);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("短信线程池-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;

    }

    @Bean(name = "orderExecutor")
    public Executor cExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(5);
        //配置最大线程数
        executor.setMaxPoolSize(10);
        //配置队列大小
        executor.setQueueCapacity(400);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("订单线程池-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

//    @Bean
//    public Executor executor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //配置核心线程数
//        executor.setCorePoolSize(5);
//        //配置最大线程数
//        executor.setMaxPoolSize(10);
//        //配置队列大小
//        executor.setQueueCapacity(400);
//        //配置线程池中的线程的名称前缀
//        executor.setThreadNamePrefix("通用线程池-");
//        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
//        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        //执行初始化
//        executor.initialize();
//        return executor;
//    }
}