package com.uci.api;

import com.uci.mode.Response;
import com.uci.mode.Server;
import com.uci.routing.RoundRobinBalancer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private Log logger = LogFactory.getLog(RegisterApi.class);

    @Autowired
    private RoundRobinBalancer roundRobinBalancer;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Response register(@RequestParam(value = "ip") String ip, @RequestParam(value = "port") Integer port) {
        logger.info(String.format("receiving server register message, ip = % s, port = %s", ip, port));
        Server server = new Server().setIp(ip).setPort(port);
        roundRobinBalancer.addServer(server);
        return Response.success(true);
    }

}



