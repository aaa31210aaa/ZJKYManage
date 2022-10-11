package com.zhks.safetyproduction.entity;

public class DjRecordBean {
    private String id;//有值就是修改，没有就是新增
    private String rlid; //岗位id
    private String checkdate; //点检时间
    private String checkman; //点检人
    private String checkmanname; //点检人名称
    private String locationname; //地点
    private String state; //状态 0：保存 1：提交
    private String itemid; //不合格的检查项id，，多个用逗号分隔

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRlid() {
        return rlid;
    }

    public void setRlid(String rlid) {
        this.rlid = rlid;
    }

    public String getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public String getCheckman() {
        return checkman;
    }

    public void setCheckman(String checkman) {
        this.checkman = checkman;
    }

    public String getCheckmanname() {
        return checkmanname;
    }

    public void setCheckmanname(String checkmanname) {
        this.checkmanname = checkmanname;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
}
