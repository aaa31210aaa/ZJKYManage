package com.zhks.safetyproduction.entity;

import java.util.List;

public class JobTaskByDeptBean {

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

    public static class DataDTO {
        private String evaname;
        private String fxdname;
        private String evaid; //岗位Id
        private String userName;
        private String type; //岗位类型 0作业岗1管理岗
        private String deptname;

        public String getEvaname() {
            return evaname;
        }

        public void setEvaname(String evaname) {
            this.evaname = evaname;
        }

        public String getFxdname() {
            return fxdname;
        }

        public void setFxdname(String fxdname) {
            this.fxdname = fxdname;
        }

        public String getEvaid() {
            return evaid;
        }

        public void setEvaid(String evaid) {
            this.evaid = evaid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }
    }
}
