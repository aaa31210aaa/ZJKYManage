package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.entity.SafeCheckBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class SafeCheckListViewModel extends ToolbarViewModel<HomeModel> {
    public ObservableInt loadingVisible = new ObservableInt(View.VISIBLE);

    public SafeCheckListViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
    }

    public ItemBinding<SafeCheckItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.safe_check_item_layout);

    public ObservableList<SafeCheckItemViewModel> itemViewModels = new ObservableArrayList<>();


    public void initData(String titleName, SmartRefreshLayout refreshLayout) {
        setRightIconVisible(View.GONE);
        setTitleText(titleName);
        getCheckList(refreshLayout);
    }

    public void getCheckList(SmartRefreshLayout refreshLayout){
        itemViewModels.clear();
        model.safeCheckList(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(SafeCheckListViewModel.this)
                .subscribe(new Consumer<SafeCheckBean>() {
                    @Override
                    public void accept(SafeCheckBean baseResponse) throws Exception {
                        loadingVisible.set(View.GONE);
                        if (TextUtils.equals(Constants.SUCCESS_STR, baseResponse.getSuccess())) {
                            for (int i = 0; i < baseResponse.getCells().size() ; i++) {
                                itemViewModels.add(new SafeCheckItemViewModel(SafeCheckListViewModel.this, baseResponse.getCells().get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingVisible.set(View.GONE);
                        Log.d("SafeCheckListViewModel", throwable.toString());
                        refreshLayout.finishRefresh();
                    }
                });

    }

}
