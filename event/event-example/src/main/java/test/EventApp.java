package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: zscome
 * DateTime: 2019-07-31 18:41
 */
@SpringBootApplication
@EnableAsync
public class EventApp {
    public static void main(String args[]) {
        SpringApplication.run(EventApp.class);
    }
}
