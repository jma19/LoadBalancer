package com.uci.routing;

import com.uci.mode.LBException;
import com.uci.mode.Request;
import com.uci.mode.Response;
import com.uci.mode.ServerInstance;
import org.springframework.core.PriorityOrdered;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by junm5 on 5/24/17.
 */
public class PriorityBalancer extends AbstractLoadBalancer {

    private PriorityQueue<ServerInstance> priorityQueue = new PriorityQueue<>();


    @Override
    public void reloadCache(List<ServerInstance> serverInstanceList) {
        PriorityQueue<ServerInstance> origin = priorityQueue;

        PriorityQueue<ServerInstance> tempQueue = new PriorityQueue<>();
        tempQueue.addAll(serverInstanceList);
        priorityQueue = tempQueue;
        dispatchFailedServer(origin, priorityQueue);
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
        try {
            return dispute(request, serverInstance);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
