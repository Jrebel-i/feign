package com.hucho.feign.demos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

    private String code;
    private String message;
    private T data;

    public Result(String code, String message){
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Status.SUCCESS.satus,Status.SUCCESS.message,data);
    }

    public static <T> Result<T> success() {
        return new Result<>(Status.SUCCESS.satus,Status.SUCCESS.message);
    }
}
