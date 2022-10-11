package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.model.HomeModel;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ConfirmRectificationViewModel extends ToolbarViewModel<HomeModel> {
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent completeDateEvent = new SingleLiveEvent();
    public SingleLiveEvent completeSubmitEvent = new SingleLiveEvent();
    public SingleLiveEvent submitSuccessEvent = new SingleLiveEvent();

    public ConfirmRectificationViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    /**
     * 完成整改日期
     */
    public BindingCommand completeDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            completeDateEvent.call();
        }
    });

    /**
     * 提交
     */
    public BindingCommand completeSubmit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            completeSubmitEvent.call();
        }
    });

    public void submit(String modelJson, List<File> files) {
        loadingVisible.set(View.VISIBLE);
        model.insertRectification(modelJson, files)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(ConfirmRectificationViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            submitSuccessEvent.call();
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("qrzg", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }
}
