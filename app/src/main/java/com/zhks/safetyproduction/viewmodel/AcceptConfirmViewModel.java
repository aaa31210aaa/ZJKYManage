package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class AcceptConfirmViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent sfhgEvent = new SingleLiveEvent();
    public SingleLiveEvent acceptDateEvent = new SingleLiveEvent();
    public SingleLiveEvent xhqrrEvent = new SingleLiveEvent();
    public SingleLiveEvent rightEvent = new SingleLiveEvent();
    public SingleLiveEvent troubleAcceptEvenet = new SingleLiveEvent();


    public AcceptConfirmViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    /**
     * 是否合格
     */
    public BindingCommand sfhgClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            sfhgEvent.call();
        }
    });

    /**
     * 验收时间
     */
    public BindingCommand acceptDateClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            acceptDateEvent.call();
        }
    });

    /**
     * 销号确认人
     */
    public BindingCommand xhqrrClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            xhqrrEvent.call();
        }
    });

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        rightEvent.call();
    }

    public void insertTroubleaccept(String modelJson) {
        model.insertTroubleaccept(modelJson)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AcceptConfirmViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            troubleAcceptEvenet.call();
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("AcceptConfirmViewModel", throwable.toString());
                    }
                });
    }
}
