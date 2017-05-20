package com.uci.api;

import com.uci.mode.HttpMethodType;
import com.uci.mode.Request;
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

    private static String queryPath = "/load/query";

    /**
     * https://docs.spring.io/spring-session/docs/current/reference/html5/guides/rest.html
     * for each request, server needs to keep the connection; this storage should be in-memory
     *
     * @param query
     * @return
     */
    @RequestMapping(path = "/query/{query}", method = RequestMethod.GET)
    public String query(@PathVariable String query) {
        System.out.println("receiving :" + query);
        Request request = new Request().setType(HttpMethodType.GET).setPath(queryPath);
        iLoadBalancer.distributeRequest(request);
        return "OK";
    }

    @RequestMapping(path = "/query/{query}", method = RequestMethod.GET)
    public String queryAsy() {
        return null;
    }


}
