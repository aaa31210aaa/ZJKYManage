package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class PostManagerItemViewModel extends ItemViewModel<RiskCheckDetailViewModel> {
    public ObservableField<String> postName = new ObservableField<>();

    public PostManagerItemViewModel(@NonNull RiskCheckDetailViewModel viewModel) {
        super(viewModel);
    }
}
