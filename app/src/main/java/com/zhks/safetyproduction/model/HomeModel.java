package com.zhks.safetyproduction.model;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.datasource.home.HomeHttpDataSource;
import com.zhks.safetyproduction.datasource.home.HomeLocalDataSource;
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
import me.goldze.mvvmhabit.base.BaseModel;

public class HomeModel extends BaseModel implements HomeHttpDataSource, HomeLocalDataSource {
    private volatile static HomeModel INSTANCE = null;
    private final HomeHttpDataSource mHttpDataSource;

    private final HomeLocalDataSource mLocalDataSource;

    private HomeModel(@NonNull HomeHttpDataSource httpDataSource,
                      @NonNull HomeLocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static HomeModel getInstance(HomeHttpDataSource httpDataSource,
                                        HomeLocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (MineModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HomeModel(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public Observable<MenuBean> getHomeMenu(String userId) {
        return mHttpDataSource.getHomeMenu(userId);
    }

    @Override
    public Observable<NewsBean> getNewsList() {
        return mHttpDataSource.getNewsList();
    }

    @Override
    public Observable<NewsDetailBean> getNewsDetail(String messageid) {
        return mHttpDataSource.getNewsDetail(messageid);
    }

    @Override
    public Observable<CustomStringResponse> addAccidentreport(String accidentJson, List<File> files) {
        return mHttpDataSource.addAccidentreport(accidentJson, files);
    }

    @Override
    public Observable<SafetyTrainingBean> getTrainRecordList(String userid) {
        return mHttpDataSource.getTrainRecordList(userid);
    }

    @Override
    public Observable<SafeCheckBean> safeCheckList(String userid) {
        return mHttpDataSource.safeCheckList(userid);
    }

    @Override
    public Observable<AccidentDetailBean> getAccidentDictionaries(String dicttypeids) {
        return mHttpDataSource.getAccidentDictionaries(dicttypeids);
    }

    @Override
    public Observable<DepartmentBean> getDepartment() {
        return mHttpDataSource.getDepartment();
    }

    @Override
    public Observable<DeptUserBean> getListUser(String deptId, String pageId) {
        return mHttpDataSource.getListUser(deptId, pageId);
    }

    @Override
    public Observable<LicenceCheckBean> getLicenceCheck() {
        return mHttpDataSource.getLicenceCheck();
    }

    @Override
    public Observable<MultistageBean> getAreaTree(String checkId) {
        return mHttpDataSource.getAreaTree(checkId);
    }

    @Override
    public Observable<SafeCheckTermBean> getSafeCheckTerm(String scmid) {
        return mHttpDataSource.getSafeCheckTerm(scmid);
    }

    @Override
    public Observable<CurrentUserBean> getCurrentListUser(String pageId, String userId) {
        return mHttpDataSource.getCurrentListUser(pageId, userId);
    }

    @Override
    public Observable<CustomResponse> uploadMeetingRecord(String modelJson, List<File> files) {
        return mHttpDataSource.uploadMeetingRecord(modelJson, files);
    }

    @Override
    public Observable<ApprovalhisBean> getApprovalhis(String prokey, String bussinesskey) {
        return mHttpDataSource.getApprovalhis(prokey, bussinesskey);
    }

    @Override
    public Observable<CustomResponse> insertCheckreg(String modelJson) {
        return mHttpDataSource.insertCheckreg(modelJson);
    }

    @Override
    public Observable<CustomResponse> insertPostpone(String modelJson) {
        return mHttpDataSource.insertPostpone(modelJson);
    }

    @Override
    public Observable<ToBeRectifiedBean> toBeRectified() {
        return mHttpDataSource.toBeRectified();
    }

    @Override
    public Observable<CustomResponse> addTroublereport(String modelJson, List<File> files) {
        return mHttpDataSource.addTroublereport(modelJson, files);
    }

    @Override
    public Observable<CustomResponse> insertRegister(String modelJson, String userid, List<File> files) {
        return mHttpDataSource.insertRegister(modelJson, userid, files);
    }

    @Override
    public Observable<CustomResponse> saveRegister(String modelJson, String userid, List<File> files) {
        return mHttpDataSource.saveRegister(modelJson, userid, files);
    }

    @Override
    public Observable<CustomResponse> insertRectification(String modelJson, List<File> files) {
        return mHttpDataSource.insertRectification(modelJson, files);
    }

    @Override
    public Observable<ToBeRectifiedBean> selectDYSList(String userid) {
        return mHttpDataSource.selectDYSList(userid);
    }

    @Override
    public Observable<MiningAreaBean> getOrg() {
        return mHttpDataSource.getOrg();
    }

    @Override
    public Observable<RiskCheckRecordsBean> getRiskRecords(String userid) {
        return mHttpDataSource.getRiskRecords(userid);
    }

    @Override
    public Observable<PositionInfoBean> getJobtaskByUserId(String userid) {
        return mHttpDataSource.getJobtaskByUserId(userid);
    }

    @Override
    public Observable<RiskCheckItemBean> getItemList(String evaid) {
        return mHttpDataSource.getItemList(evaid);
    }

    @Override
    public Observable<JobTaskByDeptBean> getJobtaskByDept(String evatype, String deptid) {
        return mHttpDataSource.getJobtaskByDept(evatype, deptid);
    }

    @Override
    public Observable<CustomResponse> saveManage(String modelJson) {
        return mHttpDataSource.saveManage(modelJson);
    }

    @Override
    public Observable<CustomResponse> saveWork(String modelJson) {
        return mHttpDataSource.saveWork(modelJson);
    }

    @Override
    public Observable<EnclosureBean> selectAttachmentApp(String businessid) {
        return mHttpDataSource.selectAttachmentApp(businessid);
    }

    @Override
    public Observable<CustomResponse> insertTroubleaccept(String modelJson) {
        return mHttpDataSource.insertTroubleaccept(modelJson);
    }

    @Override
    public Observable<ToBeRectifiedBean> selectDXAList(String userid) {
        return mHttpDataSource.selectDXAList(userid);
    }

    @Override
    public Observable<CancelCaseBean> insertCaseclose(String modelJson) {
        return mHttpDataSource.insertCaseclose(modelJson);
    }

    @Override
    public Observable<AqqrpbBean> workingFaceList(String PageId) {
        return mHttpDataSource.workingFaceList(PageId);
    }

    @Override
    public Observable<AqqrpbBean> workingFaceDetail(String csid) {
        return mHttpDataSource.workingFaceDetail(csid);
    }

    @Override
    public Observable<ToBeRectifiedBean> selectUncomitList(String userid) {
        return mHttpDataSource.selectUncomitList(userid);
    }

    @Override
    public Observable<ListQuestionsBean> listQuestions(String testid) {
        return mHttpDataSource.listQuestions(testid);
    }

    @Override
    public Observable<QuestionListBean> testlist(String userid) {
        return mHttpDataSource.testlist(userid);
    }

    @Override
    public Observable<QuestionResultBean> questionCommit(String modelJson) {
        return mHttpDataSource.questionCommit(modelJson);
    }

    @Override
    public Observable<CustomResponse> modifypwd(String userid, String pwd) {
        return mHttpDataSource.modifypwd(userid, pwd);
    }

    @Override
    public Observable<CustomResponse> workingFaceUpdate(String csid, String detailids, List<File> files) {
        return mHttpDataSource.workingFaceUpdate(csid, detailids, files);
    }

    @Override
    public Observable<LoginStateBean> loginState() {
        return mHttpDataSource.loginState();
    }

    @Override
    public Observable<AppVersionUpdateBean> updateapp() {
        return mHttpDataSource.updateapp();
    }


}
