package com.zhks.safetyproduction.ui.activity;

import static com.zhks.safetyproduction.constants.Constants.TO_MULTI_CODE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivitySelfInspectionBinding;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.RiskInspectionRecordsViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 风险点检列表
 */
public class RiskInspectionRecordsActivity extends BaseActivity<ActivitySelfInspectionBinding, RiskInspectionRecordsViewModel> {
    public String titleName;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_self_inspection;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        titleName = getIntent().getExtras().getString("titleName");
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText(titleName);
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("新增");
    }

    @Override
    public RiskInspectionRecordsViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(RiskInspectionRecordsViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        initRefresh();
        viewModel.getRecordsData(binding.refreshLayout, true);
    }

    private void initRefresh() {
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.getRecordsData(binding.refreshLayout, true);
            }
        });

        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.getRecordsData(binding.refreshLayout, false);
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.rightEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (titleName.contains("领导")) {
                    Intent intent = new Intent(RiskInspectionRecordsActivity.this, PatrolPostActivity.class);
                    startActivityForResult(intent, Constants.GWJL_LIST_CODE);
                } else {
                    Intent intent = new Intent(RiskInspectionRecordsActivity.this, RiskCheckDetailActivity.class);
                    startActivityForResult(intent, Constants.GWJL_LIST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == Constants.GWJL_LIST_CODE) {
//            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getRecordsData(binding.refreshLayout, true);
    }
}