package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;


import com.zhks.safetyproduction.ui.activity.HomeActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class SplashViewModel extends BaseViewModel {
    public ObservableField<String> countDown = new ObservableField<>("跳过");
    private CountDownTimer mTimer;

    public BindingCommand skipClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(HomeActivity.class);
            mTimer.cancel();
            finish();
        }
    });

    public SplashViewModel(@NonNull Application application, CountDownTimer timer) {
        super(application);
        this.mTimer = timer;
    }



}
