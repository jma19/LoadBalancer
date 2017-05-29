package com.uci.api;

import com.google.common.collect.Lists;
import com.uci.conf.DataBaseConfig;
import com.uci.dao.RequestServiceDao;
import com.uci.mode.*;
import com.uci.routing.AsyDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * when server finish executing asy task. It will call this restful api to update status of task information in DB
 * Created by junm5 on 5/19/17.
 */
@RestController
@RequestMapping
public class ICallback {

    private final Logger log = LoggerFactory.getLogger(DataBaseConfig.class);

    @Autowired
    private RequestServiceDao requestServiceDao;

    @Autowired
    private AsyDispatcher asyDispatcher;

    /**
     * @param id
     * @param status
     * @param remark
     * @return
     */
    @RequestMapping(path = "/callback", method = RequestMethod.POST)
    public Response callback(@RequestParam("id") Long id, @RequestParam("status") Integer status, @RequestParam("remark") String remark) {
        try {
            log.info(String.format("callback receive data [id = %s, status=%s, remark=%s]", id, status, remark));
            Request request = new Request().setStatus(status).setId(id).setRemark(remark);
            requestServiceDao.updateRequest(request);
            if (status == RequestStatus.FAILED.getStatus()) {
                Request newRequest = requestServiceDao.queryRequest(request.getId());
                requestServiceDao.insertFailure(getFailureRequest(newRequest, remark));
                asyDispatcher.add(Lists.newArrayList(newRequest));
            }
            return Response.success(null);
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            return Response.fail("Server Inner Error, DB operation!!");
        }
    }

    private FailureRequest getFailureRequest(Request request, String remark) {
        return new FailureRequest()
                .setPort(request.getPort())
                .setRemark(remark)
                .setPath(request.getPath())
                .setIp(request.getIp())
                .setRequestId(request.getId());

    }

}
