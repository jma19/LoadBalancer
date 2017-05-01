package com.uci.heartbeat;

import com.uci.mode.Server;
import com.uci.utils.HttpUtils;

/**
 * Created by junm5 on 5/1/17.
 */
public class SlowCheckTask extends HeartbeatTask {
    //10 minutes check once
    private final long MAX_LIMIT = 2 * 60 * 1000;

    public SlowCheckTask(Server server, int[] schedule, boolean backOff) {
        super(server, schedule, backOff);
    }

    @Override
    public void run() {
        while (true) {
            try {
                HttpUtils.get("");
                return;
            } catch (Exception exp) {
                System.out.println(String.format("slow check failed i = %s!!!", i));
            }
        }
    }

}

