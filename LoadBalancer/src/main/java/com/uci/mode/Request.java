package com.uci.mode;

/**
 * Created by junm5 on 4/25/17.
 */
public class Request {
    private Long id;
    private HttpMethodType type;
    private String ip;
    private Integer port;
    private Integer retryTimes;
    private String path;
    private Integer status;
    //json style
    private String remark;
    private String params;

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

    public HttpMethodType getType() {
        return type;
    }

    public Request setType(HttpMethodType type) {
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
        return new StringBuffer()
                .append(this.getIp())
                .append(":")
                .append(this.getPort())
                .append("/")
                .append(this.getPath()).toString();
    }
}
