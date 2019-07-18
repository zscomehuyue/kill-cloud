package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by eloancn on 2017/5/31.
 * FIXME mvn clean package  spring-boot:repackage
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {
    public static void main(String[] rags) {
        SpringApplication.run(EurekaServer.class, rags);
    }
}
