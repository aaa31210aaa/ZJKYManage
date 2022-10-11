package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class ToBeRectifiedBean implements Serializable {

    private String message;
    private List<DataDTO> data;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataDTO implements Serializable {
        private String trsource;
        private String trsite;
        private String trsitename;
        private String trfounddate;
        private String trid;
        private String remark;
        private String ysdutyman;
        private String trstatename;
        private String createdate;
        private String trfoundman;
        private String zgtype;
        private String zgterm;
        private String trstate;
        private String trlevelname;
        private String trcategoryname;
        private String createid;
        private String trlevel;
        private String trdescribe;
        private String zgdutyunit;
        private String trsourcename;
        private String zgtypename;
        private String trcategory;
        private String zgmeasure;
        private String ysdutyunit;
        private String zgdutyman;
        private String cqlscs; //是否采取临时措施0否 1是
        private String moneysource; //资金来源
        private String money; //治理费用
        private String bazt; // 备案状态 0未备案 1已备案 2已核销
        private String gpdb; // 是否挂牌督办0否 1是
        private String babh; //备案编号
        private String slunit; //受理单位（传中文）
        private String barq; //备案日期
        private String tadate; //隐患验收时间
        private String scregion; //检查区域

        public String getTrsource() {
            return trsource;
        }

        public void setTrsource(String trsource) {
            this.trsource = trsource;
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

        public String getTrfounddate() {
            return trfounddate;
        }

        public void setTrfounddate(String trfounddate) {
            this.trfounddate = trfounddate;
        }

        public String getTrid() {
            return trid;
        }

        public void setTrid(String trid) {
            this.trid = trid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getYsdutyman() {
            return ysdutyman;
        }

        public void setYsdutyman(String ysdutyman) {
            this.ysdutyman = ysdutyman;
        }

        public String getTrstatename() {
            return trstatename;
        }

        public void setTrstatename(String trstatename) {
            this.trstatename = trstatename;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getTrfoundman() {
            return trfoundman;
        }

        public void setTrfoundman(String trfoundman) {
            this.trfoundman = trfoundman;
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

        public String getTrstate() {
            return trstate;
        }

        public void setTrstate(String trstate) {
            this.trstate = trstate;
        }

        public String getTrlevelname() {
            return trlevelname;
        }

        public void setTrlevelname(String trlevelname) {
            this.trlevelname = trlevelname;
        }

        public String getTrcategoryname() {
            return trcategoryname;
        }

        public void setTrcategoryname(String trcategoryname) {
            this.trcategoryname = trcategoryname;
        }

        public String getCreateid() {
            return createid;
        }

        public void setCreateid(String createid) {
            this.createid = createid;
        }

        public String getTrlevel() {
            return trlevel;
        }

        public void setTrlevel(String trlevel) {
            this.trlevel = trlevel;
        }

        public String getTrdescribe() {
            return trdescribe;
        }

        public void setTrdescribe(String trdescribe) {
            this.trdescribe = trdescribe;
        }

        public String getZgdutyunit() {
            return zgdutyunit;
        }

        public void setZgdutyunit(String zgdutyunit) {
            this.zgdutyunit = zgdutyunit;
        }

        public String getTrsourcename() {
            return trsourcename;
        }

        public void setTrsourcename(String trsourcename) {
            this.trsourcename = trsourcename;
        }

        public String getZgtypename() {
            return zgtypename;
        }

        public void setZgtypename(String zgtypename) {
            this.zgtypename = zgtypename;
        }

        public String getTrcategory() {
            return trcategory;
        }

        public void setTrcategory(String trcategory) {
            this.trcategory = trcategory;
        }

        public String getZgmeasure() {
            return zgmeasure;
        }

        public void setZgmeasure(String zgmeasure) {
            this.zgmeasure = zgmeasure;
        }

        public String getYsdutyunit() {
            return ysdutyunit;
        }

        public void setYsdutyunit(String ysdutyunit) {
            this.ysdutyunit = ysdutyunit;
        }

        public String getZgdutyman() {
            return zgdutyman;
        }

        public void setZgdutyman(String zgdutyman) {
            this.zgdutyman = zgdutyman;
        }

        public String getCqlscs() {
            return cqlscs;
        }

        public void setCqlscs(String cqlscs) {
            this.cqlscs = cqlscs;
        }

        public String getMoneysource() {
            return moneysource;
        }

        public void setMoneysource(String moneysource) {
            this.moneysource = moneysource;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBazt() {
            return bazt;
        }

        public void setBazt(String bazt) {
            this.bazt = bazt;
        }

        public String getGpdb() {
            return gpdb;
        }

        public void setGpdb(String gpdb) {
            this.gpdb = gpdb;
        }

        public String getBabh() {
            return babh;
        }

        public void setBabh(String babh) {
            this.babh = babh;
        }

        public String getSlunit() {
            return slunit;
        }

        public void setSlunit(String slunit) {
            this.slunit = slunit;
        }

        public String getBarq() {
            return barq;
        }

        public void setBarq(String barq) {
            this.barq = barq;
        }

        public String getTadate() {
            return tadate;
        }

        public void setTadate(String tadate) {
            this.tadate = tadate;
        }

        public String getScregion() {
            return scregion;
        }

        public void setScregion(String scregion) {
            this.scregion = scregion;
        }
    }
}
