package com.uci.mode;

/**
 * Created by junm5 on 5/22/17.
 */
public enum InvokeType {
    ASY(1), SYN(2);
    private Integer value;

    InvokeType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public InvokeType valueOf(Integer value) {
        switch (value) {
            case 1:
                return ASY;
            case 2:
                return SYN;
            default:
                return null;
        }
    }
}

