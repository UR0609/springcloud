package com.ljryh.client.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code = 200;
    private String msg;
    private Object data;

    public static Result ok() {
        Result r = new Result();
        r.setMsg("操作成功");
        return r;
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    public static Result error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
