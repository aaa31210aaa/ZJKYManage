package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.LoginStateBean;
import com.zhks.safetyproduction.entity.MultistageBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;

public class HomeActivityViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent loginStateEvent = new SingleLiveEvent();

    public HomeActivityViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        getLoginState();
    }

    public void getLoginState() {
        model.loginState()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(HomeActivityViewModel.this)
                .subscribe(new Consumer<LoginStateBean>() {
                    @Override
                    public void accept(LoginStateBean baseResponse) throws Exception {
                        if (baseResponse.getCode().equals(Constants.SUCCESS_CODE)) {
                            if (baseResponse.getState().equals("0")) {
                                loginStateEvent.call();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("MultistageViewModel", throwable.toString());
                    }
                });
    }
}
