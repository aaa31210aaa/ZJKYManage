package com.zhks.safetyproduction.entity;

import java.util.List;

public class NewsDetailBean {
    private String success;
    private String errormessage;
    private List<NewsDetailBean> cells;
    private String messageid;// 消息id
    private String messagetitle;// 消息标题
    private String mestime;// 消息时间
    private String mestype;// 消息类型
    private String mestypename;// 类型名称
    private String appflagno;// 跳转地址 （为空时点击处理消息提示消息无需处理）
    private String mescontent;// 消息内容

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMessagetitle() {
        return messagetitle;
    }

    public void setMessagetitle(String messagetitle) {
        this.messagetitle = messagetitle;
    }

    public String getMestime() {
        return mestime;
    }

    public void setMestime(String mestime) {
        this.mestime = mestime;
    }

    public String getMestype() {
        return mestype;
    }

    public void setMestype(String mestype) {
        this.mestype = mestype;
    }

    public String getMestypename() {
        return mestypename;
    }

    public void setMestypename(String mestypename) {
        this.mestypename = mestypename;
    }

    public String getAppflagno() {
        return appflagno;
    }

    public void setAppflagno(String appflagno) {
        this.appflagno = appflagno;
    }

    public String getMescontent() {
        return mescontent;
    }

    public void setMescontent(String mescontent) {
        this.mescontent = mescontent;
    }

    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public List<NewsDetailBean> getCells() {
        return cells;
    }

    public void setCells(List<NewsDetailBean> cells) {
        this.cells = cells;
    }
}
