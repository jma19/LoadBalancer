package com.uci.dao;

import com.uci.mode.AsyResponse;
import com.uci.mode.Request;

/**
 * Created by junm5 on 5/19/17.
 */
public interface RequestServiceDao {

    int insert(Request request);

    void update(AsyResponse response);

}
