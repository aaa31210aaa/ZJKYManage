package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.ui.activity.AqqrpbDetailActivity;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AqqrpbListViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<AqqrpbItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.aqqrpb_item_layout);
    public ObservableList<AqqrpbItemViewModel> itemViewModels = new ObservableArrayList<>();
    public SingleLiveEvent<AqqrpbBean.RowsDTO> itemClickEvent = new SingleLiveEvent();
    public AqqrpbListViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void workingFaceList(String pageId, SmartRefreshLayout refreshLayout) {
        model.workingFaceList(pageId)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AqqrpbListViewModel.this)
                .subscribe(new Consumer<AqqrpbBean>() {
                    @Override
                    public void accept(AqqrpbBean baseResponse) throws Exception {
                        if (!baseResponse.getRows().isEmpty()) {
                            for (int i = 0; i < baseResponse.getRows().size(); i++) {
                                itemViewModels.add(new AqqrpbItemViewModel(AqqrpbListViewModel.this, baseResponse.getRows().get(i)));
                            }
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("workingFaceList", throwable.toString());
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                });
    }

    public void itemClick(AqqrpbBean.RowsDTO bean) {
        itemClickEvent.setValue(bean);
    }
}
