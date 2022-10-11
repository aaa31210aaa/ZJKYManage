package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivityRiskManagementBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.RiskManagerMentModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 风险管理
 */
public class RiskManagementActivity extends BaseActivity<ActivityRiskManagementBinding, RiskManagerMentModel>{

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_risk_management;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("风险管理");
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }
}