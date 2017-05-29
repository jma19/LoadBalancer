package com.uci.routing;

import com.uci.dao.RequestServiceDao;
import com.uci.mode.*;
import com.uci.utils.HttpUtils;
import com.uci.utils.JsonUtils;
import com.uci.utils.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.uci.mode.RequestStatus.EXECUTING;
import static com.uci.mode.RequestStatus.FINISHING;

/**
 * Created by junm5 on 4/25/17.
 */
@Component
public class RoundRobinBalancer extends AbstractLoadBalancer {

    private final Logger log = LoggerFactory.getLogger(RoundRobinBalancer.class);

    @Autowired
    private RequestServiceDao requestServiceDao;

    private int serverIdx = 0;

    protected List<ServerInstance> serverCache = new CopyOnWriteArrayList<>();


    public RoundRobinBalancer() {
        Thread thread = new Thread(asyDispatcher);
        thread.start();
    }

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
        dispatchFailedServer(serverInstanceList, temp);
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
                request.setIp(server.getIp()).setPort(server.getPort()).setRetryTimes(0);
                return dispute(request);
            } catch (Exception exp) {
                log.error(exp.getMessage(), exp);
                request.increaseReTimes();
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

}

