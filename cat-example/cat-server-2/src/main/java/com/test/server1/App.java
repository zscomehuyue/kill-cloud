package com.test.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:08
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages={"com.test.server1.feign"})
public class App {

    public static void main(String args[]) {
        SpringApplication.run(App.class);
    }

}
