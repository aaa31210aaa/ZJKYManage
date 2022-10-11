package com.zhks.safetyproduction.entity;

import java.util.List;

public class SafeCheckTermBean {
    private boolean success;
    private String errormessage;
    private List<CellsDTO> cells;
    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class CellsDTO {
        private String itemid;
        private String itemname;
        private String itemcode;
        private String itemtypename;
        private String itemsdescribe;
        private boolean isCheck;
        private boolean isClickble; // 是否可以点击

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getItemcode() {
            return itemcode;
        }

        public void setItemcode(String itemcode) {
            this.itemcode = itemcode;
        }

        public String getItemtypename() {
            return itemtypename;
        }

        public void setItemtypename(String itemtypename) {
            this.itemtypename = itemtypename;
        }

        public String getItemsdescribe() {
            return itemsdescribe;
        }

        public void setItemsdescribe(String itemsdescribe) {
            this.itemsdescribe = itemsdescribe;
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
