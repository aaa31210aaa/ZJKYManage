package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivitySettingBinding;
import com.zhks.safetyproduction.utils.GlideUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.SettingViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettingViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initData() {
        GlideUtil.displayCircle(binding.infoIcon, R.drawable.login_default_headimg, true, this);
    }


}