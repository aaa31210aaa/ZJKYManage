package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.SUCCESS_STR;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CustomStringResponse;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class AccidentDetailModel extends ToolbarViewModel<HomeModel> {
    public ObservableField<String> accidentName = new ObservableField<>();
    //轻伤
    public ObservableField<String> minorWoundNumOfPeople = new ObservableField<>("0");
    //重伤
    public ObservableField<String> seriouslyInjured = new ObservableField<>("0");
    //失踪
    public ObservableField<String> missing = new ObservableField<>("0");
    //死亡
    public ObservableField<String> death = new ObservableField<>("0");
    //预估损失
    public ObservableField<String> estimateLoss = new ObservableField<>("0");
    //初步原因分析
    public ObservableField<String> accidentCause = new ObservableField<>();
    //事故简要经过
    public ObservableField<String> accidentBriefProcess = new ObservableField<>();
    //现场处理情况
    public ObservableField<String> accidentSiteTreatment = new ObservableField<>();
    //已采取措施
    public ObservableField<String> accidentMeasuresTaken = new ObservableField<>();
    public SingleLiveEvent accidentCategoryEvent = new SingleLiveEvent();
    public SingleLiveEvent accidentTypeEvent = new SingleLiveEvent();
    public SingleLiveEvent accidentLevelEvent = new SingleLiveEvent();
    public SingleLiveEvent accidentCompanyEvent = new SingleLiveEvent();
    public SingleLiveEvent accidentLeaderEvent = new SingleLiveEvent();
    public SingleLiveEvent accidentCauseAnalysisEvent = new SingleLiveEvent();
    public SingleLiveEvent accidentDateEvent = new SingleLiveEvent();
    public SingleLiveEvent rightTextClick = new SingleLiveEvent();
    private Context context;
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.SGFLDTO>> accidentTypeCall = new SingleLiveEvent<>();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.SGLXDTO>> accidentCategoryCall = new SingleLiveEvent<>();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.SGDJDTO>> accidentLevelCall = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DepartmentBean.CellsDTO.DateDTO>> departMentEvent = new SingleLiveEvent<>();
//    public SingleLiveEvent<List<DeptUserBean.CellsDTO>> deptUserEvent = new SingleLiveEvent<>();

    public AccidentDetailModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
        this.context = context;
        getData();
    }

    public void getData() {
        //请求事故类别 事故种类 事故等级
        model.getAccidentDictionaries("SGFL,SGLX,SGDJ")
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AccidentDetailModel.this)
                .subscribe(new Consumer<AccidentDetailBean>() {
                    @Override
                    public void accept(AccidentDetailBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            accidentTypeCall.setValue(baseResponse.getData().getSGFL());
                            accidentCategoryCall.setValue(baseResponse.getData().getSGLX());
                            accidentLevelCall.setValue(baseResponse.getData().getSGDJ());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("AccidentDetailModel", throwable.toString());
                    }
                });

        //请求部门单位
        model.getDepartment()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AccidentDetailModel.this)
                .subscribe(new Consumer<DepartmentBean>() {
                    @Override
                    public void accept(DepartmentBean baseResponse) throws Exception {
                        if (TextUtils.equals("0000", baseResponse.getCode())) {
                            departMentEvent.setValue(baseResponse.getCells().get(0).getDate());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("AccidentDetailModel", throwable.toString());
                    }
                });

    }

//    public void getDeptUser(String deptId, String pageId, SmartRefreshLayout refreshLayout) {
//        model.getListUser(deptId, pageId)
//                .compose(RxUtils.schedulersTransformer())
//                .doOnSubscribe(AccidentDetailModel.this)
//                .subscribe(new Consumer<DeptUserBean>() {
//                    @Override
//                    public void accept(DeptUserBean baseResponse) throws Exception {
//                        if (TextUtils.equals(SUCCESS_STR, baseResponse.getSuccess())) {
//                            deptUserEvent.setValue(baseResponse.getCells());
//                        } else {
//                            ToastUtils.showShort(baseResponse.getMessage());
//                        }
//                        refreshLayout.finishLoadMore();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.d("AccidentDetailModel", throwable.toString());
//                    }
//                });
//    }


    /**
     * 事故类别
     */
    public BindingCommand accidentCategory = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            accidentCategoryEvent.call();
        }
    });

    /**
     * 事故种类
     */
    public BindingCommand accidentTypeCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            accidentTypeEvent.call();
        }
    });

    /**
     * 事故等级
     */
    public BindingCommand accidentLevelCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            accidentLevelEvent.call();
        }
    });

    /**
     * 事故单位
     */
    public BindingCommand accidentCompanyCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            accidentCompanyEvent.call();
        }
    });

    /**
     * 单位负责人
     */
    public BindingCommand accidentLeaderCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            accidentLeaderEvent.call();
        }
    });

    /**
     * 事故时间
     */
    public BindingCommand accidentDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            accidentDateEvent.call();
        }
    });



    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        rightTextClick.call();
    }

    public void accidentInfo(String accidentJson, List<File> fileList) {
        model.addAccidentreport(accidentJson, fileList)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AccidentDetailModel.this)
                .subscribe(new Consumer<CustomStringResponse>() {
                    @Override
                    public void accept(CustomStringResponse baseResponse) throws Exception {
                        if (TextUtils.equals(SUCCESS_STR, baseResponse.getSuccess())) {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                            finish();
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("AccidentDetailModel", throwable.toString());
                    }
                });

    }
}
