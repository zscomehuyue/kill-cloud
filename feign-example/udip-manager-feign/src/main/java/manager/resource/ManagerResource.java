package manager.resource;

import api.ManagerApi;
import manager.feign.NodeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:15
 */
@RestController
public class ManagerResource implements ManagerApi {

    @Autowired
    private NodeFeignClient nodeFeignClient;

    @Override
    public String getManager(@PathVariable("id") Long id) {
        return "manager_" + id;
    }

    public String test(@PathVariable("id") Long id) {
        return nodeFeignClient.getNode(id);
    }
}
