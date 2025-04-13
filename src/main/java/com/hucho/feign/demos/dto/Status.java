package com.hucho.feign.demos.dto;

public enum Status {
    SUCCESS("200","OK."),
    SYSTEM_ERROR("500","System error.");

    public String satus;
    public String message;

    Status(String status, String message) {
        this.satus = status;
        this.message = message;
    }
}
