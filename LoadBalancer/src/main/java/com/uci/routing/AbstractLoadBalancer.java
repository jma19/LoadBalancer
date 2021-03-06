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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.uci.mode.HttpMethodType.GET;
import static com.uci.mode.HttpMethodType.POST;
import static com.uci.mode.InvokeType.ASY;
import static com.uci.mode.RequestStatus.EXECUTING;

/**
 * Created by junm5 on 5/27/17.
 */
public abstract class AbstractLoadBalancer implements ILoadBalancer {

    protected final static int LIMIT_TIMES = 20;
    private final Logger log = LoggerFactory.getLogger(RoundRobinBalancer.class);

    @Autowired
    protected RequestServiceDao requestServiceDao;

    @Autowired
    protected AsyDispatcher asyDispatcher;

    @PostConstruct
    private void init() {
        Thread thread = new Thread(asyDispatcher);
        thread.start();
    }

    protected void dispatchFailedServer(Collection<ServerInstance> serverInstanceList, Collection<ServerInstance> temp) {
        for (ServerInstance serverInstance : temp) {
            if (!serverInstanceList.contains(serverInstance)) {
                //获取所有调度到 这台机器上请求，然后将其调度到其他机器上, 数据库port要index
                log.info("server down [" + serverInstance + "]");
                List<Request> requests = requestServiceDao.queryAllUnderExRequest(serverInstance);
                if (requests == null || requests.isEmpty()) {
                    log.info("no under executing task on machine " + serverInstance);
                    return;
                }
                for (Request request : requests) {
                    ScheduleTask.submit(() -> {
                                try {
                                    request.setRemark("Server Down");
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

        if (ASY.getValue() == request.getInvokeType()) {
            if (request.getId() != null) {
                log.info("redistribute request : " + request);
                request.increaseReTimes();
                log.info("retry " + request.getRetryTimes() + " times.......");
                requestServiceDao.updateRequest(request);
            } else {
                //first execute.
                String params = JsonUtils.toJson(request.getPairs());
                request.setStatus(EXECUTING.getStatus()).setRemark(EXECUTING.getRemark()).setParams(params).setRetryTimes(0);
                requestServiceDao.insertRequest(request);
            }
            request.getPairs().add(new BasicNameValuePair("requestId", request.getId() + ""));
            res = HttpUtils.post(url.toString(), request.getPairs());
        } else {
            if (GET.getValue() == request.getType()) {
                if (request.getParams() != null) {
                    url.append("/").append(request.getParams());
                }
                res = HttpUtils.get(url.toString());
            } else if (POST.getValue() == request.getType()) {
                res = HttpUtils.post(url.toString(), request.getPairs());
            }
        }
        log.info("request information: " + request);
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


    protected FailureRequest getFailureRequest(Request request) {
        return new FailureRequest()
                .setIp(request.getIp())
                .setPath(request.getPath())
                .setPort(request.getPort())
                .setRemark(request.getRemark())
                .setRequestId(request.getId());
    }

}
