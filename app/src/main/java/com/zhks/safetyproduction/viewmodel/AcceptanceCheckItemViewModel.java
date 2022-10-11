package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.RectificationBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.ui.activity.AcceptConfirmActivity;
import com.zhks.safetyproduction.ui.activity.EvidenceUploadActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class AcceptanceCheckItemViewModel extends ItemViewModel<AcceptanceCheckViewModel> {
    public ObservableField<String> acceptCheckCotent = new ObservableField<>();
    public ObservableField<String> acceptCheckDate = new ObservableField<>();
    public ObservableField<String> acceptCheckReason = new ObservableField<>();
    private ToBeRectifiedBean.DataDTO bean;

    public AcceptanceCheckItemViewModel(@NonNull AcceptanceCheckViewModel viewModel, ToBeRectifiedBean.DataDTO bean) {
        super(viewModel);
        acceptCheckCotent.set(bean.getTrcategoryname());
        acceptCheckDate.set(bean.getTrfounddate());
        acceptCheckReason.set(bean.getTrdescribe());
        this.bean = bean;
    }


    /**
     * item 点击
     */
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.itemClick(bean.getTrid());
        }
    });

}
