package com.test.server1.mock;

import com.suixingpay.starter.cat.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:05
 */

@Service
public class CatServer3_B {
    @Autowired
    private CatServer3_C catServer3C;

    @Metric
    public String b() {

        return catServer3C.c();
    }
}
