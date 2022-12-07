package com.ljryh.common.entity;


/**
 * 基础错误异常
 *
 * @author <a href="mailto:fshuaxm@vip.qq.com">fshuaxm</a>
 * @date 2020/1/5
 * @since JDK1.8+
 */
public class MicroException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private int code = 5000;

    public MicroException() {

    }

    public MicroException(Throwable cause) {
        super(cause);
    }

    public MicroException(String msg) {
        super(msg);
    }

    public MicroException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public MicroException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
