package com.uci.utils;

import com.google.gson.reflect.TypeToken;
import com.uci.ServerApplication;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by junm5 on 4/28/17.
 */
public class JsonUtilsTest {

    @Test
    public void should() throws Exception {
        String params = "[{\"name\":\"id\",\"value\":\"102\"}]";

        List<BasicNameValuePair> nameValuePairs = JsonUtils.fromJson(params, new TypeToken<List<BasicNameValuePair>>() {
        });
        System.out.println(nameValuePairs);
    }
}