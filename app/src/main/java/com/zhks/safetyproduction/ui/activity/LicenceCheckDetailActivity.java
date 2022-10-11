package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityLicenceCheckDetailBinding;
import com.zhks.safetyproduction.entity.LicenceCheckBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.LicenceCheckDetailViewModel;
import com.zhks.safetyproduction.viewmodel.SafeCheckDetailViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class LicenceCheckDetailActivity extends BaseActivity<ActivityLicenceCheckDetailBinding, LicenceCheckDetailViewModel> {
    private LicenceCheckBean.DataDTO licence;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_licence_check_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("许可证详情");
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            licence = (LicenceCheckBean.DataDTO) bundle.get("licence");
        }
    }

    @Override
    public LicenceCheckDetailViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(LicenceCheckDetailViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setLicence(licence);
    }
}