package com.uci.mode;

/**
 * Created by junm5 on 4/25/17.
 */
public class Request {
    private Long id;
    private HttpMethodType type;
    private String ip;
    private Integer port;
    private String path;
    //json style
    private String parameters;

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

    public String getParameters() {
        return parameters;
    }

    public Request setParameters(String parameters) {
        this.parameters = parameters;
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
