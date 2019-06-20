package manager.feign;

import api.NodeApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author: zscome
 * DateTime: 2019-06-20 20:07
 */
@FeignClient("udip-node")
public interface NodeFeignClient extends NodeApi {
}
