package com.uci.routing;

import com.google.gson.reflect.TypeToken;
import com.uci.mode.Request;
import com.uci.utils.JsonUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(AsyDispatcher.class);

    private BlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public void add(List<Request> requests) {
        log.info("asy dispatcher add requests size = " + requests.size());
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
                log.info("asy dispatcher consume " + request);
                String params = request.getParams();
                List<BasicNameValuePair> nameValuePairs = JsonUtils.fromJson(params, new TypeToken<List<BasicNameValuePair>>() {
                });
                request.setPairs(nameValuePairs);
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
