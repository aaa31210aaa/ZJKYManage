package com.zhks.safetyproduction.entity;

public class HiddenReportBean {
    private String reporttype;// 举报类型 （字典 yhlx）
    private String reporttypename;// 举报类型名称
    private String troubleplace;// 发生地点
    private String troubledescribe;// 问题描述
    private String troubleaccunit; //受理平台（字典 SLPT）
    private String troubleaccunitname;// 受理平台名称
    private String createid;// 创建人
    private String createdate;// 创建时间
    private String acceptanceperson;// 受理人

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public String getReporttypename() {
        return reporttypename;
    }

    public void setReporttypename(String reporttypename) {
        this.reporttypename = reporttypename;
    }

    public String getTroubleplace() {
        return troubleplace;
    }

    public void setTroubleplace(String troubleplace) {
        this.troubleplace = troubleplace;
    }

    public String getTroubledescribe() {
        return troubledescribe;
    }

    public void setTroubledescribe(String troubledescribe) {
        this.troubledescribe = troubledescribe;
    }

    public String getTroubleaccunit() {
        return troubleaccunit;
    }

    public void setTroubleaccunit(String troubleaccunit) {
        this.troubleaccunit = troubleaccunit;
    }

    public String getTroubleaccunitname() {
        return troubleaccunitname;
    }

    public void setTroubleaccunitname(String troubleaccunitname) {
        this.troubleaccunitname = troubleaccunitname;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getAcceptanceperson() {
        return acceptanceperson;
    }

    public void setAcceptanceperson(String acceptanceperson) {
        this.acceptanceperson = acceptanceperson;
    }
}
