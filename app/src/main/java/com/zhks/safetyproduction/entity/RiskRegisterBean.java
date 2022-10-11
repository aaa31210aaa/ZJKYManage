package com.zhks.safetyproduction.entity;

public class RiskRegisterBean {
    private String trsource;// 隐患来源（字典 YHLY）
    private String trcategory;// 隐患类别（字典  YHLB）
    private String trlevel;// 隐患级别（字典  YHJB)
    private String trsite;// 隐患所在单位
    private String trsitename;// 隐患所在单位
    private String scregion;// 检查区域 （传中文，不要传id）
    private String trfoundman;// 隐患排查人（传中文）
    private String trfounddate;// 隐患发现时间
    private String trdescribe;// 隐患描述
    private String zgtype;// 整改信息（字典 ZGLX）
    private String zgterm;// 整改期限
    private String zgdutyman;// 整改责任人
    private String zgdutyunit;// 整改责任单位 传中文
    private String zgmeasure;// 整改措施
    private String ysdutyman;// 验收责任人
    private String ysdutyunit;// 验收责任单位 传中文

    public String getTrsource() {
        return trsource;
    }

    public void setTrsource(String trsource) {
        this.trsource = trsource;
    }

    public String getTrcategory() {
        return trcategory;
    }

    public void setTrcategory(String trcategory) {
        this.trcategory = trcategory;
    }

    public String getTrlevel() {
        return trlevel;
    }

    public void setTrlevel(String trlevel) {
        this.trlevel = trlevel;
    }

    public String getTrsite() {
        return trsite;
    }

    public void setTrsite(String trsite) {
        this.trsite = trsite;
    }

    public String getTrsitename() {
        return trsitename;
    }

    public void setTrsitename(String trsitename) {
        this.trsitename = trsitename;
    }

    public String getScregion() {
        return scregion;
    }

    public void setScregion(String scregion) {
        this.scregion = scregion;
    }

    public String getTrfoundman() {
        return trfoundman;
    }

    public void setTrfoundman(String trfoundman) {
        this.trfoundman = trfoundman;
    }

    public String getTrfounddate() {
        return trfounddate;
    }

    public void setTrfounddate(String trfounddate) {
        this.trfounddate = trfounddate;
    }

    public String getTrdescribe() {
        return trdescribe;
    }

    public void setTrdescribe(String trdescribe) {
        this.trdescribe = trdescribe;
    }

    public String getZgtype() {
        return zgtype;
    }

    public void setZgtype(String zgtype) {
        this.zgtype = zgtype;
    }

    public String getZgterm() {
        return zgterm;
    }

    public void setZgterm(String zgterm) {
        this.zgterm = zgterm;
    }

    public String getZgdutyman() {
        return zgdutyman;
    }

    public void setZgdutyman(String zgdutyman) {
        this.zgdutyman = zgdutyman;
    }

    public String getZgdutyunit() {
        return zgdutyunit;
    }

    public void setZgdutyunit(String zgdutyunit) {
        this.zgdutyunit = zgdutyunit;
    }

    public String getZgmeasure() {
        return zgmeasure;
    }

    public void setZgmeasure(String zgmeasure) {
        this.zgmeasure = zgmeasure;
    }

    public String getYsdutyman() {
        return ysdutyman;
    }

    public void setYsdutyman(String ysdutyman) {
        this.ysdutyman = ysdutyman;
    }

    public String getYsdutyunit() {
        return ysdutyunit;
    }

    public void setYsdutyunit(String ysdutyunit) {
        this.ysdutyunit = ysdutyunit;
    }
}
