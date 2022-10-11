package com.zhks.safetyproduction.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivitySiteSafetyBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.SiteSafetyModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 现场安全
 */
public class SiteSafetyActivity extends BaseActivity<ActivitySiteSafetyBinding, SiteSafetyModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_site_safety;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("班前会议记录");
    }
}