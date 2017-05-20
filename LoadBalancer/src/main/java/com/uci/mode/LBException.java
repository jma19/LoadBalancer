package com.uci.mode;

/**
 * Created by junm5 on 5/20/17.
 */
public class LBException extends Exception {
    private String code;
    private String message;

    public LBException(String message) {
        super(message);
    }

    public LBException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LBException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

