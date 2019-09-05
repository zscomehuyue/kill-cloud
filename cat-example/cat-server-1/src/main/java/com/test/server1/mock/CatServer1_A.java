package com.test.server1.mock;

import com.suixingpay.starter.cat.Metric;
import com.test.server1.feign.CatServer2Client;
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
public class CatServer1_A {

    @Autowired
    private CatServer1_B catServer1B;

    @Autowired
    private CatServer2Client catServer2Client;

    @Metric
    @GetMapping
    public String a() {
        return catServer1B.b();
    }

    @Metric
    @GetMapping("r")
    public String remote() {
        return catServer2Client.a();
    }


}
