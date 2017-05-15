package com.uci.routing;

import com.uci.mode.Request;
import com.uci.mode.ServerInstance;

import java.util.List;

/**
 * Created by junm5 on 4/25/17.
 */
public interface ILoadBalancer {

    void reloadCache(List<ServerInstance> serverInstanceList);

    boolean remove(ServerInstance server);

    ServerInstance getServer(int index);

    void distributeRequest(Request request);
}

