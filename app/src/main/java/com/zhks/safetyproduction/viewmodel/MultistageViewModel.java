package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.MultChoiceBean;
import com.zhks.safetyproduction.entity.MultistageBean;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MultistageViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent<JSONObject> resultDatas = new SingleLiveEvent<>();
    public ObservableInt loadingVisible = new ObservableInt(View.VISIBLE);

    public MultistageViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void getAreaTree(String areaId) {
        model.getAreaTree(areaId)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(MultistageViewModel.this)
                .subscribe(new Consumer<MultistageBean>() {
                    @Override
                    public void accept(MultistageBean baseResponse) throws Exception {
                        String besnString = JSONObject.toJSONString(baseResponse);
                        JSONObject jsonObject = JSON.parseObject(besnString);
                        resultDatas.setValue(jsonObject);
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("MultistageViewModel", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }
}
