package com.zhks.safetyproduction.datasource.home;

import com.zhks.safetyproduction.entity.AppVersionUpdateBean;
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.entity.CancelCaseBean;
import com.zhks.safetyproduction.entity.EnclosureBean;
import com.zhks.safetyproduction.entity.JobTaskByDeptBean;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.LoginStateBean;
import com.zhks.safetyproduction.entity.MiningAreaBean;
import com.zhks.safetyproduction.entity.NewsDetailBean;
import com.zhks.safetyproduction.entity.PositionInfoBean;
import com.zhks.safetyproduction.entity.QuestionListBean;
import com.zhks.safetyproduction.entity.QuestionResultBean;
import com.zhks.safetyproduction.entity.RiskCheckItemBean;
import com.zhks.safetyproduction.entity.RiskCheckRecordsBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.ApprovalhisBean;
import com.zhks.safetyproduction.entity.CurrentUserBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.CustomStringResponse;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.entity.LicenceCheckBean;
import com.zhks.safetyproduction.entity.MenuBean;
import com.zhks.safetyproduction.entity.MultistageBean;
import com.zhks.safetyproduction.entity.NewsBean;
import com.zhks.safetyproduction.entity.SafeCheckBean;
import com.zhks.safetyproduction.entity.SafeCheckTermBean;
import com.zhks.safetyproduction.entity.SafetyTrainingBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.http.service.ApiService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;

public class HomeHttpDataSourceImpl implements HomeHttpDataSource {
    private ApiService apiService;
    private volatile static HomeHttpDataSourceImpl INSTANCE = null;

    public static HomeHttpDataSourceImpl getInstance(ApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HomeHttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HomeHttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HomeHttpDataSourceImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<MenuBean> getHomeMenu(String userId) {
        return apiService.getHomeMenu(PersonInfoManager.getInstance().getToken(), userId);
    }

    @Override
    public Observable<NewsBean> getNewsList() {
        return apiService.getNewsList(PersonInfoManager.getInstance().getToken());
    }

    @Override
    public Observable<NewsDetailBean> getNewsDetail(String messageid) {
        return apiService.getNewsDetail(PersonInfoManager.getInstance().getToken(), messageid);
    }

    @Override
    public Observable<CustomStringResponse> addAccidentreport(String modelJson, List<File> files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        MultipartBody.Part part;
        if (files.isEmpty()) {
            part = MultipartBody.Part.createFormData("", "");
            partList.add(part);
        } else {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                partList.add(part);
            }
        }
        return apiService.addAccidentreport(PersonInfoManager.getInstance().getToken(), modelJson, partList);
    }


    @Override
    public Observable<SafetyTrainingBean> getTrainRecordList(String userid) {
        return apiService.getTrainRecordList(PersonInfoManager.getInstance().getToken(), userid);
    }

    @Override
    public Observable<SafeCheckBean> safeCheckList(String userid) {
        return apiService.safeCheckList(PersonInfoManager.getInstance().getToken(), userid);
    }

    @Override
    public Observable<AccidentDetailBean> getAccidentDictionaries(String dicttypeids) {
        return apiService.getAccidentDictionaries(PersonInfoManager.getInstance().getToken(), dicttypeids);
    }

    @Override
    public Observable<DepartmentBean> getDepartment() {
        return apiService.getDepartment(PersonInfoManager.getInstance().getToken());
    }

    @Override
    public Observable<DeptUserBean> getListUser(String deptId, String pageId) {
        return apiService.getListUser(PersonInfoManager.getInstance().getToken(), deptId, pageId);
    }

    @Override
    public Observable<LicenceCheckBean> getLicenceCheck() {
        return apiService.getLicenceCheckList(PersonInfoManager.getInstance().getToken());
    }

    @Override
    public Observable<MultistageBean> getAreaTree(String checkId) {
        return apiService.getAreaTree(PersonInfoManager.getInstance().getToken(), checkId);
    }

    @Override
    public Observable<SafeCheckTermBean> getSafeCheckTerm(String scmid) {
        return apiService.getSafeCheckTerm(PersonInfoManager.getInstance().getToken(), scmid);
    }

    @Override
    public Observable<CurrentUserBean> getCurrentListUser(String pageId, String userId) {
        return apiService.getCurrentListUser(PersonInfoManager.getInstance().getToken(), pageId, userId);
    }

    @Override
    public Observable<CustomResponse> uploadMeetingRecord(String modelJson, List<File> files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        MultipartBody.Part part;
        if (files.isEmpty()) {
            part = MultipartBody.Part.createFormData("", "");
            partList.add(part);
        } else {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                partList.add(part);
            }
        }
        return apiService.uploadMeetingRecord(PersonInfoManager.getInstance().getToken(), modelJson, partList);
    }

    @Override
    public Observable<ApprovalhisBean> getApprovalhis(String prokey, String bussinesskey) {
        return apiService.getApprovalhis(PersonInfoManager.getInstance().getToken(), prokey, bussinesskey);
    }

    @Override
    public Observable<CustomResponse> insertCheckreg(String modelJson) {
        return apiService.insertCheckreg(PersonInfoManager.getInstance().getToken(), modelJson);
    }

    @Override
    public Observable<CustomResponse> insertPostpone(String modelJson) {
        return apiService.insertPostpone(PersonInfoManager.getInstance().getToken(), modelJson);
    }

    @Override
    public Observable<ToBeRectifiedBean> toBeRectified() {
        return apiService.toBeRectified(PersonInfoManager.getInstance().getToken());
    }

    @Override
    public Observable<CustomResponse> addTroublereport(String modelJson, List<File> files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        MultipartBody.Part part;
        if (files.isEmpty()) {
            part = MultipartBody.Part.createFormData("", "");
            partList.add(part);
        } else {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                partList.add(part);
            }
        }

        return apiService.addTroublereport(PersonInfoManager.getInstance().getToken(), modelJson, partList);
    }

    @Override
    public Observable<CustomResponse> insertRegister(String modelJson, String userid, List<File> files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        MultipartBody.Part part;
        if (files.isEmpty()) {
            part = MultipartBody.Part.createFormData("", "");
            partList.add(part);
        } else {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                partList.add(part);
            }
        }

        return apiService.insertRegister(PersonInfoManager.getInstance().getToken(), modelJson, userid, partList);
    }

    @Override
    public Observable<CustomResponse> saveRegister(String modelJson, String userid, List<File> files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        MultipartBody.Part part;
        if (files.isEmpty()) {
            part = MultipartBody.Part.createFormData("", "");
            partList.add(part);
        } else {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                partList.add(part);
            }
        }

        return apiService.saveRegister(PersonInfoManager.getInstance().getToken(), modelJson, userid, partList);
    }

    @Override
    public Observable<CustomResponse> insertRectification(String modelJson, List<File> files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        MultipartBody.Part part;
        if (files.isEmpty()) {
            part = MultipartBody.Part.createFormData("", "");
            partList.add(part);
        } else {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                partList.add(part);
            }
        }

        return apiService.insertRectification(PersonInfoManager.getInstance().getToken(), modelJson, partList);
    }

    @Override
    public Observable<ToBeRectifiedBean> selectDYSList(String userid) {
        return apiService.selectDYSList(PersonInfoManager.getInstance().getToken(), userid);
    }

    @Override
    public Observable<MiningAreaBean> getOrg() {
        return apiService.getOrg(PersonInfoManager.getInstance().getToken());
    }

    @Override
    public Observable<RiskCheckRecordsBean> getRiskRecords(String userid) {
        return apiService.getRiskRecords(PersonInfoManager.getInstance().getToken(), userid);
    }

    @Override
    public Observable<PositionInfoBean> getJobtaskByUserId(String userid) {
        return apiService.getJobtaskByUserId(PersonInfoManager.getInstance().getToken(), userid);
    }


    @Override
    public Observable<RiskCheckItemBean> getItemList(String evaid) {
        return apiService.getItemList(PersonInfoManager.getInstance().getToken(), evaid);
    }

    @Override
    public Observable<JobTaskByDeptBean> getJobtaskByDept(String evatype, String deptid) {
        return apiService.getJobtaskByDept(PersonInfoManager.getInstance().getToken(), evatype, deptid);
    }

    @Override
    public Observable<CustomResponse> saveManage(String modelJson) {
        return apiService.saveManage(modelJson);
    }

    @Override
    public Observable<CustomResponse> saveWork(String modelJson) {
        return apiService.saveWork(modelJson);
    }

    @Override
    public Observable<EnclosureBean> selectAttachmentApp(String businessid) {
        return apiService.selectAttachmentApp(businessid);
    }

    @Override
    public Observable<CustomResponse> insertTroubleaccept(String modelJson) {
        return apiService.insertTroubleaccept(modelJson);
    }

    @Override
    public Observable<ToBeRectifiedBean> selectDXAList(String userid) {
        return apiService.selectDXAList(userid);
    }

    @Override
    public Observable<CancelCaseBean> insertCaseclose(String modelJson) {
        return apiService.insertCaseclose(modelJson);
    }

    @Override
    public Observable<AqqrpbBean> workingFaceList(String PageId) {
        return apiService.workingFaceList(PageId);
    }

    @Override
    public Observable<AqqrpbBean> workingFaceDetail(String csid) {
        return apiService.workingFaceDetail(csid);
    }

    @Override
    public Observable<ToBeRectifiedBean> selectUncomitList(String userid) {
        return apiService.selectUncomitList(userid);
    }

    @Override
    public Observable<ListQuestionsBean> listQuestions(String testid) {
        return apiService.listQuestions(testid);
    }

    @Override
    public Observable<QuestionListBean> testlist(String userid) {
        return apiService.testlist(userid);
    }

    @Override
    public Observable<QuestionResultBean> questionCommit(String modelJson) {
        return apiService.questionCommit(modelJson);
    }

    @Override
    public Observable<CustomResponse> modifypwd(String userid, String pwd) {
        return apiService.modifypwd(userid, pwd);
    }

    @Override
    public Observable<CustomResponse> workingFaceUpdate(String csid, String detailids, List<File> files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        MultipartBody.Part part;
        if (files.isEmpty()) {
            part = MultipartBody.Part.createFormData("", "");
            partList.add(part);
        } else {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                partList.add(part);
            }
        }

        return apiService.workingFaceUpdate(csid, detailids, partList);
    }

    @Override
    public Observable<LoginStateBean> loginState() {
        return apiService.loginState();
    }

    @Override
    public Observable<AppVersionUpdateBean> updateapp() {
        return apiService.updateapp();
    }

}
