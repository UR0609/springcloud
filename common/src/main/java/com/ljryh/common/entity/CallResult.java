package com.ljryh.common.entity;

import java.io.Serializable;

public class CallResult<T> implements Serializable {
    private boolean isSuccess;
    private int status;
    private String msg;
    private T data;

    private CallResult(boolean isSuccess, int status) {
        this.isSuccess = isSuccess;
        this.status = status;
    }

    private CallResult(boolean isSuccess, int status, T data) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.data = data;
    }

    private CallResult(boolean isSuccess, int status, String msg, T data) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private CallResult(boolean isSuccess, int status, String msg) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.msg = msg;
    }

    public int getstatus() {
        return this.status;
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
        return new CallResult(true, Responsestatus.SUCCESS.getstatus());
    }

    public static <T> CallResult<T> success(String msg) {
        return new CallResult(true, Responsestatus.SUCCESS.getstatus(), msg);
    }

    public static <T> CallResult<T> success(T data) {
        return new CallResult(true, Responsestatus.SUCCESS.getstatus(), data);
    }

    public static <T> CallResult<T> success(String msg, T data) {
        return new CallResult(true, Responsestatus.SUCCESS.getstatus(), msg, data);
    }

    public static <T> CallResult<T> success(int status, String msg, T data) {
        return new CallResult(true, status, msg, data);
    }

    public static <T> CallResult<T> fail() {
        return new CallResult(false, Responsestatus.ERROR.getstatus(), Responsestatus.ERROR.getDesc());
    }

    public static <T> CallResult<T> fail(String msg) {
        return new CallResult(false, Responsestatus.ERROR.getstatus(), msg);
    }

    public static <T> CallResult<T> fail(int status, String msg) {
        return new CallResult(false, status, msg);
    }

    public static <T> CallResult<T> fail(int status, String msg, T data) {
        return new CallResult(false, status, msg, data);
    }

    private static enum Responsestatus {
        SUCCESS(200, "SUCCESS"),
        ERROR(400, "ERROR");

        private final int status;
        private final String desc;

        private Responsestatus(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public int getstatus() {
            return this.status;
        }

        public String getDesc() {
            return this.desc;
        }
    }
}
