package com.uci.routing;

import com.uci.dao.RequestServiceDao;
import com.uci.mode.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by junm5 on 5/21/17.
 */
@Service
public class AsyDispatcher implements Runnable {
    @Autowired
    private ILoadBalancer balancer;


    @Autowired
    private RequestServiceDao requestServiceDao;

    private BlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public void add(List<Request> requests) {
        if (requests != null && !requests.isEmpty()) {
            for (Request request : requests) {
                queue.offer(request);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            Request request = null;
            try {
                request = queue.take();
                balancer.distributeRequest(request);
            } catch (Exception e) {
                e.printStackTrace();
                if (request != null) {
                    queue.offer(request);
                }
            }
        }
    }
}
