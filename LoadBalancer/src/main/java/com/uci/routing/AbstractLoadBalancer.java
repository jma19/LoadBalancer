package com.uci.routing;

import com.uci.dao.RequestServiceDao;
import com.uci.mode.*;
import com.uci.utils.HttpUtils;
import com.uci.utils.JsonUtils;
import com.uci.utils.ScheduleTask;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.uci.mode.HttpMethodType.GET;
import static com.uci.mode.InvokeType.ASY;
import static com.uci.mode.RequestStatus.EXECUTING;
import static com.uci.mode.RequestStatus.FINISHING;

/**
 * Created by junm5 on 5/27/17.
 */
public abstract class AbstractLoadBalancer implements ILoadBalancer {

    private final Logger log = LoggerFactory.getLogger(RoundRobinBalancer.class);

    @Autowired
    protected RequestServiceDao requestServiceDao;

    @Autowired
    protected AsyDispatcher asyDispatcher;

    protected void dispatchFailedServer(Collection<ServerInstance> serverInstanceList, Collection<ServerInstance> temp) {
        for (ServerInstance serverInstance : temp) {
            if (!serverInstanceList.contains(serverInstance)) {
                //获取所有调度到 这台机器上请求，然后将其调度到其他机器上, 数据库port要index
                log.info("server down [" + serverInstance + "]");
                List<Request> requests = requestServiceDao.queryAllFinishRequest(serverInstance);
                if (requests == null || requests.isEmpty()) {
                    return;
                }
                for (Request request : requests) {
                    ScheduleTask.submit(() -> {
                                try {
                                    requestServiceDao.insertFailure(getFailureRequest(request));
                                    return true;
                                } catch (Exception exp) {
                                    log.error("invoke requestServiceDao.insertFailure fail", exp);
                                    return false;
                                }
                            }
                    );
                }
                asyDispatcher.add(requests);
            }
        }
    }

    protected Response dispute(Request request) throws IOException, LBException {
        StringBuffer url = buildPath(request);
        String res = null;

        if (GET.getValue() == request.getType()) {
            if (request.getParams() != null) {
                url.append("/").append(request.getParams());
            }
            res = HttpUtils.get(url.toString());
//            request.setStatus(FINISHING.getStatus()).setRemark(FINISHING.getRemark());
//            requestServiceDao.insertRequest(request);
        }
        if (ASY.getValue() == request.getInvokeType()) {
            if (request.getId() != null) {
                request.setRetryTimes(request.getRetryTimes() + 1);
            } else {
                String params = JsonUtils.toJson(request.getPairs());
                request.setStatus(EXECUTING.getStatus()).setRemark(EXECUTING.getRemark()).setParams(params);
                requestServiceDao.insertRequest(request);
            }
            request.getPairs().add(new BasicNameValuePair("requestId", request.getId() + ""));
            res = HttpUtils.post(url.toString(), request.getPairs());
        }
        return JsonUtils.toObject(res, Response.class);
    }

    private StringBuffer buildPath(Request request) {
        return new StringBuffer()
                .append("http://")
                .append(request.getIp())
                .append(":")
                .append(request.getPort())
                .append(request.getPath());
    }


    private FailureRequest getFailureRequest(Request request) {
        return new FailureRequest()
                .setIp(request.getIp())
                .setPath(request.getPath())
                .setPort(request.getPort())
                .setRemark("Server Down")
                .setRequestId(request.getId());
    }

}
