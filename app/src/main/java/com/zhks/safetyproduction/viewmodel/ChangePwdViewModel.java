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
import com.zhks.safetyproduction.entity.QuestionResultBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class ChangePwdViewModel extends ToolbarViewModel<HomeModel> {

    public ObservableField<String> currentPwdText = new ObservableField<>();
    public ObservableField<String> newPwdText = new ObservableField<>();
    public ObservableField<String> commitNewPwdText = new ObservableField<>();
    public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent modifyPwdEvent = new SingleLiveEvent();

    /**
     * 密码显示或脱敏
     */
    public BindingCommand pwdShowHideClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            pSwitchEvent.setValue(pSwitchEvent.getValue() == null || !pSwitchEvent.getValue());
        }
    });

    /**
     * 确认修改密码
     */
    public BindingCommand changePwdConfirm = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           modifyPwdEvent.call();
        }
    });

    public ChangePwdViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void modifyPwd(String newPwd){
        loadingVisible.set(View.VISIBLE);
        model.modifypwd(PersonInfoManager.getInstance().getUserId(), newPwd)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(ChangePwdViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            ToastUtils.showShort(baseResponse.getMessage());
                            finish();
                            loadingVisible.set(View.GONE);
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                            loadingVisible.set(View.GONE);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("modifyPwd", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }


}

