package com.uci.test;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.junit.Assert;
import org.junit.Test;

import java.io.Closeable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by junm5 on 5/13/17.
 */
public class ServiceCacheTest {
    @Test
    public void testUpdate() throws Exception {
        final String connection = "localhost:2181";

        List<Closeable> closeables = Lists.newArrayList();
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient(connection, new RetryOneTime(1));
            closeables.add(client);
            client.start();

            ServiceInstance<String> instance = ServiceInstance.<String>builder().payload("thing").name("test").port(10064).build();
            ServiceDiscovery<String> discovery = ServiceDiscoveryBuilder.builder(String.class).basePath("/test").client(client).thisInstance(instance).build();
            closeables.add(discovery);
            discovery.start();

            final CountDownLatch latch = new CountDownLatch(1);
            ServiceCache<String> cache = discovery.serviceCacheBuilder().name("test").build();

            closeables.add(cache);
            ServiceCacheListener listener = new ServiceCacheListener() {
                @Override
                public void cacheChanged() {
                    System.out.println("cache changed");
                    latch.countDown();
                }

                @Override
                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                }
            };
            cache.addListener(listener);
            cache.start();

            instance = ServiceInstance.<String>builder().payload("changed").name("test").port(10064).id(instance.getId()).build();
            discovery.updateService(instance);

            Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));

            Assert.assertEquals(cache.getInstances().size(), 1);
            Assert.assertEquals(cache.getInstances().get(0).getPayload(), instance.getPayload());
        } finally {
            Collections.reverse(closeables);
            for (Closeable c : closeables) {
                CloseableUtils.closeQuietly(c);
            }
        }
    }

    @Test
    public void name() throws Exception {
        int[] nums = {3, 1, 4, 1, 5};
        Arrays.sort(nums);
        int k = 0;
        int i = 0, j = 1, cnt = 0;
        while (j < nums.length) {
            while (i < j && nums[i] + k < nums[j]) {
                i++;
            }
            if (i != j && nums[i] + k == nums[j]) {
                cnt++;
            }
            j++;
            while (j < nums.length && nums[j] == nums[j - 1]) {
                j++;
            }
        }
        System.out.println(cnt);
    }

    private static String labelSyntax = "<[^<>\\\\]*>";

    @Test
    public void name1() throws Exception {
        labelSyntax = "";
    }

}