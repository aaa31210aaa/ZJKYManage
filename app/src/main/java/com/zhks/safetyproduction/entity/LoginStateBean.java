package com.zhks.safetyproduction.entity;

public class LoginStateBean {
    private String success;
    private String code;
    private String state; //（1：正常，0退出）

    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
