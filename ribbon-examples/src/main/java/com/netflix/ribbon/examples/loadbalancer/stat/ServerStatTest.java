package com.netflix.ribbon.examples.loadbalancer.stat;

import com.alibaba.fastjson.JSON;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerStats;
import org.junit.Test;

import java.util.Random;

/**
 * @author: zscome
 * DateTime: 2019-08-16 17:57
 */
public class ServerStatTest {

    @Test
    public void stat2() {
        ServerStats ss = new ServerStats();
        ss.setBufferSize(1000);
        ss.setPublishInterval(1000);
        ss.initialize(new Server("stonse", 80));

        Random r = new Random(1459834);
        for (int i = 0; i < 299; i++) {
            double rl = r.nextDouble() * 25.2;
            ss.noteResponseTime(rl);
            ss.incrementNumRequests();
            try {
                Thread.sleep(100);
                System.out.println("ServerStats:avg:" + ss.getResponseTimeAvg());
                System.out.println("ServerStats:90 percentile:" + ss.getResponseTime90thPercentile());
                System.out.println("ServerStats:90 percentile:" + ss.getResponseTimePercentileNumValues());

            } catch (InterruptedException e) {

            }

        }
        System.out.println("done ---");
        ss.close();
        System.out.println("ServerStats:" + JSON.toJSONString(ss, true));
    }

    /**
     * ServerStats:{
     * "activeRequestsCount":0,
     * "circuitBreakerTripped":false,
     * "failureCount":0,
     * "failureCountSlidingWindowInterval":1000,
     * "measuredRequestsCount":0,
     * "monitoredActiveRequestsCount":0,
     * "openConnectionsCount":0,
     * "responseTime10thPercentile":3.05142963639804,
     * "responseTime25thPercentile":5.562968003350293,
     * "responseTime50thPercentile":12.853929658202238,
     * "responseTime75thPercentile":17.105325555578933,
     * "responseTime90thPercentile":23.705625076795815,
     * "responseTime95thPercentile":24.306653653059136,
     * "responseTime98thPercentile":24.532853520830738,
     * "responseTime99point5thPercentile":24.532853520830738,
     * "responseTime99thPercentile":24.532853520830738,
     * "responseTimeAvg":12.64203096887857,
     * "responseTimeAvgRecent":12.271790567062824,
     * "responseTimeMax":25.155690308257867,
     * "responseTimeMin":0.021071074712381233,
     * "responseTimePercentileNumValues":30,
     * "responseTimePercentileTime":"Fri Aug 16 18:16:51 CST 2019",
     * "responseTimePercentileTimeMillis":1565950611402,
     * "responseTimeStdDev":7.372521404334344,
     * "successiveConnectionFailureCount":0,
     * "totalRequestsCount":299
     * }
     */
    @Test
    public void stat() {
        ServerStats ss = new ServerStats();
        ss.setBufferSize(3000);
        ss.setPublishInterval(3000);
        ss.initialize(new Server("stonse", 80));

        Random r = new Random(1459834);
        for (int i = 0; i < 299; i++) {
            double rl = r.nextDouble() * 25.2;
            ss.noteResponseTime(rl);
            ss.incrementNumRequests();
            try {
                Thread.sleep(100);
                System.out.println("ServerStats:avg:" + ss.getResponseTimeAvg());
                System.out.println("ServerStats:90 percentile:" + ss.getResponseTime90thPercentile());
                System.out.println("ServerStats:90 percentile:" + ss.getResponseTimePercentileNumValues());
            } catch (InterruptedException e) {
            }
        }
        System.out.println("done ---");
        ss.close();
        System.out.println("ServerStats:" + JSON.toJSONString(ss, true));
    }
}
