package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CancelCaseListViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<CancelCaseListItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.cancel_case_item_layout);
    public ObservableList<CancelCaseListItemViewModel> itemViewModels = new ObservableArrayList<>();
    public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent();
    public CancelCaseListViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        selectDXAList();
    }

    public void selectDXAList() {
        itemViewModels.clear();
        model.selectDXAList(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(CancelCaseListViewModel.this)
                .subscribe(new Consumer<ToBeRectifiedBean>() {
                    @Override
                    public void accept(ToBeRectifiedBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            for (int i = 0; i < baseResponse.getData().size(); i++) {
                                itemViewModels.add(new CancelCaseListItemViewModel(CancelCaseListViewModel.this, baseResponse.getData().get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("CancelCaseListViewModel", throwable.toString());
                    }
                });
    }

    public void ItemClick(String trid){
        itemClickEvent.setValue(trid);
    }
}
