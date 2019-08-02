package com.netflix.ribbon.examples.loadbalancer;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import rx.Observable;

import com.google.common.collect.Lists;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;

/**
 * @author Allen Wang
 */
public class URLConnectionLoadBalancer {

    private final ILoadBalancer loadBalancer;
    // retry handler that does not retry on same server, but on a different server
    private final RetryHandler retryHandler = new DefaultLoadBalancerRetryHandler(0, 1, true);

    public URLConnectionLoadBalancer(List<Server> serverList) {
        loadBalancer = LoadBalancerBuilder
                .newBuilder()
                .buildFixedServerListLoadBalancer(serverList);
    }

    public String call(final String path) throws Exception {
        return LoadBalancerCommand.<String>builder()
                .withLoadBalancer(loadBalancer)
                .withRetryHandler(retryHandler)
                .build()
                .submit(server -> {
                    URL url;
                    try {
                        url = new URL("http://" + server.getHost() + ":" + server.getPort() + path);
                        System.out.println("url= " + url.toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        return Observable.just(conn.getResponseMessage());
                    } catch (Exception e) {
                        return Observable.error(e);
                    }
                })
                .toBlocking()
                .first();
    }

    public LoadBalancerStats getLoadBalancerStats() {
        return ((BaseLoadBalancer) loadBalancer).getLoadBalancerStats();
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        URLConnectionLoadBalancer urlLoadBalancer = new URLConnectionLoadBalancer(Lists.newArrayList(
                new Server("jd.com", 80),
                new Server("www.baidu.com", 80)));

        for (int i = 0; i < 6; i++) {
            System.out.println(urlLoadBalancer.call("/"));
        }

        String json = JSON.toJSONString(urlLoadBalancer.getLoadBalancerStats(), true);
        System.out.println("=== Load balancer stats ===" + json);

    }
}
