package com.uci.heartbeat;

import com.uci.mode.Server;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by junm5 on 4/28/17.
 */

public class HeartbeatManager {
    /**
     * set rules for heartbeat check
     * normal alive server: send
     */
    private static HeartbeatManager heartbeatManager = new HeartbeatManager();

    private HeartbeatManager() {

    }

    public static HeartbeatManager getHeartbeatManager() {
        return heartbeatManager;
    }

    private final static int FIX_PERIOD = 10;
    //Exponential backoff
    private final static int[] DEFAULT_SCHEDULE = {10, 20, 40, 80, 160, 320, 640, 1280};
    private final Set<HeartBeatTimer> ALIVE_TIMER = new CopyOnWriteArraySet<>();

    private final Queue<HeartBeatTimer> FEAK_DEAD_TIMER = new LinkedList<>();

    public void addHeartChecker(Server server) {
        HeartbeatTask heartbeatTask = new HeartbeatTask(server, DEFAULT_SCHEDULE, true);
        HeartBeatTimer heartBeatTimer = new HeartBeatTimer(heartbeatTask);
        ALIVE_TIMER.add(heartBeatTimer);
        heartBeatTimer.scheduleAtFixedRate(0, FIX_PERIOD);
    }

    public void remove(Server server) {

    }


}
