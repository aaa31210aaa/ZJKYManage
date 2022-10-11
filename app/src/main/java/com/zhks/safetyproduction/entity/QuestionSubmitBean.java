package com.zhks.safetyproduction.entity;

import java.util.List;

public class QuestionSubmitBean {
    private String testid;
    private String userid;
    private String deptid;// 部门id
    private String duration;// 考试时长(秒)
    private List<QuestionsDTO> questions;

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<QuestionsDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsDTO> questions) {
        this.questions = questions;
    }

    public static class QuestionsDTO {
        private String questionid;
        private List<String> answers;

        public String getQuestionid() {
            return questionid;
        }

        public void setQuestionid(String questionid) {
            this.questionid = questionid;
        }

        public List<String> getAnswers() {
            return answers;
        }

        public void setAnswers(List<String> answers) {
            this.answers = answers;
        }
    }
}
