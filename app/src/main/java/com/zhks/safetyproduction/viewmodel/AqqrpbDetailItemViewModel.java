package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.AqqrpbBean;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class AqqrpbDetailItemViewModel extends ItemViewModel<AqqrpbDetailViewModel> {
    private AqqrpbBean.RowsDTO bean;

    public AqqrpbDetailItemViewModel(@NonNull AqqrpbDetailViewModel viewModel, AqqrpbBean.RowsDTO bean) {
        super(viewModel);
    }
}
