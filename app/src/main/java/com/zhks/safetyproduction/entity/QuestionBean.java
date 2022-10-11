package com.zhks.safetyproduction.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class QuestionBean {
    @Id(autoincrement = true)
    /**
     * 主键id
     */
    @NotNull
    private Long id;
    /**
     * 问题题目
     */
    private String que;
    private int questionNo;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String kind;
    private String type;
    private String answer;
    private String detail;
    @Generated(hash = 2071790666)
    public QuestionBean(@NotNull Long id, String que, int questionNo,
            String choiceA, String choiceB, String choiceC, String choiceD,
            String kind, String type, String answer, String detail) {
        this.id = id;
        this.que = que;
        this.questionNo = questionNo;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.kind = kind;
        this.type = type;
        this.answer = answer;
        this.detail = detail;
    }
    @Generated(hash = 842286453)
    public QuestionBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQue() {
        return this.que;
    }
    public void setQue(String que) {
        this.que = que;
    }
    public int getQuestionNo() {
        return this.questionNo;
    }
    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }
    public String getChoiceA() {
        return this.choiceA;
    }
    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }
    public String getChoiceB() {
        return this.choiceB;
    }
    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }
    public String getChoiceC() {
        return this.choiceC;
    }
    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }
    public String getChoiceD() {
        return this.choiceD;
    }
    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }
    public String getKind() {
        return this.kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getDetail() {
        return this.detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
}
