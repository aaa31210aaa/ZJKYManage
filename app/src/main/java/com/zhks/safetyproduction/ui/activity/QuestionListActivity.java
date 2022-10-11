package com.zhks.safetyproduction.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityQuestionListBinding;
import com.zhks.safetyproduction.entity.QuestionListBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.MultistageViewModel;
import com.zhks.safetyproduction.viewmodel.QuestionListViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class QuestionListActivity extends BaseActivity<ActivityQuestionListBinding, QuestionListViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_question_list;
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
        viewModel.setTitleText("题库考试");
    }

    @Override
    public QuestionListViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(QuestionListViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.testList(binding.refreshLayout);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.testList(binding.refreshLayout);
            }
        });

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.itemClickEvent.observe(this, new Observer<QuestionListBean.DataDTO>() {
            @Override
            public void onChanged(QuestionListBean.DataDTO dataDTO) {
                Intent intent = new Intent(QuestionListActivity.this, QuestionActivity.class);
                if (null != dataDTO) {
                    intent.putExtra("testid", dataDTO.getId());
                }
                startActivityForResult(intent, Constants.QUESTION_TM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.QUESTION_TM) {
            viewModel.testList(binding.refreshLayout);
        }
    }
}