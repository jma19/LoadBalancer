package com.uci.routing;

import com.uci.mode.Request;
import com.uci.mode.Server;

/**
 * Created by junm5 on 4/25/17.
 */
public interface ILoadBalancer {

    boolean addServer(Server server);

    boolean remove(Server server);

    Server getServer(int index);

    void distributeRequest(Request request);
}

