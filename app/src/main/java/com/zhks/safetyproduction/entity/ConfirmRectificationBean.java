package com.zhks.safetyproduction.entity;

public class ConfirmRectificationBean {
    private String trid;// 隐患id
    private String recfinishdate;// 整改完成时间
    private String recview;// 整改意见

    public String getTrid() {
        return trid;
    }

    public void setTrid(String trid) {
        this.trid = trid;
    }

    public String getRecfinishdate() {
        return recfinishdate;
    }

    public void setRecfinishdate(String recfinishdate) {
        this.recfinishdate = recfinishdate;
    }

    public String getRecview() {
        return recview;
    }

    public void setRecview(String recview) {
        this.recview = recview;
    }
}
