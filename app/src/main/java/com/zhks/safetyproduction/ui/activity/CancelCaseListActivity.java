package com.zhks.safetyproduction.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityCancelListCaseBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.CancelCaseListViewModel;
import com.zhks.safetyproduction.viewmodel.HomeItemCommonViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 待销案列表
 */
public class CancelCaseListActivity extends BaseActivity<ActivityCancelListCaseBinding, CancelCaseListViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_cancel_list_case;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public CancelCaseListViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(CancelCaseListViewModel.class);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("待销案记录");
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.itemClickEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Bundle bundle = new Bundle();
                bundle.putString("trid", s);
                Intent intent = new Intent(CancelCaseListActivity.this, CancelCaseDetailActivity.class);
                intent.putExtra("trid", s);
                startActivityForResult(intent, Constants.TO_YHXA_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.TO_YHXA_CODE) {
            viewModel.selectDXAList();
        }
    }

    @Override
    public void initData() {
        super.initData();
    }
}