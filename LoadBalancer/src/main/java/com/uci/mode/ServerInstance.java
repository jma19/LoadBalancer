package com.uci.mode;

import java.util.Comparator;

/**
 * Created by junm5 on 5/27/17.
 */
public class ServerInstance implements Comparable<ServerInstance> {
    private String ip;
    private Integer port;

    private Integer freeMemory;
    private Integer availableProcessor;

    public Integer getFreeMemory() {
        return freeMemory;
    }

    public ServerInstance setFreeMemory(Integer freeMemory) {
        this.freeMemory = freeMemory;
        return this;
    }

    public Integer getAvailableProcessor() {
        return availableProcessor;
    }

    public ServerInstance setAvailableProcessor(Integer availableProcessor) {
        this.availableProcessor = availableProcessor;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ServerInstance setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public ServerInstance setPort(Integer port) {
        this.port = port;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerInstance that = (ServerInstance) o;

        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        return port != null ? port.equals(that.port) : that.port == null;

    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServerInstance{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", freeMemory=" + freeMemory +
                ", availableProcessor=" + availableProcessor +
                '}';
    }

    @Override
    public int compareTo(ServerInstance o) {
        return o.freeMemory - this.freeMemory;

    }
}
