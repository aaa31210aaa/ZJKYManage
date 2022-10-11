package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.CommonBean;
import com.zhks.safetyproduction.ui.activity.InvestigationDetailActivity;
import com.zhks.safetyproduction.ui.activity.SafeCheckListActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class InvestigationItemModel extends ItemViewModel<InvestigationModel> {
    public ObservableField<String> accidentName = new ObservableField<>();
    public ObservableField<String> accidentType = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    private CommonBean commonBean;


    public InvestigationItemModel(@NonNull InvestigationModel viewModel, CommonBean bean) {
        super(viewModel);
        this.commonBean = bean;
        initDate();
    }

    private void initDate() {
        accidentName.set(commonBean.getName());
        accidentType.set(commonBean.getType());
        date.set(commonBean.getDate());
    }

    public BindingCommand investigationItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putSerializable("commonBean",commonBean);
            viewModel.startActivity(InvestigationDetailActivity.class,bundle);
        }
    });
}
