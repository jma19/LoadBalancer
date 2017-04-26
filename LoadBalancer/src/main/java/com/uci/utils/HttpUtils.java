package com.uci.utils;

import com.uci.mode.Request;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by junm5 on 4/25/17.
 */
public class HttpUtils {
    /**
     * call post
     *
     * @return
     */
    public static String post(String url) {
        return null;

    }


    public static String get(String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        try {
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                return getStringResponse(response);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }

    private static String getStringResponse(HttpResponse httpResponse) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}
