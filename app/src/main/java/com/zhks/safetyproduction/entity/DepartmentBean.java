package com.zhks.safetyproduction.entity;

import java.util.List;

public class DepartmentBean {

    private String success;
    private String code;
    private String message;
    private String errormessage;
    private List<CellsDTO> cells;

    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public List<CellsDTO> getCells() {
        return cells;
    }

    public void setCells(List<CellsDTO> cells) {
        this.cells = cells;
    }

    public static class CellsDTO {
        private String deptid;
        private String dtname;
        private List<DateDTO> date;

        public String getDeptid() {
            return deptid;
        }

        public void setDeptid(String deptid) {
            this.deptid = deptid;
        }

        public String getDtname() {
            return dtname;
        }

        public void setDtname(String dtname) {
            this.dtname = dtname;
        }

        public List<DateDTO> getDate() {
            return date;
        }

        public void setDate(List<DateDTO> date) {
            this.date = date;
        }

        public static class DateDTO {
            private String dtid;
            private String dtname;

            public String getDtid() {
                return dtid;
            }

            public void setDtid(String dtid) {
                this.dtid = dtid;
            }

            public String getDtname() {
                return dtname;
            }

            public void setDtname(String dtname) {
                this.dtname = dtname;
            }
        }
    }
}
