package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:19
 */
@SpringBootApplication(exclude = {EurekaClientAutoConfiguration.class})
public class FeignApp {
    public static void main(String[] rags) {
        SpringApplication.run(FeignApp.class, rags);
    }
}
