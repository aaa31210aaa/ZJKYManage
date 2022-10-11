package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.example.zhouwei.library.CustomPopWindow;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityRiskRectificationBinding;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.RiskRectificationViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.goldze.mvvmhabit.base.BaseActivity;

public class RiskRectificationActivity extends BaseActivity<ActivityRiskRectificationBinding, RiskRectificationViewModel> {
    public View delayPopView;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_risk_rectification;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public RiskRectificationViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(RiskRectificationViewModel.class);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("待整改列表");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(String str) {
        viewModel.getData();
    }
}