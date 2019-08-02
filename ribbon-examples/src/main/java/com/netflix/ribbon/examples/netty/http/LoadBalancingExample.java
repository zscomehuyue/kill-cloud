package com.netflix.ribbon.examples.netty.http;

import com.alibaba.fastjson.JSON;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.RandomRule;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.client.RxClient;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import rx.Observable;
import rx.Observer;

import com.google.common.collect.Lists;
import com.netflix.ribbon.transport.netty.RibbonTransport;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;


public class LoadBalancingExample {

    static String uri = "/v1/nodes/test?id=id&name=name";

    //FIXME server 为ip+端口格式；
    static List<Server> servers = Lists.newArrayList(
            new Server("172.16.43.154:8082"),
            new Server("172.16.43.154:8089")
//            ,
//             new Server("www.baidu.com:80")
    );

    private final RetryHandler retryHandler = new DefaultLoadBalancerRetryHandler(5, 3, true);


    @Test
    public void testGet() {
        HttpClientRequest<ByteBuf> get = HttpClientRequest.createGet(uri);
        System.out.println(get.getAbsoluteUri());

        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder()
                .buildFixedServerListLoadBalancer(servers);

        LoadBalancingHttpClient<ByteBuf, ByteBuf> httpClient = RibbonTransport.newHttpClient(loadBalancer);


        HttpClientResponse<ByteBuf> last = httpClient.submit(get, retryHandler, null)
                .doOnCompleted(() -> {
                    System.err.println("Got onCompleted: ");
                })
                .doOnError(e -> {
                    System.err.println("Got onError: ");
                })
                .doOnNext(byteBufHttpClientResponse -> {
                    HttpResponseStatus status = byteBufHttpClientResponse.getStatus();
                    System.err.println("Got response: " + status.code());
                    System.err.println("Got response: " + JSON.toJSONString(byteBufHttpClientResponse, true));
                })
                .toBlocking()
                .last();

        System.out.println(JSON.toJSONString(last, true));

    }

    @Test
    public void testGet2() {
        HttpClientRequest<ByteBuf> request = HttpClientRequest.createGet(uri);

        LoadBalancingHttpClient<ByteBuf, ByteBuf> httpClient = RibbonTransport.newHttpClient(LoadBalancerBuilder
                .newBuilder()
                .withRule(new RandomRule())
                .buildFixedServerListLoadBalancer(servers));

        //FIXME 连接相关配置参数
        IClientConfig config = IClientConfig.Builder
                .newBuilder()
                .withConnectTimeout(1)// FIXME  millisecond
                .withReadTimeout(1)// FIXME  millisecond
                .build();

        httpClient.submit(request, retryHandler, config)
                .concatMap(response -> {
                    //FIXME 可以串联多个请求
                    return response.getContent();
                })
                .toBlocking()
                .subscribe(new Observer<ByteBuf>() {
                    @Override
                    public void onCompleted() {
                        System.err.println("  onCompleted-1: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("  onError-1: ");
                    }

                    @Override
                    public void onNext(ByteBuf byteBuf) {
                        System.err.println("result = " + byteBuf.toString());
                        System.err.println("result = " + JSON.toJSONString(byteBuf, true));
                    }
                });

        httpClient.submit(request, retryHandler, config)
                .toBlocking()
                .subscribe(new Observer<HttpClientResponse<ByteBuf>>() {
                    @Override
                    public void onCompleted() {
                        System.err.println("  onCompleted-2: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("  onError-2: ");
                    }

                    @Override
                    public void onNext(HttpClientResponse<ByteBuf> response) {
                        System.err.println("  onNext-2: " + response.getStatus());
                    }
                });


    }

    HttpClientRequest<ByteBuf> get = HttpClientRequest.createGet(uri);

    @Test
    public void testTcp() {
        System.out.println(get.getAbsoluteUri());

        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder()
                .buildFixedServerListLoadBalancer(servers);

        RxClient<ByteBuf, ByteBuf> tcpClient = RibbonTransport.newTcpClient(loadBalancer, null);


    }

    @Test
    public void dd() {
        Observable.from(new String[]{"a", "b"}).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.err.println("=onCompleted=");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("=onError=");
            }

            @Override
            public void onNext(String s) {
                System.err.println("=onNext=" + s);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        BaseLoadBalancer lb = LoadBalancerBuilder.newBuilder()
                .buildFixedServerListLoadBalancer(servers);

        LoadBalancingHttpClient<ByteBuf, ByteBuf> client = RibbonTransport.newHttpClient(lb);
        final CountDownLatch latch = new CountDownLatch(servers.size());
        Observer<HttpClientResponse<ByteBuf>> observer = new Observer<HttpClientResponse<ByteBuf>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(HttpClientResponse<ByteBuf> args) {
                latch.countDown();
                System.out.println("Got response: " + args.getStatus());
            }
        };
        for (int i = 0; i < servers.size(); i++) {
            HttpClientRequest<ByteBuf> request = HttpClientRequest.createGet("/");
            client.submit(request)
                    .subscribe(observer);
        }
        latch.await();
        System.out.println(lb.getLoadBalancerStats());
    }
}
