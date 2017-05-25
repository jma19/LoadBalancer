package com.uci.routing;

import com.uci.mode.Request;
import com.uci.mode.Response;
import com.uci.mode.ServerInstance;

import java.util.List;

/**
 * Created by junm5 on 5/24/17.
 */
public class PriorityBalancer implements ILoadBalancer {

    @Override
    public void reloadCache(List<ServerInstance> serverInstanceList) {

    }

    @Override
    public boolean remove(ServerInstance server) {
        return false;
    }

    @Override
    public ServerInstance getServer(int index) {
        return null;
    }

    @Override
    public Response distributeRequest(Request request) {
        return null;
    }
}
