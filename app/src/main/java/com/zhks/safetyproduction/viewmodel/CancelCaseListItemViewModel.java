package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.ui.activity.CancelCaseDetailActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class CancelCaseListItemViewModel extends ItemViewModel<CancelCaseListViewModel> {
    public ObservableField<String> yhmsTv = new ObservableField<>();
    public ObservableField<String> yhszqyTv = new ObservableField<>();
    public ObservableField<String> yhyssjTv = new ObservableField<>();

    private ToBeRectifiedBean.DataDTO bean;

    public CancelCaseListItemViewModel(@NonNull CancelCaseListViewModel viewModel, ToBeRectifiedBean.DataDTO bean) {
        super(viewModel);
        yhmsTv.set(bean.getTrdescribe());
        yhszqyTv.set(bean.getScregion());
        yhyssjTv.set(bean.getTadate());
        this.bean = bean;
    }

    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.ItemClick(bean.getTrid());
        }
    });
}
