package com.ljryh.common.entity;

import java.io.Serializable;

public class CallResult<T> implements Serializable {
    private boolean isSuccess;
    private int code;
    private String msg;
    private T data;

    private CallResult(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }

    private CallResult(boolean isSuccess, int code, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.data = data;
    }

    private CallResult(boolean isSuccess, int code, String msg, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private CallResult(boolean isSuccess, int code, String msg) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static <T> CallResult<T> success() {
        return new CallResult(true, ResponseCode.SUCCESS.getCode());
    }

    public static <T> CallResult<T> success(String msg) {
        return new CallResult(true, ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> CallResult<T> success(T data) {
        return new CallResult(true, ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> CallResult<T> success(String msg, T data) {
        return new CallResult(true, ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> CallResult<T> success(int code, String msg, T data) {
        return new CallResult(true, code, msg, data);
    }

    public static <T> CallResult<T> fail() {
        return new CallResult(false, ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> CallResult<T> fail(String msg) {
        return new CallResult(false, ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> CallResult<T> fail(int code, String msg) {
        return new CallResult(false, code, msg);
    }

    public static <T> CallResult<T> fail(int code, String msg, T data) {
        return new CallResult(false, code, msg, data);
    }

    private static enum ResponseCode {
        SUCCESS(0, "SUCCESS"),
        ERROR(1, "ERROR");

        private final int code;
        private final String desc;

        private ResponseCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }
    }
}
