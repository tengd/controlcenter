package com.evisible.os.controlcenter.exception;

public class BleException extends RuntimeException {
    private static final long serialVersionUID = 8710396445793589764L;
    private String code = null;

    public BleException() {

    }

    public BleException(String message) {
        super(message);
    }

    public BleException(String message, Throwable cause) {
        super(message, cause);
    }

    public BleException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BleException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
