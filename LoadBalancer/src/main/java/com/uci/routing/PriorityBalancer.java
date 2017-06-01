package com.uci.routing;

import com.uci.common.ConstantException;
import com.uci.mode.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    public Response distributeRequest(Request request) throws LBException {
        ServerInstance serverInstance;
        while ((serverInstance = priorityQueue.peek()) != null) {
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
            }
        }
        log.info("No Server Available");
        request.increaseReTimes();
        request.setRemark("No Server Available");
        requestServiceDao.updateRequest(request);
        throw ConstantException.NO_SERVER_AVAILABLE;
    }
}
