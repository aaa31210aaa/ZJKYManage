package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivityAccidentManagementBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.AccidentManagementModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 事故管理
 */
public class AccidentManagementActivity extends BaseActivity<ActivityAccidentManagementBinding, AccidentManagementModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_accident_management;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("事故管理");
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }

    @Override
    public void initData() {
        super.initData();
    }


}