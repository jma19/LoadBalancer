package com.uci.routing;

import com.uci.mode.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

import static com.uci.mode.RequestStatus.FAILED;

/**
 * Created by junm5 on 5/24/17.
 */
@Component
public class PriorityBalancer extends AbstractLoadBalancer {

    private PriorityQueue<ServerInstance> priorityQueue = new PriorityQueue<>();
    private final Logger log = LoggerFactory.getLogger(PriorityBalancer.class);


    @Override
    public void reloadCache(List<ServerInstance> serverInstanceList) {
        PriorityQueue<ServerInstance> origin = priorityQueue;
        PriorityQueue<ServerInstance> tempQueue = new PriorityQueue<>();
        tempQueue.addAll(serverInstanceList);
        priorityQueue = tempQueue;
        dispatchFailedServer(priorityQueue, origin);
    }

    @Override
    public boolean remove(ServerInstance server) {
        return priorityQueue.remove(server);
    }

    @Override
    public ServerInstance getServer(int index) {
        return priorityQueue.peek();
    }

    @Override
    public Response distributeRequest(Request request) {
        ServerInstance serverInstance = priorityQueue.peek();
        if (serverInstance == null) {
            return Response.fail("No Server available!!");
        }

        try {
            request.setPort(serverInstance.getPort()).setIp(serverInstance.getIp());
            Response response = dispute(request);
            ServerInstance temp = response.getServerInstance().setIp(serverInstance.getIp()).setPort(serverInstance.getPort());
            priorityQueue.remove(serverInstance);
            priorityQueue.offer(temp);
            return response;
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            request.increaseReTimes();
            if (InvokeType.ASY.getValue() == request.getInvokeType()) {
                request.setRemark(exp.getMessage()).setStatus(FAILED.getStatus());
                requestServiceDao.updateRequest(request);
                requestServiceDao.insertFailure(getFailureRequest(request));

            } else {
                if (request.getRetryTimes() == 20) {
                    log.error("distribute failed, over distribute time limit!!");
                    return Response.fail("Syn Request, distribute failed!! distribute time > " + LIMIT_TIMES);
                }
            }
            distributeRequest(request);
        }
        return null;
    }
}
