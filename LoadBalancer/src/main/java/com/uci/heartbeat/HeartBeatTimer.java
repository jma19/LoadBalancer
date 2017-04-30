package com.uci.heartbeat;

import java.util.Timer;

/**
 * Created by junm5 on 4/30/17.
 */
public class HeartBeatTimer {
    private Timer timer = new Timer();
    private HeartbeatTask heatbeatChecker;

    public HeartBeatTimer(HeartbeatTask heatbeatChecker) {
        this.heatbeatChecker = heatbeatChecker;
    }

    public void scheduleAtFixedRate(long start, long peroid) {
        timer.scheduleAtFixedRate(heatbeatChecker, start, peroid);
    }

    public void cancel() {
        timer.cancel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeartBeatTimer that = (HeartBeatTimer) o;

        return heatbeatChecker != null ? heatbeatChecker.equals(that.heatbeatChecker) : that.heatbeatChecker == null;

    }

    @Override
    public int hashCode() {
        return heatbeatChecker != null ? heatbeatChecker.hashCode() : 0;
    }
}

