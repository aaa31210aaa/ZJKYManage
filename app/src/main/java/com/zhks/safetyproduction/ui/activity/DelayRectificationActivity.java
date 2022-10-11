package com.zhks.safetyproduction.ui.activity;


import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSON;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityDelayRectificationBinding;
import com.zhks.safetyproduction.entity.DelayRectificationBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.AccidentDetailModel;
import com.zhks.safetyproduction.viewmodel.DelayRectificationViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.goldze.mvvmhabit.base.BaseActivity;

public class DelayRectificationActivity extends BaseActivity<ActivityDelayRectificationBinding, DelayRectificationViewModel> {
    private String currentDate;
    private DateEntity dateEntity;
    private String trid;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_delay_rectification;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("申请延期");
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            trid = bundle.getString("trid");
        }
    }

    @Override
    public void initData() {
        super.initData();
        dateEntity = DateEntity.today();
        binding.delayDateTv.setText(DateUtils.getCurrentDate());
    }

    @Override
    public DelayRectificationViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(DelayRectificationViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.delayDateCommandEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(DelayRectificationActivity.this, true, dateEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.delayDateTv.setText(year + "-" + month + "-" + day);
                        try {
                            Date date = DateUtils.formatterYmd.parse(year + "-" + month + "-" + day);
                            dateEntity = DateEntity.target(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        viewModel.delaySubmitEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DelayRectificationBean bean = new DelayRectificationBean();
                bean.setTrid(trid);
                bean.setDelayDate(binding.delayDateTv.getText().toString());
                bean.setDelayReson(binding.delayResonTv.getText().toString());
                String modelJson = JSON.toJSONString(bean);
                viewModel.insertPostpone(modelJson);
            }
        });
    }


}