package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.model.MineModel;
import com.zhks.safetyproduction.ui.activity.ChangePwdActivity;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginViewModel extends ToolbarViewModel<MineModel> {
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<Integer> clearBtnVisibility = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent setResultEvent = new SingleLiveEvent();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public LoginViewModel(@NonNull Application application, MineModel model) {
        super(application, model);
    }


    public class UIChangeObservable {
        //密码开关观察者

        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    /**
     * 用户名输入框监听
     */
    public BindingCommand<Boolean> onUserFocusChangeCommand = new BindingCommand<Boolean>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
            if (aBoolean) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });

    /**
     * 用户名清除按钮
     */
    public BindingCommand clearUserOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });

    /**
     * 显示密码/不显示密码
     */
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
        }
    });

    /**
     * 忘记密码
     */
    public BindingCommand forgetPwdClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ChangePwdActivity.class);
        }
    });

    /**
     * 登录
     */
    public BindingCommand loginBtnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(userName.get())) {
                ToastUtils.showShort("请填写用户名");
                return;
            }

            if (TextUtils.isEmpty(password.get())) {
                ToastUtils.showShort("请填写密码");
                return;
            }
            loadingVisible.set(View.VISIBLE);

            //调用登录接口
            model.login(userName.get(), password.get())
                    .compose(RxUtils.schedulersTransformer())
                    .doOnSubscribe(LoginViewModel.this)
                    .subscribe(new Consumer<UserBean>() {
                        @Override
                        public void accept(UserBean baseResponse) throws Exception {
                            loadingVisible.set(View.GONE);
                            if (TextUtils.equals(Constants.SUCCESS_CODE,baseResponse.getCode())) {
                                setResultEvent.setValue(baseResponse);
                            } else {
                                ToastUtils.showShort(baseResponse.getMessage());
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
    });
}
