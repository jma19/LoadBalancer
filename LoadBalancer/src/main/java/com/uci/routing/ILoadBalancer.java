package com.uci.routing;

import com.uci.mode.Request;
import com.uci.mode.Response;
import com.uci.mode.ServerInstance;

import java.util.List;

/**
 * Created by junm5 on 4/25/17.
 */
public interface ILoadBalancer {

    void reloadCache(List<ServerInstance> serverInstanceList);

    boolean remove(ServerInstance server);

    ServerInstance getServer(int index);

    Response distributeRequest(Request request);
}

