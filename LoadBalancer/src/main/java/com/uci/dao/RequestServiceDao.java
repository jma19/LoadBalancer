package com.uci.dao;

import com.uci.mode.AsyResponse;
import com.uci.mode.FailureRequest;
import com.uci.mode.Request;

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
     * update asynchronous request status and remark.
     *
     * @param response
     */
    void updateAsyRequest(AsyResponse response);


    /**
     * when we need to re-distribute the request, we need to update resquest information.
     *
     * @param request
     */
    void updateRetry(Request request);

}

