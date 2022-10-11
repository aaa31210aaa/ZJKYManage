package com.zhks.safetyproduction.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivitySafeCheckActivityBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.SafeCheckViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 安全检查
 */
public class SafeCheckActivity extends BaseActivity<ActivitySafeCheckActivityBinding, SafeCheckViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_safe_check_activity;
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
        viewModel.setTitleText("安全检查");
    }
}