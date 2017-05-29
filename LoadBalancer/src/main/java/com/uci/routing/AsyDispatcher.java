package com.uci.routing;

import com.google.gson.reflect.TypeToken;
import com.uci.mode.Request;
import com.uci.utils.JsonUtils;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
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
                String params = request.getParams();
                JsonUtils.fromJson(params, new TypeToken<List<NameValuePair>>() {});
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
