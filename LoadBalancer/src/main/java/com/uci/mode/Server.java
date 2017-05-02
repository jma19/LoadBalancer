package com.uci.mode;

import java.util.Stack;

/**
 * Created by junm5 on 4/25/17.
 */
public class Server {
    private Integer id;
    private String ip;
    private Integer port;
    private Status status;

    public Integer getId() {
        return id;
    }

    public Server setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public Server setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Server setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Server setIp(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Server server = (Server) o;

        if (ip != null ? !ip.equals(server.ip) : server.ip != null) return false;
        return port != null ? port.equals(server.port) : server.port == null;

    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}
