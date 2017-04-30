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

    public HeartbeatTask(Server server) {
        this.server = server;
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
        String res = HttpUtils.get(server.toString());
        if (res == null) {
            server.setStatus(Status.DEAD);
        }
    }
}
