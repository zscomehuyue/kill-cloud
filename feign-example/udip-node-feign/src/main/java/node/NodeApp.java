package node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:22
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages={"node.feign"})
public class NodeApp {
    public static void main(String[] rags) {
        SpringApplication.run(NodeApp.class, rags);
    }
}
