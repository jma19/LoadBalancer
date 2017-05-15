package com.uci.api;

import com.uci.routing.ILoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadBalancerApi {

    @Autowired
    private ILoadBalancer iLoadBalancer;

    @RequestMapping(path = "/query/{query}", method = RequestMethod.GET)
    public String query(@PathVariable String query) {
        System.out.println("receiving :" + query);
        iLoadBalancer.distributeRequest(null);
        return "OK";
    }


}
