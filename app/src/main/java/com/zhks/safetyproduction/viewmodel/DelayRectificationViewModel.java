package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class DelayRectificationViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent delayDateCommandEvent = new SingleLiveEvent();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent delaySubmitEvent = new SingleLiveEvent();

    public DelayRectificationViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);

    }

    /**
     * 日期选择
     */
    public BindingCommand delayDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            delayDateCommandEvent.call();
        }
    });

    /**
     * 提交
     */
    public BindingCommand delaySubmit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loadingVisible.set(View.VISIBLE);
            delaySubmitEvent.call();
        }
    });

    public void insertPostpone(String modelJson) {
        model.insertPostpone(modelJson)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(DelayRectificationViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        loadingVisible.set(View.GONE);
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            ToastUtils.showShort(baseResponse.getMessage());
                            finish();
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingVisible.set(View.GONE);
                        Log.d("LoginViewModel", throwable.toString());
                    }
                });
    }
}
