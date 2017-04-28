package com.uci.heartbeat;

import com.uci.mode.Server;

import java.sql.Timestamp;

/**
 * Created by junm5 on 4/28/17.
 */
public class HeatbeatChecker implements Runnable {
    private Server server;
    private Timestamp pre;

    public HeatbeatChecker(Server server) {
        this.server = server;
    }

    @Override
    public void run() {

    }
}
