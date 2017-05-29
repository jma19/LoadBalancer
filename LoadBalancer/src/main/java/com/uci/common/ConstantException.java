package com.uci.common;

import com.uci.mode.LBException;

/**
 * Created by junm5 on 5/20/17.
 */
public interface ConstantException {
    LBException TIME_OUT_EXCEPTION = new LBException("TIME_OUT");

    LBException NO_SERVER_AVAILABLE = new LBException("No Server Available!");
}
