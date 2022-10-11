package com.zhks.safetyproduction.ui.activity;

import android.os.Bundle;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.databinding.ActivityNoticeBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.NoticeListViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 通知公告
 */
public class NoticeListActivity extends BaseActivity<ActivityNoticeBinding, NoticeListViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_notice;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }


}