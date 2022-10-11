package com.zhks.safetyproduction.entity;


import java.util.List;

/**
 * 多级内容
 */
public class MultistageBean {

    private String code;
    private String message;
    private List<DataDTO> data;

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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private String id;
        private String pid;
        private String name;
        private String isParent;
        private String close;
        private List<CheckAreasDTO> checkAreas;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsParent() {
            return isParent;
        }

        public void setIsParent(String isParent) {
            this.isParent = isParent;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public List<CheckAreasDTO> getCheckAreas() {
            return checkAreas;
        }

        public void setCheckAreas(List<CheckAreasDTO> checkAreas) {
            this.checkAreas = checkAreas;
        }

        public static class CheckAreasDTO {
            private String id;
            private String pid;
            private String name;
            private String isParent;
            private String close;
            private List<CheckAreasDTO> checkAreas;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIsParent() {
                return isParent;
            }

            public void setIsParent(String isParent) {
                this.isParent = isParent;
            }

            public String getClose() {
                return close;
            }

            public void setClose(String close) {
                this.close = close;
            }

            public List<CheckAreasDTO> getCheckAreas() {
                return checkAreas;
            }

            public void setCheckAreas(List<CheckAreasDTO> checkAreas) {
                this.checkAreas = checkAreas;
            }
        }
    }
}
