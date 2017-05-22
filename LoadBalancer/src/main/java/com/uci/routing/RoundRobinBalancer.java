package com.uci.routing;

import com.uci.dao.RequestServiceDao;
import com.uci.mode.*;
import com.uci.utils.HttpUtils;
import com.uci.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by junm5 on 4/25/17.
 */
@Component
public class RoundRobinBalancer implements ILoadBalancer {

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
        serverCache = new CopyOnWriteArrayList<>(serverInstanceList);
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
                request.increasReTimes();
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

