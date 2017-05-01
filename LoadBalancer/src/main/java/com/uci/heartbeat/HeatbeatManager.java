package com.uci.heartbeat;

import com.uci.mode.Server;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;

/**
 * Created by junm5 on 4/28/17.
 */
public class HeatbeatManager {

    /**
     * set rules for heartbeat check
     * normal alive server: send
     */
    private final static int FIX_PEROID = 500;
    //Exponential backoff
    private final static int[] DEFAULT_SCHDULE = {10, 20, 40, 80, 160, 320, 640, 1280};
    private Set<HeartBeatTimer> checkerList = new CopyOnWriteArraySet<>();


    public void addServer(Server server) {
        HeartbeatTask heatbeatChecker = new HeartbeatTask(server, DEFAULT_SCHDULE);

        HeartBeatTimer heartBeatTimer = new HeartBeatTimer(heatbeatChecker);
        checkerList.add(heartBeatTimer);
        heartBeatTimer.scheduleAtFixedRate(0, FIX_PEROID);
    }


}
