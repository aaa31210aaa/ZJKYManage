package com.zhks.safetyproduction.entity;

public class SafeCheckSubmitBean {
    private String crid;// 检查结果登记id
    private String scmid;// 安全检查表id
    private String scmidname;// 安全检查表名称
    private String sctype;// 检查类型字典值 JCLX
    private String sclevel;// 检查级别字典值  JCJB
    private String scregion;// 检查区域
    private String scdate;// 检查时间
    private String userid;// 参检人员
    private String username;// 参检人员姓名
    private String deptid;// 参检部门
    private String deptname;// 参检部门名称
    private String itemid;// 不合格的检查项，多个用逗号分隔

    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public String getScmid() {
        return scmid;
    }

    public void setScmid(String scmid) {
        this.scmid = scmid;
    }

    public String getScmidname() {
        return scmidname;
    }

    public void setScmidname(String scmidname) {
        this.scmidname = scmidname;
    }

    public String getSctype() {
        return sctype;
    }

    public void setSctype(String sctype) {
        this.sctype = sctype;
    }

    public String getSclevel() {
        return sclevel;
    }

    public void setSclevel(String sclevel) {
        this.sclevel = sclevel;
    }

    public String getScregion() {
        return scregion;
    }

    public void setScregion(String scregion) {
        this.scregion = scregion;
    }

    public String getScdate() {
        return scdate;
    }

    public void setScdate(String scdate) {
        this.scdate = scdate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }
}
