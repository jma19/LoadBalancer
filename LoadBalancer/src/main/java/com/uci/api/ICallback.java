package com.uci.api;

import com.uci.conf.DataBaseConfig;
import com.uci.dao.RequestServiceDao;
import com.uci.mode.AsyResponse;
import com.uci.mode.Request;
import com.uci.mode.Response;
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

    /**
     * @param id
     * @param status
     * @param remark
     * @return
     */
    @RequestMapping(path = "/callback", method = RequestMethod.POST)
    public Response callback(@RequestParam Long id, @RequestParam Integer status, @RequestParam String remark) {
        try {
            log.info(String.format("receive data [id = %s, status=%s, remark=%s]", id, status, remark));
            Request request = new Request().setStatus(status).setId(id).setRemark(remark);
            requestServiceDao.updateRequest(request);
            if (status == 3) {

            }
            return Response.success(null);
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            return Response.fail("Server Inner Error, DB operation!!");
        }
    }

}
