package com.uci.routing;

import com.uci.mode.Server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by junm5 on 4/25/17.
 */
public abstract class AbstractBalancer implements ILoadBalancer {

    private final int LIMIT_SIZE = 100;

    protected final List<Server> serverCache = new CopyOnWriteArrayList<>();

    @Override
    public boolean addServer(Server server) {
        if (serverCache.size() == LIMIT_SIZE || serverCache.contains(server)) {
            return false;
        }
        return serverCache.add(server);
    }

    @Override
    public boolean remove(Server server) {
        return serverCache.remove(server);
    }

    @Override
    public Server getServer(int index) {
        return serverCache.get(index);
    }
}
