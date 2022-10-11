package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class MenuBean implements Serializable{

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

    public static class CellsDTO implements Serializable {
        private String module;
        private String appicaddr;
        private List<ObjectDTO> object;

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getAppicaddr() {
            return appicaddr;
        }

        public void setAppicaddr(String appicaddr) {
            this.appicaddr = appicaddr;
        }

        public List<ObjectDTO> getObject() {
            return object;
        }

        public void setObject(List<ObjectDTO> object) {
            this.object = object;
        }

        public static class ObjectDTO implements Serializable{
            private String appname;
            private String appicaddr;
            private String appflagno;

            public String getAppname() {
                return appname;
            }

            public void setAppname(String appname) {
                this.appname = appname;
            }

            public String getAppicaddr() {
                return appicaddr;
            }

            public void setAppicaddr(String appicaddr) {
                this.appicaddr = appicaddr;
            }

            public String getAppflagno() {
                return appflagno;
            }

            public void setAppflagno(String appflagno) {
                this.appflagno = appflagno;
            }
        }
    }
}
