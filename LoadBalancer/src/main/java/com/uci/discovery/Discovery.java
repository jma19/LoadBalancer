///**
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// * <p>
// * http://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
//package com.uci.discovery;
//
//import com.google.common.collect.Lists;
//import com.uci.mode.InstanceDetails;
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.curator.x.discovery.ServiceCache;
//import org.apache.curator.x.discovery.ServiceDiscovery;
//import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
//import org.apache.curator.x.discovery.ServiceInstance;
//import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
//
//import java.util.Collection;
//import java.util.List;
//
//public class Discovery {
//    private static final String PATH = "/discovery";
//    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//
//    private CuratorFramework client = null;
//    private ServiceDiscovery<InstanceDetails> serviceDiscovery = null;
//    private ServiceCache<InstanceDetails> serviceCache;
//    private static final String CACHE_NAME = "TIPPER";
//
//    public Discovery() {
//        client = CuratorFrameworkFactory.newClient(connection, retryPolicy);
//        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
//        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class).client(client).basePath(PATH).serializer(serializer).build();
//        serviceCache = serviceDiscovery.serviceCacheBuilder().name(CACHE_NAME).build();
//        serviceCache.addListener(new ServiceDiscoveryListener());
//    }
//
//    public void start() throws Exception {
//        client.start();
//        serviceDiscovery.start();
//        serviceCache.start();
//    }
//
//    private static final String connection = "localhost:2181";
//
//    private List<ServiceInstance<InstanceDetails>> getAllServiceInstance() throws Exception {
//        List<ServiceInstance<InstanceDetails>> res = Lists.newArrayList();
//        Collection<String> serviceNames = serviceDiscovery.queryForNames();// 获取所有的服务名称
//        for (String serviceName : serviceNames) {
//            System.out.println("Service Name: " + serviceName);
//            Collection<ServiceInstance<InstanceDetails>> instances = serviceDiscovery.queryForInstances(serviceName);
//            for (ServiceInstance<InstanceDetails> instance : instances) {
//                System.out.println("\t" + instance);
//            }
//        }
//        return res;
//    }
//
//
//    public void query() throws Exception {
//        Collection<String> strings = serviceDiscovery.queryForNames();
//
//        System.out.println(strings);
//    }
//
//    public static void main(String[] args) throws Exception {
//        Discovery discovery = new Discovery();
//        discovery.start();
//        while (true) {
//            Thread.sleep(2000);
//            discovery.getAllServiceInstance();
//        }
//    }
//}