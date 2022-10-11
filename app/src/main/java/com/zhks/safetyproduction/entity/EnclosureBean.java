package com.zhks.safetyproduction.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EnclosureBean extends BaseBean{

    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("Attachmenturl")
        private String attachmenturl;
        private String deflag;
        private String attachmentname;

        public String getAttachmenturl() {
            return attachmenturl;
        }

        public void setAttachmenturl(String attachmenturl) {
            this.attachmenturl = attachmenturl;
        }

        public String getDeflag() {
            return deflag;
        }

        public void setDeflag(String deflag) {
            this.deflag = deflag;
        }

        public String getAttachmentname() {
            return attachmentname;
        }

        public void setAttachmentname(String attachmentname) {
            this.attachmentname = attachmentname;
        }
    }
}
