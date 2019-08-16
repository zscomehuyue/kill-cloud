package com.netflix.ribbon.examples.loadbalancer;

import com.google.common.collect.Lists;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey.Keys;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.apache.commons.configuration.Configuration;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DiscoveryManager.class, DiscoveryClient.class})
@PowerMockIgnore({"javax.management.*"
        , "com.sun.jersey.*"
        , "com.sun.*"
        , "org.apache.*"
        , "weblogic.*"
        , "com.netflix.config.*"
        , "com.sun.jndi.dns.*"
        , "javax.naming.*"
        , "com.netflix.logging.*"
        , "javax.ws.*"})
public class LBBuilderTest {

    static Server expected = new Server("www.example.com", 8001);

    static List<InstanceInfo> getDummyInstanceInfo(String appName, String host, int port) {
        List<InstanceInfo> list = new ArrayList<InstanceInfo>();
        InstanceInfo info = InstanceInfo.Builder.newBuilder()
                .setAppName(appName)
                .setHostName(host)
                .setPort(port)
                .build();

        list.add(info);
        return list;
    }

    static class NiwsClientConfig extends DefaultClientConfigImpl {
        public NiwsClientConfig() {
            super();
        }

        @Override
        public String getNameSpace() {
            return "niws.client";
        }
    }

    //    @Before
    public void setupMock() {
        List<InstanceInfo> instances = getDummyInstanceInfo("dummy", expected.getHost(), expected.getPort());
        PowerMock.mockStatic(DiscoveryManager.class);
        PowerMock.mockStatic(DiscoveryClient.class);

        DiscoveryClient mockedDiscoveryClient = PowerMock.createMock(DiscoveryClient.class);
        DiscoveryManager mockedDiscoveryManager = PowerMock.createMock(DiscoveryManager.class);

        expect(DiscoveryClient.getZone((InstanceInfo) EasyMock.anyObject())).andReturn("dummyZone").anyTimes();
        expect(DiscoveryManager.getInstance()).andReturn(mockedDiscoveryManager).anyTimes();
        expect(mockedDiscoveryManager.getDiscoveryClient()).andReturn(mockedDiscoveryClient).anyTimes();


        expect(mockedDiscoveryClient.getInstancesByVipAddress("dummy:7001", false, null)).andReturn(instances).anyTimes();

        replay(DiscoveryManager.class);
        replay(DiscoveryClient.class);
        replay(mockedDiscoveryManager);
        replay(mockedDiscoveryClient);
    }

    @Test
    public void testBuildWithDiscoveryEnabledNIWSServerList() {
        IRule rule = new AvailabilityFilteringRule();
        ServerList<DiscoveryEnabledServer> list = new DiscoveryEnabledNIWSServerList("dummy:7001");
        ServerListFilter<DiscoveryEnabledServer> filter = new ZoneAffinityServerListFilter<DiscoveryEnabledServer>();

        ZoneAwareLoadBalancer<DiscoveryEnabledServer> lb = LoadBalancerBuilder.<DiscoveryEnabledServer>newBuilder()
                .withDynamicServerList(list)
                .withRule(rule)
                .withServerListFilter(filter)
                .buildDynamicServerListLoadBalancer();

        assertNotNull(lb);
        assertEquals(Lists.newArrayList(expected), lb.getAllServers());
        assertSame(filter, lb.getFilter());
        assertSame(list, lb.getServerListImpl());

        Server server = lb.chooseServer();

        // make sure load balancer does not recreate the server instance
        assertTrue(server instanceof DiscoveryEnabledServer);
    }

    //FIXME 服务更新采用ServerListUpdater来操作；
    @Test
    public void testBuildWithDiscoveryEnabledNIWSServerListAndUpdater() {
        IRule rule = new AvailabilityFilteringRule();
        ServerList<DiscoveryEnabledServer> list = new DiscoveryEnabledNIWSServerList("dummy:7001");
        ServerListFilter<DiscoveryEnabledServer> filter = new ZoneAffinityServerListFilter<DiscoveryEnabledServer>();
        ServerListUpdater updater = new PollingServerListUpdater();

        ZoneAwareLoadBalancer<DiscoveryEnabledServer> lb = LoadBalancerBuilder.<DiscoveryEnabledServer>newBuilder()
                .withDynamicServerList(list)
                .withRule(rule)
                .withServerListFilter(filter)
                .withServerListUpdater(updater)
                .buildDynamicServerListLoadBalancerWithUpdater();


        assertNotNull(lb);
        assertEquals(Lists.newArrayList(expected), lb.getAllServers());
        assertSame(filter, lb.getFilter());
        assertSame(list, lb.getServerListImpl());
        assertSame(updater, lb.getServerListUpdater());
        Server server = lb.chooseServer();
        // make sure load balancer does not recreate the server instance
        assertTrue(server instanceof DiscoveryEnabledServer);
    }

    /**
     * FIXME client1.niws.client.NFLoadBalancerClassName====com.netflix.loadbalancer.DynamicServerListLoadBalancer
     * FIXME client1.niws.client.DeploymentContextBasedVipAddresses====dummy:7001
     * FIXME client1.niws.client.NFLoadBalancerRuleClassName====com.netflix.loadbalancer.RoundRobinRule
     * FIXME client1.niws.client.ServerListUpdaterClassName====com.netflix.loadbalancer.PollingServerListUpdater
     * FIXME client1.niws.client.NIWSServerListClassName====com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList
     * FIXME client1.niws.client.InitializeNFLoadBalancer====true
     * FIXME client1.niws.client.NIWSServerListFilterClassName====com.netflix.loadbalancer.ZoneAffinityServerListFilter
     */
    @Test
    public void testBuildWithArchaiusProperties() {
        Configuration config = ConfigurationManager.getConfigInstance();
        config.setProperty("client1.niws.client." + Keys.DeploymentContextBasedVipAddresses, "dummy:7001");
        config.setProperty("client1.niws.client." + Keys.InitializeNFLoadBalancer, "true");
        config.setProperty("client1.niws.client." + Keys.NFLoadBalancerClassName, DynamicServerListLoadBalancer.class.getName());
        config.setProperty("client1.niws.client." + Keys.NFLoadBalancerRuleClassName, RoundRobinRule.class.getName());
        config.setProperty("client1.niws.client." + Keys.NIWSServerListClassName, DiscoveryEnabledNIWSServerList.class.getName());
        config.setProperty("client1.niws.client." + Keys.NIWSServerListFilterClassName, ZoneAffinityServerListFilter.class.getName());
        config.setProperty("client1.niws.client." + Keys.ServerListUpdaterClassName, PollingServerListUpdater.class.getName());

        config.getKeys().forEachRemaining(key -> {
            try {
                System.out.println(key + "====" + config.getString(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //FIXME clientName + nameSpace + subKey
        IClientConfig clientConfig = IClientConfig.Builder
                .newBuilder(NiwsClientConfig.class, "client1")
                .build();

        ILoadBalancer lb = LoadBalancerBuilder.newBuilder()
                .withClientConfig(clientConfig)
                .buildLoadBalancerFromConfigWithReflection();

        clientConfig.getProperties().entrySet().forEach(en->{
            System.out.println(en.getKey()+"="+en.getValue());
        });

        assertNotNull(lb);
        assertEquals(DynamicServerListLoadBalancer.class.getName(), lb.getClass().getName());
        DynamicServerListLoadBalancer<Server> dynamicLB = (DynamicServerListLoadBalancer<Server>) lb;
        assertTrue(dynamicLB.getServerListUpdater() instanceof PollingServerListUpdater);
        assertTrue(dynamicLB.getFilter() instanceof ZoneAffinityServerListFilter);
        assertTrue(dynamicLB.getRule() instanceof RoundRobinRule);
        assertTrue(dynamicLB.getPing() instanceof DummyPing);
        assertEquals(Lists.newArrayList(expected), lb.getAllServers());
    }

    @Test
    public void testBuildStaticServerListLoadBalancer() {
        List<Server> list = Lists.newArrayList(expected, expected);
        IRule rule = new AvailabilityFilteringRule();
        IClientConfig clientConfig = IClientConfig.Builder.newBuilder()
                .withDefaultValues()
                .withMaxAutoRetriesNextServer(3).build();

        assertEquals(3, clientConfig.get(Keys.MaxAutoRetriesNextServer).intValue());
        BaseLoadBalancer lb = LoadBalancerBuilder.newBuilder()
                .withRule(rule)
                .buildFixedServerListLoadBalancer(list);

        assertEquals(list, lb.getAllServers());
        assertSame(rule, lb.getRule());
    }
}
