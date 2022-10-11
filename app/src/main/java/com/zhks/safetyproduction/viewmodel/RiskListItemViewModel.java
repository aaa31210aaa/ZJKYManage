package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class RiskListItemViewModel extends ItemViewModel<RiskListViewModel> {
    private ToBeRectifiedBean.DataDTO mBean;
    public ObservableField<String> trdescribe = new ObservableField<>();
    public ObservableField<String> trsite = new ObservableField<>();
    public ObservableField<String> trfounddate = new ObservableField<>();

    public RiskListItemViewModel(@NonNull RiskListViewModel viewModel, ToBeRectifiedBean.DataDTO bean) {
        super(viewModel);
        this.mBean = bean;
        trdescribe.set(bean.getTrdescribe());
        trsite.set(bean.getTrsitename());
        trfounddate.set(bean.getTrfounddate());
    }

    public BindingCommand riskListItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.itemClick(mBean);
        }
    });

}
