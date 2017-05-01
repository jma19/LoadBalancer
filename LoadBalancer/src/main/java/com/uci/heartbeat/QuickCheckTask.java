package com.uci.heartbeat;

import com.uci.utils.HttpUtils;

/**
 * Created by junm5 on 5/1/17.
 */

public class QuickCheckTask extends HeartbeatTask {

    private final int QUICK_CHECK_TIME = 20;
    private final int DELAY_TIME = 100;

    public QuickCheckTask() {
        super(null, null, false);
    }

    @Override
    public void run() {
        int i = 1;
        while (i <= QUICK_CHECK_TIME) {
            try {
                HttpUtils.get("");
                return;
            } catch (Exception exp) {
                System.out.println(String.format("quick check failed i = %s!!!", i));
                i++;
                delay(DELAY_TIME);
            }
        }
    }

}
