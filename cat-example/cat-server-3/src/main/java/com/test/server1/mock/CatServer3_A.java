package com.test.server1.mock;

import com.suixingpay.starter.cat.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zscome
 * DateTime: 2019-09-03 14:05
 */
@Service
@RestController
@RequestMapping("cat")
public class CatServer3_A {

    @Autowired
    private CatServer3_B catServer1B;

    @Metric
    @GetMapping
    public String a() {
        return catServer1B.b();
    }
}
