package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.ui.activity.AqqrpbDetailActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class AqqrpbItemViewModel extends ItemViewModel<AqqrpbListViewModel> {
    private AqqrpbBean.RowsDTO bean;
    public ObservableField<String> pbDate = new ObservableField<>();
    public ObservableField<String> pbbc = new ObservableField<>();
    public ObservableField<String> pdzydd = new ObservableField<>();
    public ObservableField<String> pbsszd = new ObservableField<>();
    public ObservableField<String> pbzymlx = new ObservableField<>();
    public ObservableField<String> pbzyry = new ObservableField<>();



    public AqqrpbItemViewModel(@NonNull AqqrpbListViewModel viewModel, AqqrpbBean.RowsDTO bean) {
        super(viewModel);
        this.bean = bean;
        pbDate.set(bean.getDates());
        pbbc.set(bean.getShift());
        pdzydd.set(bean.getWorkname());
        pbsszd.set(bean.getMidname());
        pbzymlx.set(bean.getWorktypename());
        pbzyry.set(bean.getEowstaff());
    }

    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.itemClick(bean);
        }
    });
}
