package com.zhks.safetyproduction.entity;

import java.util.List;

/**
 * 安全确认内容
 */
public class AqqrnrBean {
    private String total;
    private String page;
    private String records;
    private List<RowsDTO> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public static class RowsDTO {
        private String errorMessage;
        private String pager;
        private String modifydate;
        private String confirmitem;
        private String actualsituation;
        private String confirmcontent;
        private String param;
        private String remark;
        private String query;
        private String createdate;
        private String csid;
        private String detailid;
        private String modifyid;
        private String createid;
        private String confirmitemname;
        private String confirmresultname;
        private String confirmstandard;
        private String confirmresult;
        private String success;

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
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

        public String getConfirmitem() {
            return confirmitem;
        }

        public void setConfirmitem(String confirmitem) {
            this.confirmitem = confirmitem;
        }

        public String getActualsituation() {
            return actualsituation;
        }

        public void setActualsituation(String actualsituation) {
            this.actualsituation = actualsituation;
        }

        public String getConfirmcontent() {
            return confirmcontent;
        }

        public void setConfirmcontent(String confirmcontent) {
            this.confirmcontent = confirmcontent;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getCsid() {
            return csid;
        }

        public void setCsid(String csid) {
            this.csid = csid;
        }

        public String getDetailid() {
            return detailid;
        }

        public void setDetailid(String detailid) {
            this.detailid = detailid;
        }

        public String getModifyid() {
            return modifyid;
        }

        public void setModifyid(String modifyid) {
            this.modifyid = modifyid;
        }

        public String getCreateid() {
            return createid;
        }

        public void setCreateid(String createid) {
            this.createid = createid;
        }

        public String getConfirmitemname() {
            return confirmitemname;
        }

        public void setConfirmitemname(String confirmitemname) {
            this.confirmitemname = confirmitemname;
        }

        public String getConfirmresultname() {
            return confirmresultname;
        }

        public void setConfirmresultname(String confirmresultname) {
            this.confirmresultname = confirmresultname;
        }

        public String getConfirmstandard() {
            return confirmstandard;
        }

        public void setConfirmstandard(String confirmstandard) {
            this.confirmstandard = confirmstandard;
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
    }
}
