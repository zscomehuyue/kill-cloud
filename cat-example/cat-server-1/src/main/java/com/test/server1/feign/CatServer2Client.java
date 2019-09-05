package com.test.server1.feign;

import com.suixingpay.starter.cat.Metric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:31
 */

@FeignClient(value = "cat-server-2" )
public interface CatServer2Client {
    @Metric
    @GetMapping("cat")
    String a();
}
