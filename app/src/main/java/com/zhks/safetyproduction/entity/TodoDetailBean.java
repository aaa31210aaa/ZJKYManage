package com.zhks.safetyproduction.entity;

public class TodoDetailBean {
    private int position;
    private String approverNikeName; //审批人称号
    private String approver; //审批人
    private String approvalTime; //审批时间
    private String approvalStatus; //审批状态
    private String approvalComments; //审批评价

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getApproverNikeName() {
        return approverNikeName;
    }

    public void setApproverNikeName(String approverNikeName) {
        this.approverNikeName = approverNikeName;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalComments() {
        return approvalComments;
    }

    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
    }
}
