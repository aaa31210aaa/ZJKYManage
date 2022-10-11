package com.zhks.safetyproduction.datasource.home;

import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.AppVersionUpdateBean;
import com.zhks.safetyproduction.entity.ApprovalhisBean;
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.entity.CancelCaseBean;
import com.zhks.safetyproduction.entity.CurrentUserBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.CustomStringResponse;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.entity.EnclosureBean;
import com.zhks.safetyproduction.entity.JobTaskByDeptBean;
import com.zhks.safetyproduction.entity.LicenceCheckBean;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.LoginStateBean;
import com.zhks.safetyproduction.entity.MenuBean;
import com.zhks.safetyproduction.entity.MiningAreaBean;
import com.zhks.safetyproduction.entity.MultistageBean;
import com.zhks.safetyproduction.entity.NewsBean;
import com.zhks.safetyproduction.entity.NewsDetailBean;
import com.zhks.safetyproduction.entity.PositionInfoBean;
import com.zhks.safetyproduction.entity.QuestionListBean;
import com.zhks.safetyproduction.entity.QuestionResultBean;
import com.zhks.safetyproduction.entity.RiskCheckItemBean;
import com.zhks.safetyproduction.entity.RiskCheckRecordsBean;
import com.zhks.safetyproduction.entity.SafeCheckBean;
import com.zhks.safetyproduction.entity.SafeCheckTermBean;
import com.zhks.safetyproduction.entity.SafetyTrainingBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

public interface HomeHttpDataSource {
    Observable<MenuBean> getHomeMenu(String userId);

    Observable<NewsBean> getNewsList();

    Observable<NewsDetailBean> getNewsDetail(String messageid);

    Observable<CustomStringResponse> addAccidentreport(String modelJson, List<File> files);

    Observable<SafetyTrainingBean> getTrainRecordList(String userid);

    Observable<SafeCheckBean> safeCheckList(String userid);

    Observable<AccidentDetailBean> getAccidentDictionaries(String dicttypeids);

    Observable<DepartmentBean> getDepartment();

    Observable<DeptUserBean> getListUser(String deptId, String pageId);

    Observable<LicenceCheckBean> getLicenceCheck();

    Observable<MultistageBean> getAreaTree(String checkId);

    Observable<SafeCheckTermBean> getSafeCheckTerm(String scmid);

    Observable<CurrentUserBean> getCurrentListUser(String pageId, String userId);

    Observable<CustomResponse> uploadMeetingRecord(String modelJson, List<File> files);

    Observable<ApprovalhisBean> getApprovalhis(String prokey, String bussinesskey);

    Observable<CustomResponse> insertCheckreg(String modelJson);

    Observable<CustomResponse> insertPostpone(String modelJson);

    Observable<ToBeRectifiedBean> toBeRectified();

    Observable<CustomResponse> addTroublereport(String modelJson, List<File> files);

    Observable<CustomResponse> insertRegister(String modelJson, String userid, List<File> files);

    Observable<CustomResponse> saveRegister(String modelJson, String userid, List<File> files);

    Observable<CustomResponse> insertRectification(String modelJson, List<File> files);

    Observable<ToBeRectifiedBean> selectDYSList(String userid);

    Observable<MiningAreaBean> getOrg();

    Observable<RiskCheckRecordsBean> getRiskRecords(String userid);

    Observable<PositionInfoBean> getJobtaskByUserId(String userid);

    Observable<RiskCheckItemBean> getItemList(String evaid);

    Observable<JobTaskByDeptBean> getJobtaskByDept(String evatype, String deptid);

    Observable<CustomResponse> saveManage(String modelJson);

    Observable<CustomResponse> saveWork(String modelJson);

    Observable<EnclosureBean> selectAttachmentApp(String businessid);

    Observable<CustomResponse> insertTroubleaccept(String modelJson);

    Observable<ToBeRectifiedBean> selectDXAList(String userid);

    Observable<CancelCaseBean> insertCaseclose(String modelJson);

    Observable<AqqrpbBean> workingFaceList(String PageId);

    Observable<AqqrpbBean> workingFaceDetail(String csid);

    Observable<ToBeRectifiedBean> selectUncomitList(String userid);

    Observable<ListQuestionsBean> listQuestions(String testid);

    Observable<QuestionListBean> testlist(String userid);

    Observable<QuestionResultBean> questionCommit(String modelJson);

    Observable<CustomResponse> modifypwd(String userid, String pwd);

    Observable<CustomResponse> workingFaceUpdate(String csid, String detailids, List<File> files);

    Observable<LoginStateBean> loginState();

    Observable<AppVersionUpdateBean> updateapp();

}
