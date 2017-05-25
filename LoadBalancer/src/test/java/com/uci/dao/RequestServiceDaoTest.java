package com.uci.dao;

import com.uci.ServerApplication;
import com.uci.mode.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

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
        requestServiceDao.insertRequest(request);
        System.out.println("request id = " + request.getId());
    }

    @Test
    public void insertFailure() throws Exception {

    }

    @Test
    public void updateRequest() throws Exception {
        Request request = new Request().setIp("1.1.1.1").setPort(9000).setId(10l).setRetryTimes(2).setStatus(RequestStatus.FAILED.getStatus()).setRemark(RequestStatus.FAILED.getRemark());
        requestServiceDao.updateRequest(request);
    }

    @Test
    public void queryAllFinishRequest() throws Exception {
        List<Request> requests = requestServiceDao.queryAllFinishRequest(new ServerInstance().setIp("192.168.1.1").setPort(8080));
        System.out.println(requests);

    }

}