package com.uci.routing;

import com.uci.mode.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junm5 on 4/25/17.
 */
public abstract class AbstractBalancer implements ILoadBalancer {

    private final int LIMIT_SIZE = 100;

    protected final List<Server> serverCache = new ArrayList<>();

    @Override
    public synchronized boolean addServer(Server server) {
        if (serverCache.size() == LIMIT_SIZE) {
            return false;
        }
        if (!serverCache.contains(server)) {
            return serverCache.add(server);
        }
        return false;
    }

    @Override
    public synchronized boolean remove(Server server) {
        return serverCache.remove(server);
    }
}
