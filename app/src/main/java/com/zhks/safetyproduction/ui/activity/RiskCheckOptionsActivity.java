package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityRiskCheckOptionsBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.AcceptanceCheckViewModel;
import com.zhks.safetyproduction.viewmodel.RiskCheckOptionsViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 风险检查选项
 */
public class RiskCheckOptionsActivity extends BaseActivity<ActivityRiskCheckOptionsBinding, RiskCheckOptionsViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_risk_check_options;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }

    @Override
    public void initToolBar() {
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("风险排查");
    }


    @Override
    public RiskCheckOptionsViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(RiskCheckOptionsViewModel.class);
    }

}