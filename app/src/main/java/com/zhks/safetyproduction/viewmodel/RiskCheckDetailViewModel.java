package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.TO_MULTI_CODE;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.MiningAreaBean;
import com.zhks.safetyproduction.entity.PositionInfoBean;
import com.zhks.safetyproduction.entity.RiskCheckItemBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.ui.activity.HiddenReportDetailActivity;
import com.zhks.safetyproduction.ui.activity.MultistageActivity;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.ApiDisposableObserver;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class RiskCheckDetailViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent djAddressEvent = new SingleLiveEvent();
    public SingleLiveEvent djDateEvent = new SingleLiveEvent();
    public SingleLiveEvent<List<RiskCheckItemBean.DataDTO>> itemListEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<PositionInfoBean.DataDTO>> jobtaskEvent = new SingleLiveEvent<>();
    public SingleLiveEvent saveEvent = new SingleLiveEvent();
    public SingleLiveEvent submitEvent = new SingleLiveEvent();
    public SingleLiveEvent saveManageEvent = new SingleLiveEvent();
    public SingleLiveEvent saveWorksEvent = new SingleLiveEvent();


    public RiskCheckDetailViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void getJobtaskByUserId() {
        model.getJobtaskByUserId(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskCheckDetailViewModel.this)
                .subscribe(new Consumer<PositionInfoBean>() {
                    @Override
                    public void accept(PositionInfoBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            if (!baseResponse.getData().isEmpty()) {
                                jobtaskEvent.setValue(baseResponse.getData());
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("RiskDetailViewModel", throwable.toString());
                    }
                });

    }

    public void getItemList(String evaid) {
        model.getItemList(evaid)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskCheckDetailViewModel.this)
                .subscribe(new Consumer<RiskCheckItemBean>() {
                    @Override
                    public void accept(RiskCheckItemBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            for (int i = 0; i < baseResponse.getData().size(); i++) {
                                baseResponse.getData().get(i).setClickble(true);
                            }
                            itemListEvent.setValue(baseResponse.getData());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("getItemList", throwable.toString());
                    }
                });
    }


    /**
     * 点检地点
     */
    public BindingCommand djAddressCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            djAddressEvent.call();
        }
    });


    /**
     * 点检时间
     */
    public BindingCommand djDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            djDateEvent.call();
        }
    });

    /**
     * 保存
     */
    public BindingCommand saveCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            saveEvent.call();
        }
    });

    /**
     * 提交
     */
    public BindingCommand submitCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            submitEvent.call();
        }
    });

    public void saveManage(String modelJson) {
        model.saveManage(modelJson)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskCheckDetailViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            saveManageEvent.call();
                            ToastUtils.showShort(baseResponse.getMessage());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("saveManage", throwable.toString());
                    }
                });
    }

    public void saveWork(String modelJson) {
        model.saveWork(modelJson)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskCheckDetailViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            saveWorksEvent.call();
                            ToastUtils.showShort(baseResponse.getMessage());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("saveWork", throwable.toString());
                    }
                });
    }
}
