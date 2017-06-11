package com.uci.discovery;

import com.google.common.collect.Lists;
import com.uci.conf.DataBaseConfig;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(ServiceDiscoveryListener.class);

    private static final String PATH = "/discovery";
    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    private CuratorFramework client = null;
    private ServiceDiscovery<com.uci.mode.ServerInstance> serviceDiscovery = null;
    private ServiceCache<com.uci.mode.ServerInstance> serviceCache;
    private static final String CACHE_NAME = "TIPPER";

    private static final String connection = "169.234.54.56:2181";

    @Autowired
    private ILoadBalancer iLoadBalancer;

    @PostConstruct
    public void init() throws Exception {
        client = CuratorFrameworkFactory.newClient(connection, retryPolicy);
        JsonInstanceSerializer<com.uci.mode.ServerInstance> serializer = new JsonInstanceSerializer<>(com.uci.mode.ServerInstance.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(com.uci.mode.ServerInstance.class).client(client).basePath(PATH).serializer(serializer).build();
        serviceCache = serviceDiscovery.serviceCacheBuilder().name(CACHE_NAME).build();
        serviceCache.addListener(this);

        client.start();
        serviceDiscovery.start();
        serviceCache.start();
    }

    private List<ServiceInstance<com.uci.mode.ServerInstance>> queryAllServiceInstance(String cacheName) throws Exception {
        Collection<ServiceInstance<com.uci.mode.ServerInstance>> instances = serviceDiscovery.queryForInstances(cacheName);
        return Lists.newArrayList(instances);
    }

    @PreDestroy
    public void unregisterService() {
        CloseableUtils.closeQuietly(serviceCache);
        CloseableUtils.closeQuietly(serviceDiscovery);
        CloseableUtils.closeQuietly(client);
    }

    private List<ServerInstance> pre = null;

    @Override
    public void cacheChanged() {
        try {
            log.info("call cacheChanged!!!");

            List<ServiceInstance<com.uci.mode.ServerInstance>> serviceInstances = queryAllServiceInstance(CACHE_NAME);

            if (serviceInstances != null && !serviceInstances.isEmpty()) {

                List<ServerInstance> serverInstances = serviceInstances.stream()
                        .map(serviceInstance -> getServerInstance(serviceInstance))
                        .collect(Collectors.toList());

                if (pre != null && pre.equals(serverInstances)) {
                    log.info("cache does't change");
                    return;
                }
                pre = serverInstances;
                log.info("cache change, server size:" + serverInstances.size() + "," + serverInstances);
                iLoadBalancer.reloadCache(serverInstances);
            } else {
                log.info("cache is empty, no server exists");
                pre = Lists.newArrayList();
                iLoadBalancer.reloadCache(Lists.newArrayList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ServerInstance getServerInstance(ServiceInstance<com.uci.mode.ServerInstance> serviceInstance) {
        com.uci.mode.ServerInstance payload = serviceInstance.getPayload();

        return new ServerInstance()
                .setPort(payload.getPort())
                .setIp(payload.getIp())
                .setAvailableProcessor(payload.getAvailableProcessor())
                .setFreeMemory(payload.getFreeMemory());
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
    }


}
