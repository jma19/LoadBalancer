package com.uci.api;

import com.google.common.collect.Lists;
import com.uci.ServerApplication;
import com.uci.mode.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by junm5 on 5/17/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class LoadBalancerApiTest {
    @Autowired
    private LoadBalancerApi loadBalancerApi;

    @Test
    public void should_query_syn() throws Exception {
        Response query = loadBalancerApi.query(1);
        System.out.println(query);
    }

    @Test
    public void should_push_asy() throws Exception {


    }
}