package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class CancelCaseBean {
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
        private String trid;// 隐患id
        private String closedate;// 销案时间
        private String closeview;// 销案意见
        private String userid;// 登录用户id

        public String getTrid() {
            return trid;
        }

        public void setTrid(String trid) {
            this.trid = trid;
        }

        public String getClosedate() {
            return closedate;
        }

        public void setClosedate(String closedate) {
            this.closedate = closedate;
        }

        public String getCloseview() {
            return closeview;
        }

        public void setCloseview(String closeview) {
            this.closeview = closeview;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
