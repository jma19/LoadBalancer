package com.uci.mode;

/**
 * Created by junm5 on 4/25/17.
 */
public class ServerInstance {
    private String ip;
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public ServerInstance setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ServerInstance setIp(String ip) {
        this.ip = ip;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerInstance server = (ServerInstance) o;

        if (ip != null ? !ip.equals(server.ip) : server.ip != null) return false;
        return port != null ? port.equals(server.port) : server.port == null;

    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServerInstance:" +
                "ip_'" + ip + '\'' +
                ", port_" + port;
    }
}
