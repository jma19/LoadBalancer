package com.uci.routing;

import com.uci.conf.DataBaseConfig;
import com.uci.dao.RequestServiceDao;
import com.uci.mode.*;
import com.uci.utils.HttpUtils;
import com.uci.utils.JsonUtils;
import com.uci.utils.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by junm5 on 4/25/17.
 */
@Component
public class RoundRobinBalancer implements ILoadBalancer {

    private final Logger log = LoggerFactory.getLogger(RoundRobinBalancer.class);

    @Autowired
    private RequestServiceDao requestServiceDao;

    private int serverIdx = 0;

    protected List<ServerInstance> serverCache = new CopyOnWriteArrayList<>();

    // get the next slot by using the using round-robin approach
    private synchronized int nextServerSlot() {
        if (serverCache.size() == 0) {
            return -1;
        }
        return (++serverIdx) % serverCache.size();
    }

    @Override
    public void reloadCache(List<ServerInstance> serverInstanceList) {
        List<ServerInstance> temp = serverCache;
        serverCache = new CopyOnWriteArrayList<>(serverInstanceList);
        for (ServerInstance serverInstance : temp) {
            if (!serverInstanceList.contains(serverInstance)) {
                //获取所有调度到 这台机器上请求，然后将其调度到其他机器上, 数据库port要index
                log.info("server down [" + serverInstance + "]");
                ScheduleTask.submit(new )
            }
        }
    }

    @Override
    public boolean remove(ServerInstance server) {
        return serverCache.remove(server);
    }

    @Override
    public ServerInstance getServer(int index) {
        return serverCache.get(index);
    }

    @Override
    public Response distributeRequest(Request request) {
        int curIndex;
        while ((curIndex = nextServerSlot()) != -1) {
            try {
                ServerInstance server = getServer(curIndex);
                StringBuffer url = buildPath(request, server);
                String res = null;
                if (HttpMethodType.GET == request.getType()) {
                    if (request.getParams() != null) {
                        url.append("/" + request.getParams());
                    }
                    res = HttpUtils.get(url.toString());
                } else if (HttpMethodType.POST == request.getType()) {
                    res = HttpUtils.post(url.toString(), request.getPairs());
                }
                return JsonUtils.toObject(res, Response.class);
            } catch (Exception exp) {
                request.increaseReTimes();
//              requestServiceDao.insertFailure();
            }
        }
        return Response.fail("No Server Available!");
    }

    private FailureRequest transform(Request request, Exception exp) {
        return new FailureRequest()
                .setRemark(exp.getMessage())
                .setRequestId(request.getId())
                .setIp(request.getIp())
                .setPort(request.getPort())
                .setPath(request.getPath());
    }

    private StringBuffer buildPath(Request request, ServerInstance serverInstance) {
        return new StringBuffer()
                .append("http://")
                .append(serverInstance.getIp())
                .append(":")
                .append(serverInstance.getPort())
                .append(request.getPath());
    }
}

