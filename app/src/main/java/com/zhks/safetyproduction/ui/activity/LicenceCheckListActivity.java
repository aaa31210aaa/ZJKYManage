package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityLicenceCheckBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.LicenceCheckViewModel;
import com.zhks.safetyproduction.viewmodel.SafetyTrainModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class LicenceCheckListActivity extends BaseActivity<ActivityLicenceCheckBinding, LicenceCheckViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_licence_check;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("作业许可证");
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }

    @Override
    public LicenceCheckViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(LicenceCheckViewModel.class);
    }
}