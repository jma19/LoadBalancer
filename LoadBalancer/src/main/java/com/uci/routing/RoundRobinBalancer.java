package com.uci.routing;

import com.uci.mode.Request;
import com.uci.mode.Server;

/**
 * Created by junm5 on 4/25/17.
 */
public class RoundRobinBalancer extends AbstractBalancer {

    private int serverIdx = 0;

    // get the next slot by using the using round-robin approach
    private synchronized int nextServerSlot() {
        return (serverIdx + 1) % serverCache.size();
    }

    @Override
    public void distributeRequest(Request request) {
        int curIndex = nextServerSlot();
        Server server = getServer(curIndex);
        String resuestPath = request.setIp(server.getIp()).setPort(request.getPort()).toString();


    }
}

