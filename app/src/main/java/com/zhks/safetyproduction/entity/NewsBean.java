package com.zhks.safetyproduction.entity;

import java.util.List;

public class NewsBean {

    private String success;
    private String errormessage;
    private List<CellsDTO> cells;

    public String isSuccess() {
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

    public static class CellsDTO {
        private String messageid;
        private String messagetitle;
        private String mestime;
        private String isreaded; //消息状态（0：未读，1：已读）
        private String appflagno;
        private String mescontent;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getMessagetitle() {
            return messagetitle;
        }

        public void setMessagetitle(String messagetitle) {
            this.messagetitle = messagetitle;
        }

        public String getMestime() {
            return mestime;
        }

        public void setMestime(String mestime) {
            this.mestime = mestime;
        }

        public String getIsreaded() {
            return isreaded;
        }

        public void setIsreaded(String isreaded) {
            this.isreaded = isreaded;
        }

        public String getAppflagno() {
            return appflagno;
        }

        public void setAppflagno(String appflagno) {
            this.appflagno = appflagno;
        }

        public String getMescontent() {
            return mescontent;
        }

        public void setMescontent(String mescontent) {
            this.mescontent = mescontent;
        }
    }
}
