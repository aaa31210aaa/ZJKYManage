package com.zhks.safetyproduction.ui.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityPersonnelMutilQueryBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.PersonnelMutilViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class PersonnelMutilQueryActivity extends BaseActivity<ActivityPersonnelMutilQueryBinding, PersonnelMutilViewModel> {
    private int page = 1;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_personnel_mutil_query;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("人员选择");
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("确定");
    }

    @Override
    public PersonnelMutilViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(PersonnelMutilViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.multiSubmitEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent();
                intent.putExtra("mans", s);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.getPersonData(String.valueOf(page), binding.multiRefreshlayout, false);
        binding.multiRefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                viewModel.getPersonData(String.valueOf(page), binding.multiRefreshlayout, false);
            }
        });

        binding.multiRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                viewModel.getPersonData(String.valueOf(page), binding.multiRefreshlayout, true);
            }
        });
    }
}