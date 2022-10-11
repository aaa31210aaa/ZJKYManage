package com.zhks.safetyproduction.ui.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivitySafeCheckListBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.SafeCheckListViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhks.safetyproduction.viewmodel.SafetyTrainModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class SafeCheckListActivity extends BaseActivity<ActivitySafeCheckListBinding, SafeCheckListViewModel> {
    private String titleName;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_safe_check_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Bundle bundle = getIntent().getExtras();
        titleName = bundle.getString("titleName");
    }

    @Override
    public SafeCheckListViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(SafeCheckListViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.initData(titleName, binding.refreshLayout);
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.getCheckList(binding.refreshLayout);
            }
        });

    }

}