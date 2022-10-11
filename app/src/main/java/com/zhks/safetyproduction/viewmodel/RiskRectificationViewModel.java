package com.zhks.safetyproduction.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.RectificationBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class RiskRectificationViewModel extends ToolbarViewModel<HomeModel> {
    public List<ToBeRectifiedBean.DataDTO> mDatas = new ArrayList<>();

    public RiskRectificationViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        getData();
    }

    public ItemBinding<RiskRectificationItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.risk_rectification_item_layout);
    public ObservableList<RiskRectificationItemViewModel> itemViewModels = new ObservableArrayList<>();

    public void getData() {
        model.toBeRectified()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskRectificationViewModel.this)
                .subscribe(new Consumer<ToBeRectifiedBean>() {
                    @Override
                    public void accept(ToBeRectifiedBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            mDatas = baseResponse.getData();
                            itemViewModels.clear();
                            for (int i = 0; i < mDatas.size(); i++) {
                                itemViewModels.add(new RiskRectificationItemViewModel(RiskRectificationViewModel.this, mDatas.get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("RiskViewModel", throwable.toString());
                    }
                });
    }


}
