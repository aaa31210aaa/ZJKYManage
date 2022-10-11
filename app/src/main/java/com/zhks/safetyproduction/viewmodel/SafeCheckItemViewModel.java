package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.entity.CustomPopBean;
import com.zhks.safetyproduction.entity.SafeCheckBean;
import com.zhks.safetyproduction.ui.activity.SafeCheckDetailActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class SafeCheckItemViewModel extends ItemViewModel<SafeCheckListViewModel> {
    private SafeCheckBean.CellsDTO mBean;
    public ObservableField<String> safeCheckScmname = new ObservableField<>();
    public ObservableField<String> safeCheckScmdescribe = new ObservableField<>();

    public SafeCheckItemViewModel(@NonNull SafeCheckListViewModel viewModel, SafeCheckBean.CellsDTO bean) {
        super(viewModel);
        this.mBean = bean;
        safeCheckScmname.set(mBean.getScmname());
        safeCheckScmdescribe.set(mBean.getScmdescribe());
    }


    /**
     * 安全检查表子项点击事件
     */
    public BindingCommand safeCheckItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("pageTitle", mBean.getScmname());
            bundle.putSerializable("bean", mBean);
            viewModel.startActivity(SafeCheckDetailActivity.class, bundle);
        }
    });

}
