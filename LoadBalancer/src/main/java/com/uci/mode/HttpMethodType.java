package com.uci.mode;

/**
 * Created by junm5 on 4/25/17.
 */
public enum HttpMethodType {
    POST(1), PUT(2), GET(3);

    int value;

    HttpMethodType(int value) {
        this.value = value;
    }

    public HttpMethodType valueOf(int value) {
        switch (value) {
            case 1:
                return POST;
            case 2:
                return PUT;
            case 3:
                return GET;
            default:
                return null;
        }
    }

    public int getValue(){
        return this.value;
    }

}
