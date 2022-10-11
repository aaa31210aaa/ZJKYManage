package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivityNoticeDetailBinding;
import com.zhks.safetyproduction.viewmodel.NoticeDetailViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class NoticeDetailActivity extends BaseActivity<ActivityNoticeDetailBinding, NoticeDetailViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_notice_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}