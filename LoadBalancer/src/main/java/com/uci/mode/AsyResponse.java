package com.uci.mode;

/**
 * Created by junm5 on 5/19/17.
 */
public class AsyResponse {
    private Integer id;
    private String remark;
    private Boolean isSuccess;

    public Integer getId() {
        return id;
    }

    public AsyResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AsyResponse setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public AsyResponse setSuccess(Boolean success) {
        isSuccess = success;
        return this;
    }

    @Override
    public String toString() {
        return "AsyResponse{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
