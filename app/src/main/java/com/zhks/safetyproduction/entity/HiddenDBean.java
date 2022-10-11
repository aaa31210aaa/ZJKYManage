package com.zhks.safetyproduction.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Entity
public class HiddenDBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    /**
     * 隐患id
     */
    @NotNull
    private Long hiddenId;
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
    private String inputDate;// 填入日期
    private boolean isCheck; //是否选中状态
    private String fileList; //图片
    @Generated(hash = 346972328)
    public HiddenDBean(@NotNull Long hiddenId, String trsource, String trcategory,
            String trlevel, String trsite, String trsitename, String scregion,
            String trfoundman, String trfounddate, String trdescribe, String zgtype,
            String zgterm, String zgdutyman, String zgdutyunit, String zgmeasure,
            String ysdutyman, String ysdutyunit, String inputDate, boolean isCheck,
            String fileList) {
        this.hiddenId = hiddenId;
        this.trsource = trsource;
        this.trcategory = trcategory;
        this.trlevel = trlevel;
        this.trsite = trsite;
        this.trsitename = trsitename;
        this.scregion = scregion;
        this.trfoundman = trfoundman;
        this.trfounddate = trfounddate;
        this.trdescribe = trdescribe;
        this.zgtype = zgtype;
        this.zgterm = zgterm;
        this.zgdutyman = zgdutyman;
        this.zgdutyunit = zgdutyunit;
        this.zgmeasure = zgmeasure;
        this.ysdutyman = ysdutyman;
        this.ysdutyunit = ysdutyunit;
        this.inputDate = inputDate;
        this.isCheck = isCheck;
        this.fileList = fileList;
    }
    @Generated(hash = 58020985)
    public HiddenDBean() {
    }
    public Long getHiddenId() {
        return this.hiddenId;
    }
    public void setHiddenId(Long hiddenId) {
        this.hiddenId = hiddenId;
    }
    public String getTrsource() {
        return this.trsource;
    }
    public void setTrsource(String trsource) {
        this.trsource = trsource;
    }
    public String getTrcategory() {
        return this.trcategory;
    }
    public void setTrcategory(String trcategory) {
        this.trcategory = trcategory;
    }
    public String getTrlevel() {
        return this.trlevel;
    }
    public void setTrlevel(String trlevel) {
        this.trlevel = trlevel;
    }
    public String getTrsite() {
        return this.trsite;
    }
    public void setTrsite(String trsite) {
        this.trsite = trsite;
    }
    public String getTrsitename() {
        return this.trsitename;
    }
    public void setTrsitename(String trsitename) {
        this.trsitename = trsitename;
    }
    public String getScregion() {
        return this.scregion;
    }
    public void setScregion(String scregion) {
        this.scregion = scregion;
    }
    public String getTrfoundman() {
        return this.trfoundman;
    }
    public void setTrfoundman(String trfoundman) {
        this.trfoundman = trfoundman;
    }
    public String getTrfounddate() {
        return this.trfounddate;
    }
    public void setTrfounddate(String trfounddate) {
        this.trfounddate = trfounddate;
    }
    public String getTrdescribe() {
        return this.trdescribe;
    }
    public void setTrdescribe(String trdescribe) {
        this.trdescribe = trdescribe;
    }
    public String getZgtype() {
        return this.zgtype;
    }
    public void setZgtype(String zgtype) {
        this.zgtype = zgtype;
    }
    public String getZgterm() {
        return this.zgterm;
    }
    public void setZgterm(String zgterm) {
        this.zgterm = zgterm;
    }
    public String getZgdutyman() {
        return this.zgdutyman;
    }
    public void setZgdutyman(String zgdutyman) {
        this.zgdutyman = zgdutyman;
    }
    public String getZgdutyunit() {
        return this.zgdutyunit;
    }
    public void setZgdutyunit(String zgdutyunit) {
        this.zgdutyunit = zgdutyunit;
    }
    public String getZgmeasure() {
        return this.zgmeasure;
    }
    public void setZgmeasure(String zgmeasure) {
        this.zgmeasure = zgmeasure;
    }
    public String getYsdutyman() {
        return this.ysdutyman;
    }
    public void setYsdutyman(String ysdutyman) {
        this.ysdutyman = ysdutyman;
    }
    public String getYsdutyunit() {
        return this.ysdutyunit;
    }
    public void setYsdutyunit(String ysdutyunit) {
        this.ysdutyunit = ysdutyunit;
    }
    public String getInputDate() {
        return this.inputDate;
    }
    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
    public boolean getIsCheck() {
        return this.isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
    public String getFileList() {
        return this.fileList;
    }
    public void setFileList(String fileList) {
        this.fileList = fileList;
    }


    
}
