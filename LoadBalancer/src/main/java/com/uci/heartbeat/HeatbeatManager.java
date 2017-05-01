package com.uci.heartbeat;

import com.uci.mode.Server;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by junm5 on 4/28/17.
 */
public class HeatbeatManager {

    /**
     * set rules for heartbeat check
     * normal alive server: send
     */
    private final static int FIX_PERIOD = 500;
    //Exponential backoff
    private final static int[] DEFAULT_SCHEDULE = {10, 20, 40, 80, 160, 320, 640, 1280};
    private final Set<HeartBeatTimer> ALIVE_CHECKER = new CopyOnWriteArraySet<>();

    private final Queue<HeartBeatTimer> FEAK_DEAD_CHECKER = new LinkedList<>();

    //fixed
    public void addServer(Server server) {
        HeartbeatTask heatbeatChecker = new HeartbeatTask(server, DEFAULT_SCHEDULE);
        HeartBeatTimer heartBeatTimer = new HeartBeatTimer(heatbeatChecker);
        ALIVE_CHECKER.add(heartBeatTimer);
        heartBeatTimer.scheduleAtFixedRate(0, FIX_PERIOD);
    }

}
