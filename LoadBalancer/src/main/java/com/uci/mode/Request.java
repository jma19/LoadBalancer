package com.uci.mode;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by junm5 on 4/25/17.
 */
public class Request {
    private Long id;
    private Integer type;
    private String ip;
    private Integer port;
    private Integer retryTimes;
    private String path;
    private Integer status;
    //json style
    private String remark;
    private String params;
    private List<NameValuePair> pairs;
    private InvokeType invokeType;

    public InvokeType getInvokeType() {
        return invokeType;
    }

    public Request setInvokeType(InvokeType invokeType) {
        this.invokeType = invokeType;
        return this;
    }

    public void increaseReTimes(){
        retryTimes++;
    }

    public List<NameValuePair> getPairs() {
        return pairs;
    }

    public Request setPairs(List<NameValuePair> pairs) {
        this.pairs = pairs;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Request setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public Request setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Request setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Request setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Request setType(Integer type) {
        this.type = type;
        return this;
    }
    
    public String getIp() {
        return ip;
    }

    public Request setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public Request setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Request setPath(String path) {
        this.path = path;
        return this;
    }

    public String getParams() {
        return params;
    }

    public Request setParams(String params) {
        this.params = params;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", type=" + type +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", retryTimes=" + retryTimes +
                ", path='" + path + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", params='" + params + '\'' +
                ", pairs=" + pairs +
                ", invokeType=" + invokeType +
                '}';
    }
}
