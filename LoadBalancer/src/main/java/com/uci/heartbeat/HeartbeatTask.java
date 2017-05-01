package com.uci.heartbeat;

import com.uci.mode.Server;
import com.uci.mode.Status;
import com.uci.utils.HttpUtils;

import java.util.TimerTask;

/**
 * Created by junm5 on 4/28/17.
 */
public class HeartbeatTask extends TimerTask {
    private final Server server;
    private final int[] schedule;
    private int index = 0;

    public HeartbeatTask(Server server, int[] schedule) {
        this.server = server;
        this.schedule = schedule;
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
        Status temp = Status.DEAD;
        while (temp == Status.DEAD && index < schedule.length) {
            try {
                HttpUtils.get(server.toString());
                temp = Status.ALIVE;
            } catch (Exception exp) {
                try {
                    Thread.sleep(schedule[index]);
                    index++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        index = 0;
    }
}
