package com.test.server1.mock;

import com.suixingpay.starter.cat.Metric;
import org.springframework.stereotype.Service;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:05
 */

@Service
public class CatServer2_C {

    @Metric
    public String c() {
        return "cat-server-2";
    }

}
