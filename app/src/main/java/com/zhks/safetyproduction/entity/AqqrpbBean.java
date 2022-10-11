package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class AqqrpbBean {

    private int total;
    private int page;
    private int records;
    private List<RowsDTO> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public static class RowsDTO implements Serializable {
        private String errorMessage;
        private String confirmstatename;
        private String remark;
        private String param;
        private String query;
        private String createdate;
        private String midname;
        private String arrid;
        private String detailid;
        private String eowstaff;
        private String modifyid;
        private String dates;
        private String worktypename;
        private String pager;
        private String modifydate;
        private String workname;
        private String shiftname;
        private String confirmstate;
        private String csid;
        private String validationstaff;
        private String midid;
        private String createid;
        private String confirmresultname;
        private String confirmresult;
        private String success;
        private String staffname;
        private String shift;
        private String confirmstandard;
        private boolean isClickble; // 是否可以点击
        private boolean isCheck; //是否合格

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getConfirmstatename() {
            return confirmstatename;
        }

        public void setConfirmstatename(String confirmstatename) {
            this.confirmstatename = confirmstatename;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getMidname() {
            return midname;
        }

        public void setMidname(String midname) {
            this.midname = midname;
        }

        public String getArrid() {
            return arrid;
        }

        public void setArrid(String arrid) {
            this.arrid = arrid;
        }

        public String getDetailid() {
            return detailid;
        }

        public void setDetailid(String detailid) {
            this.detailid = detailid;
        }

        public String getEowstaff() {
            return eowstaff;
        }

        public void setEowstaff(String eowstaff) {
            this.eowstaff = eowstaff;
        }

        public String getModifyid() {
            return modifyid;
        }

        public void setModifyid(String modifyid) {
            this.modifyid = modifyid;
        }

        public String getDates() {
            return dates;
        }

        public void setDates(String dates) {
            this.dates = dates;
        }

        public String getWorktypename() {
            return worktypename;
        }

        public void setWorktypename(String worktypename) {
            this.worktypename = worktypename;
        }

        public String getPager() {
            return pager;
        }

        public void setPager(String pager) {
            this.pager = pager;
        }

        public String getModifydate() {
            return modifydate;
        }

        public void setModifydate(String modifydate) {
            this.modifydate = modifydate;
        }

        public String getWorkname() {
            return workname;
        }

        public void setWorkname(String workname) {
            this.workname = workname;
        }

        public String getShiftname() {
            return shiftname;
        }

        public void setShiftname(String shiftname) {
            this.shiftname = shiftname;
        }

        public String getConfirmstate() {
            return confirmstate;
        }

        public void setConfirmstate(String confirmstate) {
            this.confirmstate = confirmstate;
        }

        public String getCsid() {
            return csid;
        }

        public void setCsid(String csid) {
            this.csid = csid;
        }

        public String getValidationstaff() {
            return validationstaff;
        }

        public void setValidationstaff(String validationstaff) {
            this.validationstaff = validationstaff;
        }

        public String getMidid() {
            return midid;
        }

        public void setMidid(String midid) {
            this.midid = midid;
        }

        public String getCreateid() {
            return createid;
        }

        public void setCreateid(String createid) {
            this.createid = createid;
        }

        public String getConfirmresultname() {
            return confirmresultname;
        }

        public void setConfirmresultname(String confirmresultname) {
            this.confirmresultname = confirmresultname;
        }

        public String getConfirmresult() {
            return confirmresult;
        }

        public void setConfirmresult(String confirmresult) {
            this.confirmresult = confirmresult;
        }

        public String isSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getStaffname() {
            return staffname;
        }

        public void setStaffname(String staffname) {
            this.staffname = staffname;
        }

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

        public String getConfirmstandard() {
            return confirmstandard;
        }

        public void setConfirmstandard(String confirmstandard) {
            this.confirmstandard = confirmstandard;
        }

        public boolean isClickble() {
            return isClickble;
        }

        public void setClickble(boolean clickble) {
            isClickble = clickble;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
