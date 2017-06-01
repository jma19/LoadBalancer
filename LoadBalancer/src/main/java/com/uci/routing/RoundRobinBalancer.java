package com.uci.routing;

import com.uci.common.ConstantException;
import com.uci.dao.RequestServiceDao;
import com.uci.mode.*;
import com.uci.utils.HttpUtils;
import com.uci.utils.JsonUtils;
import com.uci.utils.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.uci.mode.RequestStatus.EXECUTING;
import static com.uci.mode.RequestStatus.FAILED;
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
    public Response distributeRequest(Request request) throws LBException {
        int curIndex;
        while ((curIndex = nextServerSlot()) != -1) {
            try {
                ServerInstance server = getServer(curIndex);
                request.setIp(server.getIp()).setPort(server.getPort());
                return dispute(request);
            } catch (Exception exp) {
                log.error(exp.getMessage(), exp);
                request.increaseReTimes();

                if (request.getInvokeType() == InvokeType.ASY.getValue()) {
                    request.setRemark(exp.getMessage()).setStatus(FAILED.getStatus());
                    requestServiceDao.updateRequest(request);
                    requestServiceDao.insertFailure(getFailureRequest(request));
                } else {
                    if (request.getRetryTimes() == 20) {
                        log.error("distribute failed, over distribute time limit!!");
                        return Response.fail("Syn Request, distribute failed!! distribute time > " + LIMIT_TIMES);
                    }
                }
            }
        }
        log.info("No Server Available");
        request.increaseReTimes();
        request.setRemark("No Server Available");
        requestServiceDao.updateRequest(request);
        throw ConstantException.NO_SERVER_AVAILABLE;
    }


}

