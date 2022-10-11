package com.zhks.safetyproduction.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivityStaffParticipateInBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.StaffParticipateInViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 员工参与
 */
public class StaffParticipateInActivity extends BaseActivity<ActivityStaffParticipateInBinding,StaffParticipateInViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_staff_participate_in;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("员工参与");
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }

}