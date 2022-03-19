package com.ax.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Demo13SeataOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo13SeataOrderApplication.class, args);
    }

}
