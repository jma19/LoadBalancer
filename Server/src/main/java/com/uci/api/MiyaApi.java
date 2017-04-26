package com.uci.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/miya")
public class MiyaApi {


    @RequestMapping(path = "/query/{query}", method = RequestMethod.GET)
    public String query(@PathVariable String query) {
        return null;
    }
}
