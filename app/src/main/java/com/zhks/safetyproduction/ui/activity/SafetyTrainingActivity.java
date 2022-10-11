package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivitySafetyTrainingBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.NewsDetailViewModel;
import com.zhks.safetyproduction.viewmodel.SafetyTrainModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 安全培训
 */
public class SafetyTrainingActivity extends BaseActivity<ActivitySafetyTrainingBinding, SafetyTrainModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_safety_training;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SafetyTrainModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(SafetyTrainModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("安全培训");
    }
}