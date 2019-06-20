package manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:19
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages={"manager.feign"})
public class ManagerApp {
    public static void main(String[] rags) {
        SpringApplication.run(ManagerApp.class, rags);
    }
}
