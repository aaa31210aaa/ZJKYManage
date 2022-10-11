package com.zhks.safetyproduction.entity;

import java.io.Serializable;
import java.util.List;

public class ListQuestionsBean implements Serializable{

    private boolean success;
    private String code;
    private String message;
    private String errormessage;
    private List<DataDTO> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO implements Serializable {
        private int position;
        private String questionNo;
        private String questionid;
        private String questioncontent;
        private String createunit;
        private String questionmodel; //试题类型 tx001 单选  tx002 多选 tx003 判断
        private String createid;
        private boolean isCheck;
        private List<AnswerListDTO> answerList;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getQuestionNo() {
            return questionNo;
        }

        public void setQuestionNo(String questionNo) {
            this.questionNo = questionNo;
        }

        public String getQuestionid() {
            return questionid;
        }

        public void setQuestionid(String questionid) {
            this.questionid = questionid;
        }

        public String getQuestioncontent() {
            return questioncontent;
        }

        public void setQuestioncontent(String questioncontent) {
            this.questioncontent = questioncontent;
        }

        public String getCreateunit() {
            return createunit;
        }

        public void setCreateunit(String createunit) {
            this.createunit = createunit;
        }

        public String getQuestionmodel() {
            return questionmodel;
        }

        public void setQuestionmodel(String questionmodel) {
            this.questionmodel = questionmodel;
        }

        public String getCreateid() {
            return createid;
        }

        public void setCreateid(String createid) {
            this.createid = createid;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public List<AnswerListDTO> getAnswerList() {
            return answerList;
        }

        public void setAnswerList(List<AnswerListDTO> answerList) {
            this.answerList = answerList;
        }

        public static class AnswerListDTO implements Serializable{
            private String answerid;
            private String answercontent;
            private String answerflag;
            private boolean answerCheck;

            public String getAnswerid() {
                return answerid;
            }

            public void setAnswerid(String answerid) {
                this.answerid = answerid;
            }

            public String getAnswercontent() {
                return answercontent;
            }

            public void setAnswercontent(String answercontent) {
                this.answercontent = answercontent;
            }

            public String getAnswerflag() {
                return answerflag;
            }

            public void setAnswerflag(String answerflag) {
                this.answerflag = answerflag;
            }

            public boolean isAnswerCheck() {
                return answerCheck;
            }

            public void setAnswerCheck(boolean answerCheck) {
                this.answerCheck = answerCheck;
            }
        }
    }
}
