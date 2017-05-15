package com.uci.discovery;

import com.google.common.collect.Lists;
import com.uci.mode.InstanceDetails;
import com.uci.mode.ServerInstance;
import com.uci.routing.ILoadBalancer;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 5/13/17.
 */
@Component
public class ServiceDiscoveryListener implements ServiceCacheListener {

    private static final String PATH = "/discovery";
    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    private CuratorFramework client = null;
    private ServiceDiscovery<InstanceDetails> serviceDiscovery = null;
    private ServiceCache<InstanceDetails> serviceCache;
    private static final String CACHE_NAME = "TIPPER";

    private static final String connection = "localhost:2181";

    @Autowired
    private ILoadBalancer iLoadBalancer;

    @PostConstruct
    public void init() {
        client = CuratorFrameworkFactory.newClient(connection, retryPolicy);
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class).client(client).basePath(PATH).serializer(serializer).build();
        serviceCache = serviceDiscovery.serviceCacheBuilder().name(CACHE_NAME).build();
        serviceCache.addListener(this);
    }

    private List<ServiceInstance<InstanceDetails>> queryAllServiceInstance(String cachName) throws Exception {
        Collection<ServiceInstance<InstanceDetails>> instances = serviceDiscovery.queryForInstances(CACHE_NAME);
        return Lists.newArrayList(instances);
    }

    @PreDestroy
    public void unregisterService() {
        CloseableUtils.closeQuietly(serviceCache);
        CloseableUtils.closeQuietly(serviceDiscovery);
        CloseableUtils.closeQuietly(client);
    }

    @Override
    public void cacheChanged() {
        System.out.println("cacheChanged");
        try {
            List<ServiceInstance<InstanceDetails>> serviceInstances = queryAllServiceInstance(CACHE_NAME);
            if (serviceInstances != null && !serviceInstances.isEmpty()) {
                List<ServerInstance> serverInstances = serviceInstances.stream()
                        .map(serviceInstance -> getServerInstance(serviceInstance))
                        .collect(Collectors.toList());
                iLoadBalancer.reloadCache(serverInstances);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ServerInstance getServerInstance(ServiceInstance<InstanceDetails> serviceInstance) {
        return new ServerInstance().setPort(serviceInstance.getPort())
                .setIp(serviceInstance.getAddress());
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
    }


}
