package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityCommonItemBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.HomeItemCommonViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

import com.zhks.safetyproduction.entity.MenuBean.CellsDTO;
import com.zhks.safetyproduction.viewmodel.HomeViewModel;


public class HomeItemCommonActivity extends BaseActivity<ActivityCommonItemBinding, HomeItemCommonViewModel> {
    private CellsDTO cells;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_common_item;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public HomeItemCommonViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(HomeItemCommonViewModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }


    @Override
    public void initData() {
        super.initData();
        cells = (CellsDTO) getIntent().getSerializableExtra("cells");
        viewModel.setCellsBean(cells);
    }

}