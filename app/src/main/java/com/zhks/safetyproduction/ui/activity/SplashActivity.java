package com.zhks.safetyproduction.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivitySplashBinding;
import com.zhks.safetyproduction.manager.MyPreferencesManager;
import com.zhks.safetyproduction.utils.SystemUtils;
import com.zhks.safetyproduction.viewmodel.SplashViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {
    public CountDownTimer timer;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SplashViewModel initViewModel() {
        timer = new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long sin) {
            }

            @Override
            public void onFinish() {
                startActivity(HomeActivity.class);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };

        return new SplashViewModel(getApplication(), timer);
    }

    @Override
    public void initData() {
        SystemUtils.setTranslucentStatus(this);
        timer.start();
    }

    @Override
    public void initViewObservable() {

    }

}