package com.uci.dao;

import com.uci.ServerApplication;
import com.uci.mode.HttpMethodType;
import com.uci.mode.InvokeType;
import com.uci.mode.Request;
import com.uci.mode.RequestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by junm5 on 5/23/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class RequestServiceDaoTest {
    @Autowired
    private RequestServiceDao requestServiceDao;

    @Test
    public void insertRequest() throws Exception {
        Request request = new Request()
                .setIp("192.168.11.1")
                .setPort(8080)
                .setPath("/query")
                .setStatus(RequestStatus.FINISHING.getStatus())
                .setRemark(RequestStatus.FINISHING.getRemark())
                .setInvokeType(InvokeType.SYN)
                .setType(HttpMethodType.POST.getValue())
                .setParams("{id: 2}")
                .setRetryTimes(0);
        int i = requestServiceDao.insertRequest(request);
        System.out.println(i);
    }

    @Test
    public void insertFailure() throws Exception {

    }

    @Test
    public void updateRequest() throws Exception {

    }

    @Test
    public void queryAllFinishRequest() throws Exception {

    }

}