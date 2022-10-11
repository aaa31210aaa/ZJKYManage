package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
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
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.entity.QuestionListBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class QuestionListViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<QuestionListItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.question_list_item_layout);
    public ObservableList<QuestionListItemViewModel> itemViewModels = new ObservableArrayList<>();
    public SingleLiveEvent<QuestionListBean.DataDTO> itemClickEvent = new SingleLiveEvent();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);

    public QuestionListViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void testList(SmartRefreshLayout refreshLayout) {
        loadingVisible.set(View.VISIBLE);
        itemViewModels.clear();
        model.testlist(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(QuestionListViewModel.this)
                .subscribe(new Consumer<QuestionListBean>() {
                    @Override
                    public void accept(QuestionListBean baseResponse) throws Exception {
                        if (baseResponse.getCode().equals(Constants.SUCCESS_CODE)) {
                            for (int i = 0; i < baseResponse.getData().size(); i++) {
                                itemViewModels.add(new QuestionListItemViewModel(QuestionListViewModel.this, baseResponse.getData().get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        refreshLayout.finishRefresh();
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("testList", throwable.toString());
                        refreshLayout.finishRefresh();
                        loadingVisible.set(View.GONE);
                    }
                });
    }

    public void setItemClick(QuestionListBean.DataDTO bean) {
        itemClickEvent.setValue(bean);
    }
}
