package com.xylibrary.base;

public class QzdsException extends Throwable {

    private String message;
    private int code;

    public QzdsException(String message, int code) {
        super(message);
        this.code = code;
        this.message=message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;

    }
}
