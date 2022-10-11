package com.zhks.safetyproduction.ui.activity;

import static com.zhks.safetyproduction.constants.Constants.TO_REGISTER_RISK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityRiskListBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.RiskListViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class RiskListActivity extends BaseActivity<ActivityRiskListBinding, RiskListViewModel> {
    private Bundle bundle;
    public String tag;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_risk_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setTitleText("待整改记录");
        viewModel.setRightIconVisible(View.VISIBLE);
        viewModel.setRightIcon(R.drawable.add);
    }

    @Override
    public RiskListViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(RiskListViewModel.class);
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            tag = bundle.getString("tag");
        }
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.selectUncomitList(binding.refreshLayout, tag);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.selectUncomitList(binding.refreshLayout, tag);
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.itemClickEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object bean) {
                String riskItemStr = "";
                Intent intent = new Intent(RiskListActivity.this, RiskRegisterActivity.class);
                if (null != bean) {
                    riskItemStr = JSON.toJSONString(bean);
                }
                intent.putExtra("riskItem", riskItemStr);
                intent.putExtra("tag", tag);
                startActivityForResult(intent, TO_REGISTER_RISK);
            }
        });

        viewModel.registerRisk.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(RiskListActivity.this, RiskRegisterActivity.class);
                intent.putExtra("tag", tag);
                startActivityForResult(intent, TO_REGISTER_RISK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TO_REGISTER_RISK) {
                viewModel.selectUncomitList(binding.refreshLayout, tag);
            }
        }
    }
}