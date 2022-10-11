package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.alibaba.fastjson.JSON;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.ui.activity.DraftListActivity;
import com.zhks.safetyproduction.ui.activity.RiskRegisterActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class RiskListViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<RiskListItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.risk_list_item_layout);
    public ObservableList<RiskListItemViewModel> itemViewModels = new ObservableArrayList<>();
    public List<ToBeRectifiedBean.DataDTO> mDatas = new ArrayList<>();
    public SingleLiveEvent itemClickEvent = new SingleLiveEvent();
    public SingleLiveEvent registerRisk = new SingleLiveEvent();

    public RiskListViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void getRiskListData(SmartRefreshLayout refreshLayout, String type) {
        model.toBeRectified()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskListViewModel.this)
                .subscribe(new Consumer<ToBeRectifiedBean>() {
                    @Override
                    public void accept(ToBeRectifiedBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            itemViewModels.clear();
                            mDatas = baseResponse.getData();
                            if (TextUtils.equals("0", type)) { //0 一般隐患  1 重大隐患
                                for (int i = 0; i < mDatas.size(); i++) {
                                    if (TextUtils.equals(Constants.YHJB001, mDatas.get(i).getTrlevel())) {
                                        itemViewModels.add(new RiskListItemViewModel(RiskListViewModel.this, mDatas.get(i)));
                                    }
                                }
                            } else {
                                for (int i = 0; i < mDatas.size(); i++) {
                                    if (TextUtils.equals(Constants.YHJB002, mDatas.get(i).getTrlevel())) {
                                        itemViewModels.add(new RiskListItemViewModel(RiskListViewModel.this, mDatas.get(i)));
                                    }
                                }
                            }


                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("RiskViewModel", throwable.toString());
                    }
                });
    }

    public void selectUncomitList(SmartRefreshLayout refreshLayout, String type) {
        model.selectUncomitList(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskListViewModel.this)
                .subscribe(new Consumer<ToBeRectifiedBean>() {
                    @Override
                    public void accept(ToBeRectifiedBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            itemViewModels.clear();
                            mDatas = baseResponse.getData();
                            if (TextUtils.equals("0", type)) { //0 一般隐患  1 重大隐患
                                for (int i = 0; i < mDatas.size(); i++) {
                                    if (TextUtils.equals(Constants.YHJB001, mDatas.get(i).getTrlevel())) {
                                        itemViewModels.add(new RiskListItemViewModel(RiskListViewModel.this, mDatas.get(i)));
                                    }
                                }
                            } else {
                                for (int i = 0; i < mDatas.size(); i++) {
                                    if (TextUtils.equals(Constants.YHJB002, mDatas.get(i).getTrlevel())) {
                                        itemViewModels.add(new RiskListItemViewModel(RiskListViewModel.this, mDatas.get(i)));
                                    }
                                }
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("selectUncomitList", throwable.toString());
                    }
                });

    }

    /**
     * 新增一般隐患
     */
    @Override
    protected void rightIconOnClick() {
        super.rightIconOnClick();
        registerRisk.call();
    }

    /**
     * 草稿列表
     */
    public BindingCommand toDraftListCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("type", Constants.DRAFT_TYPE_YH);
            startActivity(DraftListActivity.class, bundle);
        }
    });

    public void itemClick(ToBeRectifiedBean.DataDTO bean) {
        itemClickEvent.setValue(bean);
    }
}
