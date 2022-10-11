package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.RectificationBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AcceptanceCheckViewModel extends ToolbarViewModel<HomeModel> {
    public List<ToBeRectifiedBean.DataDTO> mDatas = new ArrayList<>();
    public ItemBinding<AcceptanceCheckItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.accept_check_item_layout);
    public ObservableList<AcceptanceCheckItemViewModel> itemViewModels = new ObservableArrayList<>();
    public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent();

    public AcceptanceCheckViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void getAcceptance(SmartRefreshLayout refreshLayout) {
        model.selectDYSList(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AcceptanceCheckViewModel.this)
                .subscribe(new Consumer<ToBeRectifiedBean>() {
                    @Override
                    public void accept(ToBeRectifiedBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            mDatas = baseResponse.getData();
                            itemViewModels.clear();
                            for (int i = 0; i < mDatas.size(); i++) {
                                itemViewModels.add(new AcceptanceCheckItemViewModel(AcceptanceCheckViewModel.this, mDatas.get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Acceptance", throwable.toString());
                    }
                });
    }

    public void itemClick(String trid) {
        itemClickEvent.setValue(trid);
    }
}
