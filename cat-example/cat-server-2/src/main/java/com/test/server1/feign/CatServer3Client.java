package com.test.server1.feign;

import com.suixingpay.starter.cat.Metric;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:31
 */

//@FeignClient(value = "cat-server-3")
public interface CatServer3Client {

    @Metric
    @GetMapping("cat")
    String a();
}
