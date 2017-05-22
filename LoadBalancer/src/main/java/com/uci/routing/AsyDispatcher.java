package com.uci.routing;

import com.uci.mode.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by junm5 on 5/21/17.
 */
@Service
public class AsyDispatcher {
    @Autowired
    private ILoadBalancer balancer;

    private Queue<Request> queue = new LinkedList<>();







}

