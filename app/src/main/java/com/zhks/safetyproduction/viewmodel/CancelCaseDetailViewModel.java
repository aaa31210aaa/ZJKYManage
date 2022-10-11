package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.CancelCaseBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;

public class CancelCaseDetailViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent xiaoanEvent = new SingleLiveEvent();
    public SingleLiveEvent xiaoanSubmitEvent = new SingleLiveEvent();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent insertCasecloseEvent = new SingleLiveEvent();

    public CancelCaseDetailViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public BindingCommand xiaoanDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            xiaoanEvent.call();
        }
    });

    public void insertCaseclose(String modelJson) {
        loadingVisible.set(View.VISIBLE);
        model.insertCaseclose(modelJson)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(CancelCaseDetailViewModel.this)
                .subscribe(new Consumer<CancelCaseBean>() {
                    @Override
                    public void accept(CancelCaseBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            insertCasecloseEvent.call();
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("insertCaseclose", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        xiaoanSubmitEvent.call();
    }
}
