package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;

import me.goldze.mvvmhabit.base.BaseActivity;

public class RiskManagerDetailActivity extends BaseActivity {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_risk_manager_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}