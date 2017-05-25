package com.uci.mode;

/**
 * Created by junm5 on 4/30/17.
 */
public enum RequestStatus {

    EXECUTING(1, "under executing "), FINISHING(2, "execute successfully"), FAILED(3, "execute failed");

    private Integer status;
    private String remark;

    RequestStatus(Integer status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

}
