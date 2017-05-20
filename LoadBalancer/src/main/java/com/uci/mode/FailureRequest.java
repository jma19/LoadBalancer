package com.uci.mode;

/**
 * Created by junm5 on 5/20/17.
 */

public class FailureRequest {
    private Long id;
    private Long requestId;
    private String ip;
    private Integer port;
    private String path;
    private String remark;
    private Long createdAt;
    private Long updatedAt;

    public Long getId() {
        return id;
    }

    public FailureRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getRequestId() {
        return requestId;
    }

    public FailureRequest setRequestId(Long requestId) {
        this.requestId = requestId;
        return this;

    }

    public String getIp() {
        return ip;
    }

    public FailureRequest setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public FailureRequest setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public FailureRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public FailureRequest setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public FailureRequest setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public FailureRequest setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public String toString() {
        return "FailureRequest{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", remark='" + remark + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
