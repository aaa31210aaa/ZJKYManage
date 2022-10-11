package com.zhks.safetyproduction.entity;

public class AcceptConfirmBean {
    private String userid; //用户id
    private String trid;//隐患id
    private String isqualified;//是否合格（字典）
    private String taattendman;//验收参与人
    private String taview;//验收意见
    private String tadate;//验收时间
    private String taconfirmman;//销号确认人

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTrid() {
        return trid;
    }

    public void setTrid(String trid) {
        this.trid = trid;
    }

    public String getIsqualified() {
        return isqualified;
    }

    public void setIsqualified(String isqualified) {
        this.isqualified = isqualified;
    }

    public String getTaattendman() {
        return taattendman;
    }

    public void setTaattendman(String taattendman) {
        this.taattendman = taattendman;
    }

    public String getTaview() {
        return taview;
    }

    public void setTaview(String taview) {
        this.taview = taview;
    }

    public String getTadate() {
        return tadate;
    }

    public void setTadate(String tadate) {
        this.tadate = tadate;
    }

    public String getTaconfirmman() {
        return taconfirmman;
    }

    public void setTaconfirmman(String taconfirmman) {
        this.taconfirmman = taconfirmman;
    }
}
