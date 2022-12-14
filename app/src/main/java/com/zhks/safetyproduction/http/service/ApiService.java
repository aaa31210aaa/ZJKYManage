package com.zhks.safetyproduction.http.service;


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
import com.zhks.safetyproduction.entity.UserBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * ??????
     *
     * @param head
     * @param loginname
     * @param pwd
     * @return
     */
    @GET("mobile/login.action")
    Observable<UserBean> login(@Header("authorization") String head, @Query("loginname") String loginname, @Query("loginpwd") String pwd);

    /**
     * ????????????
     *
     * @param head
     * @param userId
     * @return
     */
    @GET("mobile/role/selectAppModule.action")
    Observable<MenuBean> getHomeMenu(@Header("authorization") String head, @Query("userid") String userId);

    /**
     * ??????????????????
     *
     * @param head
     * @return
     */
    @GET("mobile/message/messageList.action")
    Observable<NewsBean> getNewsList(@Header("authorization") String head);

    /**
     * ??????????????????
     */
    @GET("mobile/message/messageInfo.action")
    Observable<NewsDetailBean> getNewsDetail(@Header("authorization") String head, @Query("messageid") String messageid);

    /**
     * ??????????????????
     */
    @Multipart
    @POST("mobile/mobileAccidentreport/addAccidentreport.action")
    Observable<CustomStringResponse> addAccidentreport(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * ????????????????????????
     */
    @GET("mobile/trainrecord/trainrecordList.action")
    Observable<SafetyTrainingBean> getTrainRecordList(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * ??????????????????
     */
    @GET("mobile/mobileCheckReg/checkmanalist.action")
    Observable<SafeCheckBean> safeCheckList(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * ??????????????????
     */
    @GET("mobile/check/selectDictionaryById.action")
    Observable<AccidentDetailBean> getAccidentDictionaries(@Header("authorization") String head, @Query("dicttypeids") String dicttypeids);

    /**
     * ????????????
     */
    @GET("mobile/mobileSafe/selectDeptt.action")
    Observable<DepartmentBean> getDepartment(@Header("authorization") String head);

    /**
     * ????????????
     */
    @GET("mobile/mobileSafe/listUser.action")
    Observable<DeptUserBean> getListUser(@Header("authorization") String head, @Query("Beanuser.deptid") String deptId, @Query("PageId") String pageId);

    /**
     * ???????????????????????????
     */
    @GET("mobile/dangerwork/selectWorkList.action")
    Observable<LicenceCheckBean> getLicenceCheckList(@Header("authorization") String head);

    /**
     * ??????????????????
     */
    @GET("mobile/trregister/areatree.action")
    Observable<MultistageBean> getAreaTree(@Header("authorization") String head, @Query("areabean.checkid") String checkId);

    /**
     * ?????????????????????
     */
    @GET("mobile/mobileCheckReg/checkitemlist.action")
    Observable<SafeCheckTermBean> getSafeCheckTerm(@Header("authorization") String head, @Query("securitycheckmana.scmid") String scmid);

    /**
     * ??????????????????????????????????????????
     */
    @GET("mobile/mobileSafe/listUserByOrg.action")
    Observable<CurrentUserBean> getCurrentListUser(@Header("authorization") String head, @Query("PageId") String pageId, @Query("userid") String userId);

    /**
     * ??????????????????
     */
    @Multipart
    @POST("mobile/mobileTeammeeting/addTeammeeting.action")
    Observable<CustomResponse> uploadMeetingRecord(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * ????????????????????????
     */
    @GET("manager/activity/approvalhis.action")
    Observable<ApprovalhisBean> getApprovalhis(@Header("authorization") String head, @Query("prokey") String prokey, @Query("bussinesskey") String bussinesskey);

    /**
     * ??????????????????
     */
    @POST("mobile/mobileCheckReg/insertcheckreg.action")
    Observable<CustomResponse> insertCheckreg(@Header("authorization") String head, @Query("modelJson") String modelJson);

    /**
     * ???????????????
     */
    @GET("mobile/trregister/selectRectificationList.action")
    Observable<ToBeRectifiedBean> toBeRectified(@Header("authorization") String head);

    /**
     * ????????????????????????
     */
    @POST("moblie/postpone/insertPostpone.action")
    Observable<CustomResponse> insertPostpone(@Header("authorization") String head, @Query("modelJson") String modelJson);

    /**
     * ????????????
     */
    @Multipart
    @POST("mobile/mobileTroublereport/addTroublereport.action")
    Observable<CustomResponse> addTroublereport(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * ????????????
     */
    @Multipart
    @POST("mobile/trregister/insertRegister.action")
    Observable<CustomResponse> insertRegister(@Header("authorization") String head, @Query("modelJson") String modelJson, @Query("userid") String userid, @Part() List<MultipartBody.Part> files);

    /**
     * ????????????
     */
    @Multipart
    @POST("mobile/trregister/saveRegister.action")
    Observable<CustomResponse> saveRegister(@Header("authorization") String head, @Query("modelJson") String modelJson, @Query("userid") String userid, @Part() List<MultipartBody.Part> files);

    /**
     * ????????????
     */
    @Multipart
    @POST("mobile/ectification/insertRectification.action")
    Observable<CustomResponse> insertRectification(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * ????????????
     */
    @GET("mobile/trregister/selectDYSList.action")
    Observable<ToBeRectifiedBean> selectDYSList(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * ????????????
     */
    @GET("mobile/mobileSafe/getOrg.action")
    Observable<MiningAreaBean> getOrg(@Header("authorization") String head);

    /**
     * ????????????/??????????????????
     */
    @GET("mobile/riskcheck/getListByUserId.action")
    Observable<RiskCheckRecordsBean> getRiskRecords(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * ????????????id??????????????????
     */
    @GET("mobile/jobtask/getJobtaskByUserId.action")
    Observable<PositionInfoBean> getJobtaskByUserId(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * ????????????id???????????????
     */
    @GET("mobile/riskcheck/itemList.action")
    Observable<RiskCheckItemBean> getItemList(@Header("authorization") String head, @Query("evaid") String evaid);

    /**
     * ?????????????????????????????????????????????
     */
    @GET("mobile/jobtask/getJobtaskByDept.action")
    Observable<JobTaskByDeptBean> getJobtaskByDept(@Header("authorization") String head, @Query("evatype") String evatype, @Query("deptid") String deptid);

    /**
     * ????????????????????????????????????
     */
    @POST("mobile/riskcheck/saveManage.action")
    Observable<CustomResponse> saveManage(@Query("modelJson") String modelJson);

    /**
     * ????????????????????????????????????
     */
    @POST("mobile/riskcheck/saveWork.action")
    Observable<CustomResponse> saveWork(@Query("modelJson") String modelJson);

    /**
     * ????????????
     */
    @GET("mobile/upload/selectAttachmentApp.action")
    Observable<EnclosureBean> selectAttachmentApp(@Query("businessid") String businessid);

    /**
     * ????????????
     */
    @POST("mobile/check/insertTroubleaccept.action")
    Observable<CustomResponse> insertTroubleaccept(@Query("modelJson") String modelJson);

    /**
     * ???????????????
     */
    @GET("mobile/trregister/selectDXAList.action")
    Observable<ToBeRectifiedBean> selectDXAList(@Query("userid") String userid);

    /**
     * ????????????
     */
    @POST("mobile/caseclose/insertCaseclose.action")
    Observable<CancelCaseBean> insertCaseclose(@Query("modelJson") String modelJson);

    /**
     * ????????????????????????
     */
    @GET("mobile/workingFace/list.action")
    Observable<AqqrpbBean> workingFaceList(@Query("PageId") String PageId);

    /**
     * ??????????????????
     */
    @GET("mobile/workingFace/detail.action")
    Observable<AqqrpbBean> workingFaceDetail(@Query("csid") String csid);

    /**
     * ??????????????????
     */
    @Multipart
    @POST("mobile/workingFace/update.action")
    Observable<CustomResponse> workingFaceUpdate(@Query("csid") String csid, @Query("detailids") String detailids, @Part() List<MultipartBody.Part> files);

    /**
     * ?????????????????????
     */
    @GET("mobile/trregister/selectUncomitList.action")
    Observable<ToBeRectifiedBean> selectUncomitList(@Query("userid") String userid);

    /**
     * ????????????
     */
    @GET("mobile/question/listQuestions.action")
    Observable<ListQuestionsBean> listQuestions(@Query("testid") String testid);

    /**
     * ????????????
     */
    @GET("mobile/question/testlist.action")
    Observable<QuestionListBean> testlist(@Query("userid") String userid);

    /**
     * ????????????
     */
    @GET("mobile/question/commit.action")
    Observable<QuestionResultBean> questionCommit(@Query("modelJson") String modelJson);

    /**
     * ????????????
     */
    @GET("mobile/mobileSafe/modifypwd.action")
    Observable<CustomResponse> modifypwd(@Query("userid") String userid, @Query("pwd") String pwd);

    /**
     * ????????????
     */
    @GET("mobile/mobileSafe/loginState.action")
    Observable<LoginStateBean> loginState();

    /**
     * ????????????
     */
    @GET("mobile/update/updateapp.action")
    Observable<AppVersionUpdateBean> updateapp();
}

