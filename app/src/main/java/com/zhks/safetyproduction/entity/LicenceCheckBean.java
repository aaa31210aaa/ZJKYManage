package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class LicenceCheckBean implements Serializable {

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
        private String waname;
        private String remark;
        private String watypename;
        private String createdate;
        private String wxzyNumber;
        private String auditstatename;
        private String workplace;
        private String watype;
        private String walevel;
        private String analysis;
        private String workcontent;
        private String deptid;
        private String startdate;
        private String createid;
        private String walevelname;
        private String auditstate;
        private String measures;
        private String enddate;
        private String deptname;
        private String dangerworkid;

        public String getWaname() {
            return waname;
        }

        public void setWaname(String waname) {
            this.waname = waname;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getWatypename() {
            return watypename;
        }

        public void setWatypename(String watypename) {
            this.watypename = watypename;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getWxzyNumber() {
            return wxzyNumber;
        }

        public void setWxzyNumber(String wxzyNumber) {
            this.wxzyNumber = wxzyNumber;
        }

        public String getAuditstatename() {
            return auditstatename;
        }

        public void setAuditstatename(String auditstatename) {
            this.auditstatename = auditstatename;
        }

        public String getWorkplace() {
            return workplace;
        }

        public void setWorkplace(String workplace) {
            this.workplace = workplace;
        }

        public String getWatype() {
            return watype;
        }

        public void setWatype(String watype) {
            this.watype = watype;
        }

        public String getWalevel() {
            return walevel;
        }

        public void setWalevel(String walevel) {
            this.walevel = walevel;
        }

        public String getAnalysis() {
            return analysis;
        }

        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }

        public String getWorkcontent() {
            return workcontent;
        }

        public void setWorkcontent(String workcontent) {
            this.workcontent = workcontent;
        }

        public String getDeptid() {
            return deptid;
        }

        public void setDeptid(String deptid) {
            this.deptid = deptid;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getCreateid() {
            return createid;
        }

        public void setCreateid(String createid) {
            this.createid = createid;
        }

        public String getWalevelname() {
            return walevelname;
        }

        public void setWalevelname(String walevelname) {
            this.walevelname = walevelname;
        }

        public String getAuditstate() {
            return auditstate;
        }

        public void setAuditstate(String auditstate) {
            this.auditstate = auditstate;
        }

        public String getMeasures() {
            return measures;
        }

        public void setMeasures(String measures) {
            this.measures = measures;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public String getDangerworkid() {
            return dangerworkid;
        }

        public void setDangerworkid(String dangerworkid) {
            this.dangerworkid = dangerworkid;
        }
    }
}
