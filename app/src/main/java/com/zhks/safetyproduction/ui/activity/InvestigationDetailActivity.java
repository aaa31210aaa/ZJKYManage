package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivityInvestigationDetailBinding;
import com.zhks.safetyproduction.entity.CommonBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.InvestigationDetailModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class InvestigationDetailActivity extends BaseActivity<ActivityInvestigationDetailBinding, InvestigationDetailModel> {
    public CommonBean commonBean;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_investigation_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("现场调查取证");
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Bundle bundle = getIntent().getExtras();
        commonBean = (CommonBean) bundle.get("commonBean");
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.accidentName.set(commonBean.getName());
        viewModel.accidentType.set(commonBean.getType());
        viewModel.accidentDate.set(commonBean.getDate());
        viewModel.accidentAddress.set(commonBean.getAddress());
        viewModel.accidentSituation.set(commonBean.getSituation());
    }
}