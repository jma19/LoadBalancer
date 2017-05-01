package com.uci.heartbeat;

import java.util.Timer;

/**
 * Created by junm5 on 4/30/17.
 */
public class HeartBeatTimer {
    private Timer timer = new Timer();
    private HeartbeatTask heartbeatTask;

    public HeartBeatTimer(HeartbeatTask heartbeatTask) {
        this.heartbeatTask = heartbeatTask;
    }

    public void scheduleAtFixedRate(long start, long peroid) {
        timer.scheduleAtFixedRate(heartbeatTask, start, peroid);
    }

    public void cancel() {
        timer.cancel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeartBeatTimer that = (HeartBeatTimer) o;

        return heartbeatTask != null ? heartbeatTask.equals(that.heartbeatTask) : that.heartbeatTask == null;

    }

    @Override
    public int hashCode() {
        return heartbeatTask != null ? heartbeatTask.hashCode() : 0;
    }
}

