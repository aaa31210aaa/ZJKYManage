package com.zhks.safetyproduction.viewmodel;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.ApprovalhisBean;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class LicenceCheckDetailItemViewModel extends ItemViewModel<LicenceCheckDetailViewModel> {
    public ObservableInt processTopLineVisible = new ObservableInt(View.VISIBLE);
    public ObservableInt processTopLineColor = new ObservableInt();
    public ObservableInt processIcon = new ObservableInt();
    public ObservableInt processBottomLineVisible = new ObservableInt();
    public ObservableInt processBottomLineColor = new ObservableInt();
    public ObservableField<String> approverRank = new ObservableField<>();
    public ObservableField<String> processDate = new ObservableField<>();
    public ObservableField<String> approverName = new ObservableField<>();
    public ObservableField<String> approverStatus = new ObservableField<>();
    public ObservableField<String> approveProposal = new ObservableField<>();
    private ApprovalhisBean.RowsDTO approvalhis;

    public LicenceCheckDetailItemViewModel(@NonNull LicenceCheckDetailViewModel viewModel, ApprovalhisBean.RowsDTO rows) {
        super(viewModel);
        approvalhis = rows;
        processTopLineColor.set(R.color.gray_ccc);
    }
}
