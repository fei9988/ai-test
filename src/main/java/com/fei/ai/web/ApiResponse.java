package com.fei.ai.web;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String status;
    private T data;
    private String msg;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.status = "ok";
        r.data = data;
        return r;
    }

    public static ApiResponse<Object> error(String msg) {
        ApiResponse<Object> r = new ApiResponse<>();
        r.status = "error";
        r.msg = msg;
        return r;
    }
}
