package com.zhks.safetyproduction.entity;

import java.util.List;

public class RiskCheckItemBean{

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
        private String rlid; //检查id
        private String manameasure; //管控措施
        private boolean isCheck; //是否合格
        private boolean isClickble; // 是否可以点击

        public String getRlid() {
            return rlid;
        }

        public void setRlid(String rlid) {
            this.rlid = rlid;
        }

        public String getManameasure() {
            return manameasure;
        }

        public void setManameasure(String manameasure) {
            this.manameasure = manameasure;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isClickble() {
            return isClickble;
        }

        public void setClickble(boolean clickble) {
            isClickble = clickble;
        }
    }
}
