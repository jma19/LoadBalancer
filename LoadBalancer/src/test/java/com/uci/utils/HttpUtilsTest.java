package com.uci.utils;

import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.List;

/**
 * Created by junm5 on 5/21/17.
 */
public class HttpUtilsTest {

    @Test
    public void should_query_using_get_method() throws Exception {
        String response = HttpUtils.get("http://localhost:8001/query/2");
        System.out.println(response);
    }

    @Test
    public void test_post_method_using_parameters() throws Exception {
        // HttpUtils.post();
        List<NameValuePair> nameValuePairs = Lists.newArrayList(new BasicNameValuePair("id", "1"));
        String response = HttpUtils.post("http://localhost:8001/post", nameValuePairs);
        System.out.println(response);
    }

    @Test
    public void test_post_method_using_json() throws Exception {


    }
}