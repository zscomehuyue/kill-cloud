package com.test.server1.mock;

import com.suixingpay.starter.cat.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:05
 */

@Service
public class CatServer2_B {

    @Autowired
    private CatServer2_C catServer2C;

    @Metric
    public String b() {

        return catServer2C.c();
    }
}
