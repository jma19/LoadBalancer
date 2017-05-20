package com.uci.api;

import com.uci.mode.AsyResponse;
import com.uci.mode.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * when server finish executing asy task. It will call this restful api to update status of task information in DB
 * Created by junm5 on 5/19/17.
 */
@RestController
@RequestMapping("/load")
public class ICallback {

    /**
     *
     * @param asyResponse
     * @return
     */
    @RequestMapping(path = "/callback", method = RequestMethod.POST)
    public Response callback(@RequestBody AsyResponse asyResponse) {

        return null;
    }

}