package node.resource;

import api.NodeApi;
import api.User;
import node.feign.ManagerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:19
 */
@RestController
public class NodeResource implements NodeApi {
    @Autowired
    private ManagerFeignClient managerFeignClient;


    @Override
    public String getNode(@PathVariable("id") Long id) {
        return "node_" + id;
    }


    public String test(@PathVariable("id") Long id) {
        return managerFeignClient.getManager(id);
    }

    @Override
    public User getUser(@RequestBody User user) {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User test(User user) {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
