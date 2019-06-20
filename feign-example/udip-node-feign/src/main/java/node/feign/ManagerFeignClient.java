package node.feign;

import api.ManagerApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:20
 */
@FeignClient("udip-manager")
public interface ManagerFeignClient extends ManagerApi {
}
