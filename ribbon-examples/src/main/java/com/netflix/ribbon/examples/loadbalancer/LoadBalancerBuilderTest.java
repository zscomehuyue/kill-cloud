package com.netflix.ribbon.examples.loadbalancer;

import com.alibaba.fastjson.JSON;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import org.apache.commons.configuration.Configuration;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;

import static org.junit.Assert.*;

/**
 * @author: zscome
 * DateTime: 2019-08-13 11:27
 */
public class LoadBalancerBuilderTest {

    @Test
    public void buildDynamicServerListLoadBalancer() {
        ZoneAwareLoadBalancer<Server> loadBalancer = LoadBalancerBuilder.newBuilder()
                .buildDynamicServerListLoadBalancer();
        System.out.println(JSON.toJSONString(loadBalancer, true));
    }

    @Test
    public void buildDynamicServerListLoadBalancerWithUpdater() {
        ZoneAwareLoadBalancer<Server> loadBalancer = LoadBalancerBuilder.newBuilder()
                .buildDynamicServerListLoadBalancerWithUpdater();
        System.out.println(JSON.toJSONString(loadBalancer, true));
    }

    /**
     * FIXME 服务连接配置、连接池配置等
     * 通过
     */
    @Test
    public void buildLoadBalancerFromConfigWithReflection() {

        ILoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder()
                .withClientConfig(IClientConfig.Builder.newBuilder()
                        .withReadTimeout(3000)
                        .build())
                .buildLoadBalancerFromConfigWithReflection();
        loadBalancer.getAllServers().forEach(s -> {
            System.err.println("server:" + s);
        });
        System.out.println(JSON.toJSONString(loadBalancer, true));
    }

    //https://www.programcreek.com/java-api-examples/index.php?api=com.netflix.loadbalancer.LoadBalancerBuilder
    @Test
    public void buildFixedServerListLoadBalancer() {

        ILoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder()
                .buildFixedServerListLoadBalancer(Lists.newArrayList(new Server("www.jd.com:80")));

        System.out.println(JSON.toJSONString(loadBalancer, true));
    }

    public static class NiwsClientConfig extends DefaultClientConfigImpl {
        public NiwsClientConfig() {
            super();
        }

        @Override
        public String getNameSpace() {
            return "niws.client";
        }
    }

    @Test
    public void dd() {
        Configuration config = ConfigurationManager.getConfigInstance();
        config.setProperty("client1.niws.client." + IClientConfigKey.Keys.DeploymentContextBasedVipAddresses, "dummy:7001");
        config.setProperty("client1.niws.client." + IClientConfigKey.Keys.InitializeNFLoadBalancer, "true");
        config.setProperty("client1.niws.client." + IClientConfigKey.Keys.NFLoadBalancerClassName, DynamicServerListLoadBalancer.class.getName());
        config.setProperty("client1.niws.client." + IClientConfigKey.Keys.NFLoadBalancerRuleClassName, RoundRobinRule.class.getName());
        config.setProperty("client1.niws.client." + IClientConfigKey.Keys.NIWSServerListClassName, DiscoveryEnabledNIWSServerList.class.getName());
        config.setProperty("client1.niws.client." + IClientConfigKey.Keys.NIWSServerListFilterClassName, ZoneAffinityServerListFilter.class.getName());
        config.setProperty("client1.niws.client." + IClientConfigKey.Keys.ServerListUpdaterClassName, PollingServerListUpdater.class.getName());

        IClientConfig clientConfig = IClientConfig.Builder
                .newBuilder(NiwsClientConfig.class, "client1")
                .build();
        clientConfig.getProperties()
                .entrySet()
                .forEach(en -> {
                    System.err.println(en.getKey() + " " + en.getValue());
                });
    }


    @Test
    public void fllownredirect() {
        HttpURLConnection httpURLConnection = new HttpURLConnection(null) {
            @Override
            public void connect() throws IOException {

            }

            @Override
            public void disconnect() {

            }

            @Override
            public boolean usingProxy() {
                return false;
            }
        };

        httpURLConnection.setFollowRedirects(false);
        Field followRedirects = ReflectionUtils.findField(HttpURLConnection.class, "followRedirects");
        followRedirects.setAccessible(true);
        Object value = ReflectionUtils.getField(followRedirects, httpURLConnection);
        System.out.println("==" + value);
    }
}
