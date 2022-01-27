package com.ax.juc.controller;


import com.ax.juc.service.FutureTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@Slf4j
public class JUCController {

    @Autowired
    FutureTaskService taskService;


    @RequestMapping("/async")
    public Object async() throws InterruptedException, ExecutionException {

        // 开始时间
        long stime = System.nanoTime();

        taskService.testAsync1();
        Future<String> r1 = taskService.asyncGetResult1();
        Future<String> r2 = taskService.asyncGetResult2();

        Map map = new HashMap();
        map.put("r1", r1.get());
        map.put("r2", r2.get());

        log.info("Controller执行完毕");
        // 结束时间
        long etime = System.currentTimeMillis();

        float seconds = (etime - stime) / 1000F;
        System.out.println("seconds = " + seconds);
        log.info("执行时长：{} 秒", seconds);
        return map;
    }



    @RequestMapping("/completableFuture")
    public Object test2() throws ExecutionException, InterruptedException {

        // 开始时间
        long sTime = System.currentTimeMillis();

        CompletableFuture<String> future1 = taskService.asyncTes1(5);
        log.info("completableFuture 等待1");
        CompletableFuture<String> future2 = taskService.asyncTes2(8);
        log.info("completableFuture 等待2");

        /// 这个会超时
        CompletableFuture<String> future3 = taskService.asyncTes3(10);
        log.info("completableFuture 等待2");


        Map map = new HashMap();

//        map.put("r2", r2.get());

        future1.whenComplete((k, v)->{
            map.put("future1", k);
        });

        future2.whenComplete((k, v)->{
            map.put("future2", k);
        });
        future3.whenComplete((k, v)->{
            map.put("future3", k);
        });
        CompletableFuture.allOf(future1,future2,future3).join();


        log.info("completableFuture 都有结果 = "+map);
        // 结束时间
        long eTime = System.currentTimeMillis();

        float seconds = (eTime - sTime) / 1000F;
        log.info("执行时长：{} 秒, 应该小于3秒}", seconds);
        return map;
    }

    @RequestMapping("/async3")
    public Object test3() throws ExecutionException, InterruptedException {
        return taskService.asyncTes1And2().get();
    }

    @RequestMapping("/async31")
    public Object test3_1() throws ExecutionException, InterruptedException {
        return taskService.asyncTes1And2_2();
    }


    @RequestMapping("/async4")
    public Object test4() throws ExecutionException, InterruptedException {
        return taskService.asyncTes1And2().join();
    }


    @RequestMapping("/completableAll")
    public Object completableAll()  {
        final Object all = taskService.completableAll();
        System.out.println("all = "+all);
        return all;
    }

}
