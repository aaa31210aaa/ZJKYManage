package com.zhks.safetyproduction.entity;

import java.util.List;

public class SafetyTrainingBean {

    private String success;
    private String errormessage;
    private List<CellsDTO> cells;

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

    public static class CellsDTO {
        private String trid; //培训记录ID
        private String planname; //培训名称
        private String startdate; //培训开始日期
        private String enddate; //培训结束日期
        private String trainSite; //培训地点
        private String trainscore; //培训成绩
        private String traineffect;//培训效果
        private String trainhour; //培训学时

        public String getTrid() {
            return trid;
        }

        public void setTrid(String trid) {
            this.trid = trid;
        }

        public String getPlanname() {
            return planname;
        }

        public void setPlanname(String planname) {
            this.planname = planname;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getTrainSite() {
            return trainSite;
        }

        public void setTrainSite(String trainSite) {
            this.trainSite = trainSite;
        }

        public String getTrainscore() {
            return trainscore;
        }

        public void setTrainscore(String trainscore) {
            this.trainscore = trainscore;
        }

        public String getTraineffect() {
            return traineffect;
        }

        public void setTraineffect(String traineffect) {
            this.traineffect = traineffect;
        }

        public String getTrainhour() {
            return trainhour;
        }

        public void setTrainhour(String trainhour) {
            this.trainhour = trainhour;
        }
    }
}
