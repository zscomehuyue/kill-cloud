package com.test.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:08
 */

@SpringBootApplication
@EnableEurekaClient
public class App {

    public static void main(String args[]) {
        SpringApplication.run(App.class);
    }

}
