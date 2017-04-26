package com.uci.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadBalancerApi {

    @RequestMapping(path = "/query/{query}", method = RequestMethod.GET)
    public String query(@PathVariable String query) {
        System.out.println("receiving :" + query);
        return "";
    }
}
