package com.uci.api;

import com.google.common.collect.Lists;
import com.uci.mode.*;
import com.uci.routing.ILoadBalancer;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class LoadBalancerApi {

    @Autowired
    private ILoadBalancer iLoadBalancer;

    private static String queryPath = "/query";
    private static String postPath = "/post";

    /**
     * https://docs.spring.io/spring-session/docs/current/reference/html5/guides/rest.html
     * for each request, server needs to keep the connection; this storage should be in-memory
     *
     * @param id
     * @return
     */

    @RequestMapping(path = "/query/{id}", method = RequestMethod.GET)
    public Response query(@PathVariable Integer id) {
        System.out.println("receiving :" + id);
        Request request = new Request().setType(HttpMethodType.GET.getValue())
                .setPath(queryPath)
                .setParams("" + id)
                .setInvokeType(InvokeType.SYN.getValue());

        try {
            return iLoadBalancer.distributeRequest(request);
        } catch (LBException e) {
            return Response.fail(e.getMessage());
        }
    }

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public Response queryAsy(@RequestParam Integer id) {

        Request request = new Request().setType(HttpMethodType.POST.getValue())
                .setPath(postPath)
                .setPairs(Lists.newArrayList(new BasicNameValuePair("id", "" + id)))
                .setInvokeType(InvokeType.ASY.getValue());

        try {
            return iLoadBalancer.distributeRequest(request);
        } catch (LBException e) {
            return Response.fail(e.getMessage());
        }
    }


}
