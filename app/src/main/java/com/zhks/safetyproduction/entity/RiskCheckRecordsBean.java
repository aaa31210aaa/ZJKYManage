package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class RiskCheckRecordsBean {
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
        private String id; //点检记录id
        private String evaname; //岗位名称
        private String checkdate; //点检日期
        private String checkman; //点检人
        private String state; //状态 0：未提交 1:已提交
        private String locationname; //地点
        private String type; //岗位类型 0作业岗 1管理岗
        private String deptname; //所属矿区
        private String checkmanname; //点检人名称
        private String fxdname; //风险点名称
        private String username; //岗位责任人

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEvaname() {
            return evaname;
        }

        public void setEvaname(String evaname) {
            this.evaname = evaname;
        }

        public String getCheckdate() {
            return checkdate;
        }

        public void setCheckdate(String checkdate) {
            this.checkdate = checkdate;
        }

        public String getCheckman() {
            return checkman;
        }

        public void setCheckman(String checkman) {
            this.checkman = checkman;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLocationname() {
            return locationname;
        }

        public void setLocationname(String locationname) {
            this.locationname = locationname;
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

        public String getCheckmanname() {
            return checkmanname;
        }

        public void setCheckmanname(String checkmanname) {
            this.checkmanname = checkmanname;
        }

        public String getFxdname() {
            return fxdname;
        }

        public void setFxdname(String fxdname) {
            this.fxdname = fxdname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
