package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivityInvestigationBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.InvestigationModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class InvestigationActivity extends BaseActivity<ActivityInvestigationBinding, InvestigationModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_investigation;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
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
        viewModel.setTitleText("选择取证");
    }

    @Override
    public void initData() {
        super.initData();

    }
}