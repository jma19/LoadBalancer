package com.uci.heartbeat;

import com.uci.mode.Server;
import com.uci.utils.HttpUtils;

import java.util.TimerTask;

/**
 * Created by junm5 on 4/28/17.
 */
public class HeartbeatTask extends TimerTask {
    private final Server server;
    private final int[] schedule;
    private int index = 0;
    private boolean backOff = false;

    public HeartbeatTask(Server server, int[] schedule, boolean backOff) {
        this.server = server;
        this.schedule = schedule;
        this.backOff = backOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartbeatTask that = (HeartbeatTask) o;
        return server != null ? server.equals(that.server) : that.server == null;
    }

    @Override
    public int hashCode() {
        return server != null ? server.hashCode() : 0;
    }

    @Override
    public void run() {
        boolean res = false;
        while (res && index < schedule.length) {
            try {
                HttpUtils.get(server.toString());
                res = true;
            } catch (Exception exp) {
                System.out.println("heartbeat fails:" + server);
                if (backOff) {
                    delay(schedule[index]);
                    index++;
                }
            }
        }
        // heart check failed
        if (!res) {

        }
        index = 0;
    }

    protected void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
