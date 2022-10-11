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
     * 登录
     *
     * @param head
     * @param loginname
     * @param pwd
     * @return
     */
    @GET("mobile/login.action")
    Observable<UserBean> login(@Header("authorization") String head, @Query("loginname") String loginname, @Query("loginpwd") String pwd);

    /**
     * 获取菜单
     *
     * @param head
     * @param userId
     * @return
     */
    @GET("mobile/role/selectAppModule.action")
    Observable<MenuBean> getHomeMenu(@Header("authorization") String head, @Query("userid") String userId);

    /**
     * 获取消息列表
     *
     * @param head
     * @return
     */
    @GET("mobile/message/messageList.action")
    Observable<NewsBean> getNewsList(@Header("authorization") String head);

    /**
     * 获取消息详情
     */
    @GET("mobile/message/messageInfo.action")
    Observable<NewsDetailBean> getNewsDetail(@Header("authorization") String head, @Query("messageid") String messageid);

    /**
     * 事故快报填写
     */
    @Multipart
    @POST("mobile/mobileAccidentreport/addAccidentreport.action")
    Observable<CustomStringResponse> addAccidentreport(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * 个人培训记录查询
     */
    @GET("mobile/trainrecord/trainrecordList.action")
    Observable<SafetyTrainingBean> getTrainRecordList(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * 安全检查列表
     */
    @GET("mobile/mobileCheckReg/checkmanalist.action")
    Observable<SafeCheckBean> safeCheckList(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * 字典查询接口
     */
    @GET("mobile/check/selectDictionaryById.action")
    Observable<AccidentDetailBean> getAccidentDictionaries(@Header("authorization") String head, @Query("dicttypeids") String dicttypeids);

    /**
     * 部门查询
     */
    @GET("mobile/mobileSafe/selectDeptt.action")
    Observable<DepartmentBean> getDepartment(@Header("authorization") String head);

    /**
     * 人员查询
     */
    @GET("mobile/mobileSafe/listUser.action")
    Observable<DeptUserBean> getListUser(@Header("authorization") String head, @Query("Beanuser.deptid") String deptId, @Query("PageId") String pageId);

    /**
     * 获取危险许可证列表
     */
    @GET("mobile/dangerwork/selectWorkList.action")
    Observable<LicenceCheckBean> getLicenceCheckList(@Header("authorization") String head);

    /**
     * 查询区域列表
     */
    @GET("mobile/trregister/areatree.action")
    Observable<MultistageBean> getAreaTree(@Header("authorization") String head, @Query("areabean.checkid") String checkId);

    /**
     * 安全检查项列表
     */
    @GET("mobile/mobileCheckReg/checkitemlist.action")
    Observable<SafeCheckTermBean> getSafeCheckTerm(@Header("authorization") String head, @Query("securitycheckmana.scmid") String scmid);

    /**
     * 查询用户所属组织下的所有用户
     */
    @GET("mobile/mobileSafe/listUserByOrg.action")
    Observable<CurrentUserBean> getCurrentListUser(@Header("authorization") String head, @Query("PageId") String pageId, @Query("userid") String userId);

    /**
     * 班前会议记录
     */
    @Multipart
    @POST("mobile/mobileTeammeeting/addTeammeeting.action")
    Observable<CustomResponse> uploadMeetingRecord(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * 查询流程详情接口
     */
    @GET("manager/activity/approvalhis.action")
    Observable<ApprovalhisBean> getApprovalhis(@Header("authorization") String head, @Query("prokey") String prokey, @Query("bussinesskey") String bussinesskey);

    /**
     * 安全检查新增
     */
    @POST("mobile/mobileCheckReg/insertcheckreg.action")
    Observable<CustomResponse> insertCheckreg(@Header("authorization") String head, @Query("modelJson") String modelJson);

    /**
     * 待整改记录
     */
    @GET("mobile/trregister/selectRectificationList.action")
    Observable<ToBeRectifiedBean> toBeRectified(@Header("authorization") String head);

    /**
     * 隐患整改申请延期
     */
    @POST("moblie/postpone/insertPostpone.action")
    Observable<CustomResponse> insertPostpone(@Header("authorization") String head, @Query("modelJson") String modelJson);

    /**
     * 隐患举报
     */
    @Multipart
    @POST("mobile/mobileTroublereport/addTroublereport.action")
    Observable<CustomResponse> addTroublereport(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * 隐患登记
     */
    @Multipart
    @POST("mobile/trregister/insertRegister.action")
    Observable<CustomResponse> insertRegister(@Header("authorization") String head, @Query("modelJson") String modelJson, @Query("userid") String userid, @Part() List<MultipartBody.Part> files);

    /**
     * 隐患保存
     */
    @Multipart
    @POST("mobile/trregister/saveRegister.action")
    Observable<CustomResponse> saveRegister(@Header("authorization") String head, @Query("modelJson") String modelJson, @Query("userid") String userid, @Part() List<MultipartBody.Part> files);

    /**
     * 确认整改
     */
    @Multipart
    @POST("mobile/ectification/insertRectification.action")
    Observable<CustomResponse> insertRectification(@Header("authorization") String head, @Query("modelJson") String modelJson, @Part() List<MultipartBody.Part> files);

    /**
     * 隐患验收
     */
    @GET("mobile/trregister/selectDYSList.action")
    Observable<ToBeRectifiedBean> selectDYSList(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * 获取矿区
     */
    @GET("mobile/mobileSafe/getOrg.action")
    Observable<MiningAreaBean> getOrg(@Header("authorization") String head);

    /**
     * 岗位自查/领导巡查记录
     */
    @GET("mobile/riskcheck/getListByUserId.action")
    Observable<RiskCheckRecordsBean> getRiskRecords(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * 根据用户id获取岗位信息
     */
    @GET("mobile/jobtask/getJobtaskByUserId.action")
    Observable<PositionInfoBean> getJobtaskByUserId(@Header("authorization") String head, @Query("userid") String userid);

    /**
     * 根据岗位id查询检查项
     */
    @GET("mobile/riskcheck/itemList.action")
    Observable<RiskCheckItemBean> getItemList(@Header("authorization") String head, @Query("evaid") String evaid);

    /**
     * 根据矿区及岗位类型查询岗位列表
     */
    @GET("mobile/jobtask/getJobtaskByDept.action")
    Observable<JobTaskByDeptBean> getJobtaskByDept(@Header("authorization") String head, @Query("evatype") String evatype, @Query("deptid") String deptid);

    /**
     * 管理岗点检记录保存、提交
     */
    @POST("mobile/riskcheck/saveManage.action")
    Observable<CustomResponse> saveManage(@Query("modelJson") String modelJson);

    /**
     * 作业岗点检记录保存、提交
     */
    @POST("mobile/riskcheck/saveWork.action")
    Observable<CustomResponse> saveWork(@Query("modelJson") String modelJson);

    /**
     * 附件查询
     */
    @GET("mobile/upload/selectAttachmentApp.action")
    Observable<EnclosureBean> selectAttachmentApp(@Query("businessid") String businessid);

    /**
     * 隐患验收
     */
    @POST("mobile/check/insertTroubleaccept.action")
    Observable<CustomResponse> insertTroubleaccept(@Query("modelJson") String modelJson);

    /**
     * 待销案记录
     */
    @GET("mobile/trregister/selectDXAList.action")
    Observable<ToBeRectifiedBean> selectDXAList(@Query("userid") String userid);

    /**
     * 隐患销案
     */
    @POST("mobile/caseclose/insertCaseclose.action")
    Observable<CancelCaseBean> insertCaseclose(@Query("modelJson") String modelJson);

    /**
     * 安全确认排班记录
     */
    @GET("mobile/workingFace/list.action")
    Observable<AqqrpbBean> workingFaceList(@Query("PageId") String PageId);

    /**
     * 安全确认内容
     */
    @GET("mobile/workingFace/detail.action")
    Observable<AqqrpbBean> workingFaceDetail(@Query("csid") String csid);

    /**
     * 安全确认修改
     */
    @Multipart
    @POST("mobile/workingFace/update.action")
    Observable<CustomResponse> workingFaceUpdate(@Query("csid") String csid, @Query("detailids") String detailids, @Part() List<MultipartBody.Part> files);

    /**
     * 隐患未提交记录
     */
    @GET("mobile/trregister/selectUncomitList.action")
    Observable<ToBeRectifiedBean> selectUncomitList(@Query("userid") String userid);

    /**
     * 试题接口
     */
    @GET("mobile/question/listQuestions.action")
    Observable<ListQuestionsBean> listQuestions(@Query("testid") String testid);

    /**
     * 考试列表
     */
    @GET("mobile/question/testlist.action")
    Observable<QuestionListBean> testlist(@Query("userid") String userid);

    /**
     * 考试提交
     */
    @GET("mobile/question/commit.action")
    Observable<QuestionResultBean> questionCommit(@Query("modelJson") String modelJson);

    /**
     * 修改密码
     */
    @GET("mobile/mobileSafe/modifypwd.action")
    Observable<CustomResponse> modifypwd(@Query("userid") String userid, @Query("pwd") String pwd);

    /**
     * 登录状态
     */
    @GET("mobile/mobileSafe/loginState.action")
    Observable<LoginStateBean> loginState();

    /**
     * 检查更新
     */
    @GET("mobile/update/updateapp.action")
    Observable<AppVersionUpdateBean> updateapp();
}

