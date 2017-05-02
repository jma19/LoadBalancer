package com.uci.api;

import com.uci.mode.Response;
import com.uci.mode.Server;
import com.uci.routing.RoundRobinBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by junm5 on 5/1/17.
 */
@Component
@RequestMapping("/loadbalancer")
public class RegisterApi {

    @Autowired
    private RoundRobinBalancer roundRobinBalancer;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Response publish(@RequestParam(value = "ip") String ip, @RequestParam(value = "port") Integer port) {
        Server server = new Server().setIp(ip).setPort(port);
        roundRobinBalancer.addServer(server);
        return Response.success(true);
    }

}



