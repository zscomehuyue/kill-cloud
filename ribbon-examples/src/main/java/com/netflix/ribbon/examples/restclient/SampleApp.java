/*
*
* Copyright 2013 Netflix, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

package com.netflix.ribbon.examples.restclient;

import java.net.URI;

import com.netflix.client.ClientFactory;
import com.netflix.client.IClient;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;

public class SampleApp {

    //FIXME iclient使用方式
    //FIXME 使用了命令行的方式；LoadBalancerCommand
    //FIXME executeWithLoadBalancer create LoadBalancerCommand
	public static void main(String[] args) throws Exception {
	    //FIXME 通过配置文件读取相关设置
        //FIXME DefaultClientConfigImpl 使用到了ConfigurationManager相关读取的配置

        ConfigurationManager.loadPropertiesFromResources("sample-client.properties");  // 1

//        System.err.println(ConfigurationManager.getConfigInstance().getProperty("sample-client.ribbon.listOfServers"));

        //FIXME 如何区分不同的client？通过那个key判断的啊？ 通过配置文件的ClientClassName 来区分的；
        //FIXME DefaultClientConfigImpl getDefaultClientClassname 默认返回restclient对象
        RestClient client = (RestClient) ClientFactory.getNamedClient("sample-client");  // 2

        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build(); // 3
        for (int i = 0; i < 20; i++)  {
        	HttpResponse response = client.executeWithLoadBalancer(request); // 4
        	System.out.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus());
        }

        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.err.println(lb.getLoadBalancerStats());

        ConfigurationManager.getConfigInstance().setProperty(
        		"sample-client.ribbon.listOfServers", "www.linkedin.com:80,www.jd.com:80"); // 5

        System.out.println("changing servers ...");
        Thread.sleep(3000); // 6
        for (int i = 0; i < 20; i++)  {
            HttpResponse response = null;
            try {
        	    response = client.executeWithLoadBalancer(request);
        	    System.out.println("Status code for " + response.getRequestedURI() + "  : " + response.getStatus());
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        System.out.println(lb.getLoadBalancerStats()); // 7
	}
}
