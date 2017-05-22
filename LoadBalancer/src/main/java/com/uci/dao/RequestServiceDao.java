package com.uci.dao;

import com.uci.mode.FailureRequest;
import com.uci.mode.Request;
import com.uci.mode.ServerInstance;

import java.util.List;

/**
 * Created by junm5 on 5/19/17.
 */
public interface RequestServiceDao {

    /**
     * insert request information
     *
     * @param request
     * @return
     */
    int insertRequest(Request request);

    /**
     * when loadbalancer fails to distribute a request, then create a failure record.
     * there are two failure situation:
     * 1. asynchronous callback fail
     * 2. synchronous invoke server fail
     *
     * @param failureRequest
     */
    void insertFailure(FailureRequest failureRequest);

    /**
     * update Request information, there are two situation
     * 1.receive asynchronous callback
     * 2.when we need to re-distribute the request, we need to update request information.
     *
     * @param request
     */
    void updateRequest(Request request);


    List<Request> queryAllFinishRequest(ServerInstance serverInstance);

}

