package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.JobTaskByDeptBean;
import com.zhks.safetyproduction.ui.activity.RiskCheckDetailActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class PatrolPostItemViewModel extends ItemViewModel<PatrolPostViewModel> {
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> evaname = new ObservableField<>();
    public ObservableField<String> fxdname = new ObservableField<>();
    private JobTaskByDeptBean.DataDTO bean;

    public PatrolPostItemViewModel(@NonNull PatrolPostViewModel viewModel, JobTaskByDeptBean.DataDTO dataDTO) {
        super(viewModel);
        bean = dataDTO;
        userName.set(dataDTO.getUserName());
        evaname.set(dataDTO.getEvaname() + "(岗位名称)");
        fxdname.set(dataDTO.getFxdname() + "(风险点)");
    }

    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("index", "0");
            bundle.putString("type", bean.getType());
            bundle.putString("userName", bean.getUserName());
            bundle.putString("deptname", bean.getDeptname());
            bundle.putString("evaname", bean.getEvaname());
            bundle.putString("fxdname", bean.getFxdname());
            bundle.putString("evaid", bean.getEvaid());
            viewModel.startActivity(RiskCheckDetailActivity.class, bundle);
        }
    });
}
