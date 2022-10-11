package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class SafeCheckBean implements Serializable {

    private String success;
    private String errormessage;
    private List<CellsDTO> cells;
    private String code;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
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

    public static class CellsDTO implements Serializable{
        private String scmid;
        private String scmname;
        private String attridname;
        private String scmdescribe;

        public String getScmid() {
            return scmid;
        }

        public void setScmid(String scmid) {
            this.scmid = scmid;
        }

        public String getScmname() {
            return scmname;
        }

        public void setScmname(String scmname) {
            this.scmname = scmname;
        }

        public String getAttridname() {
            return attridname;
        }

        public void setAttridname(String attridname) {
            this.attridname = attridname;
        }

        public String getScmdescribe() {
            return scmdescribe;
        }

        public void setScmdescribe(String scmdescribe) {
            this.scmdescribe = scmdescribe;
        }
    }
}
