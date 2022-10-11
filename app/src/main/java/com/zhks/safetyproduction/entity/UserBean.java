package com.zhks.safetyproduction.entity;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * code 状态码
 * message 消息内容
 * username 用户名
 * userid 用户id
 * deptid 组织机构id
 * deptname 组织机构名称
 * usertype 用户类型
 */
@Keep
public class UserBean implements Serializable {
    private String code;
    private String message;
    private String username;
    private String userid;
    private String deptid;
    private String deptname;
    private String usertype;
    private String isAdmin;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String isAdmin() {
        return isAdmin;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }
}
