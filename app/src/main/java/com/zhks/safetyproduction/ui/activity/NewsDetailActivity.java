package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityNewsDetailBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.NewsDetailViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class NewsDetailActivity extends BaseActivity<ActivityNewsDetailBinding, NewsDetailViewModel> {
    public String messageid;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_news_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }

    @Override
    public NewsDetailViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(NewsDetailViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        messageid = bundle.getString("messageid");
        viewModel.setMessageId(messageid);
    }

}