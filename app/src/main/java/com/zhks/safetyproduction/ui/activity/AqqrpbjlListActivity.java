package com.zhks.safetyproduction.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityListAqqrpbjlBinding;
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.AqqrpbListViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class AqqrpbjlListActivity extends BaseActivity<ActivityListAqqrpbjlBinding, AqqrpbListViewModel> {
    private int pageId = 1;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_list_aqqrpbjl;
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
        viewModel.setTitleText("安全确认排班记录");
    }

    @Override
    public AqqrpbListViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(AqqrpbListViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        initRefreshLayout();
        viewModel.workingFaceList(String.valueOf(pageId), binding.refreshLayout);
    }

    private void initRefreshLayout() {
        binding.refreshLayout.setEnableScrollContentWhenLoaded(true);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageId = 1;
                viewModel.workingFaceList(String.valueOf(pageId), binding.refreshLayout);
            }
        });

        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout mRefreshLayout) {
                pageId++;
                viewModel.workingFaceList(String.valueOf(pageId), binding.refreshLayout);
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.itemClickEvent.observe(this, new Observer<AqqrpbBean.RowsDTO>() {
            @Override
            public void onChanged(AqqrpbBean.RowsDTO rowsDTO) {
                Intent intent = new Intent(AqqrpbjlListActivity.this, AqqrpbDetailActivity.class);
                intent.putExtra("bean", rowsDTO);
                startActivityForResult(intent, Constants.TO_AQQRDETAIL_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.TO_AQQRDETAIL_CODE) {
            viewModel.workingFaceList("1", binding.refreshLayout);
        }
    }
}