package com.uci.routing;

import com.uci.mode.Request;
import com.uci.mode.ServerInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by junm5 on 4/25/17.
 */
@Component
public class RoundRobinBalancer implements ILoadBalancer {

    private Object data = new Object();
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();


    private int serverIdx = 0;

    protected List<ServerInstance> serverCache = new CopyOnWriteArrayList<>();

    // get the next slot by using the using round-robin approach
    private synchronized int nextServerSlot() {
        return (++serverIdx) % serverCache.size();
    }

    @Override
    public void reloadCache(List<ServerInstance> serverInstanceList) {
        serverCache = new CopyOnWriteArrayList<>(serverInstanceList);
    }

    @Override
    public boolean remove(ServerInstance server) {
        return serverCache.remove(server);
    }

    @Override
    public ServerInstance getServer(int index) {
        return serverCache.get(index);
    }

    @Override
    public void distributeRequest(Request request) {
        int curIndex = nextServerSlot();
        ServerInstance server = getServer(curIndex);
        System.out.println("server_"+ server+ "_distribute");
    }
}

