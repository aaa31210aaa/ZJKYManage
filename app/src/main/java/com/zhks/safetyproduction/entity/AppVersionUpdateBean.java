package com.zhks.safetyproduction.entity;

public class AppVersionUpdateBean {
    private String code;
    private String message;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private String appmemo;
        private String verCode;
        private String url;

        public String getAppmemo() {
            return appmemo;
        }

        public void setAppmemo(String appmemo) {
            this.appmemo = appmemo;
        }

        public String getVerCode() {
            return verCode;
        }

        public void setVerCode(String verCode) {
            this.verCode = verCode;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
