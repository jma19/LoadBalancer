package com.uci.heartbeat;

import com.uci.mode.Server;
import com.uci.utils.HttpUtils;

import java.util.concurrent.Callable;

/**
 * Created by junm5 on 4/28/17.
 */
public class HeatbeatChecker implements Callable<Boolean> {
    private final Server server;

    public HeatbeatChecker(Server server) {
        this.server = server;
    }

    @Override
    public Boolean call() throws Exception {
        String res = HttpUtils.get(server.toString());
        return res != null;
    }
}
