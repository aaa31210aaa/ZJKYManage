package com.zhks.safetyproduction.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityAcceptanceCheckBinding;
import com.zhks.safetyproduction.entity.RiskCheckRecordsBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.AcceptanceCheckViewModel;
import com.zhks.safetyproduction.viewmodel.ConfirmRectificationViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class AcceptanceCheckActivity extends BaseActivity<ActivityAcceptanceCheckBinding, AcceptanceCheckViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_acceptance_check;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("待验收记录");
    }

    @Override
    public AcceptanceCheckViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(AcceptanceCheckViewModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.getAcceptance(binding.refreshLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.YHYSCODE) {
            viewModel.getAcceptance(binding.refreshLayout);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.itemClickEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(AcceptanceCheckActivity.this, AcceptConfirmActivity.class);
                intent.putExtra("trid", s);
                startActivityForResult(intent, Constants.YHYSCODE);
            }
        });
    }
}