package com.uci.routing;

import com.google.common.collect.Lists;
import com.uci.ServerApplication;
import com.uci.mode.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by junm5 on 5/29/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class AsyDispatcherTest {

    @Autowired
    private AsyDispatcher asyDispatcher;

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void name() throws Exception {
        Thread thread = new Thread(asyDispatcher);
        thread.start();

        asyDispatcher.add(Lists.newArrayList(new Request()));
    }
}