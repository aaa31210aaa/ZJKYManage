package com.zhks.safetyproduction.ui.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityCancelCaseDetailBinding;
import com.zhks.safetyproduction.entity.CancelCaseBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.CancelCaseDetailViewModel;

import java.text.ParseException;
import java.util.Date;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 销案详情
 */
public class CancelCaseDetailActivity extends BaseActivity<ActivityCancelCaseDetailBinding, CancelCaseDetailViewModel> {
    private DateEntity cancelCaseEntity;
    private String trid;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_cancel_case_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
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
    public CancelCaseDetailViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(CancelCaseDetailViewModel.class);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("隐患销案");
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("提交");
    }

    @Override
    public void initData() {
        super.initData();
        cancelCaseEntity = DateEntity.today();
        binding.xiaoanDate.setText(DateUtils.getCurrentDate());
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.xiaoanEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(CancelCaseDetailActivity.this, false, cancelCaseEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.xiaoanDate.setText(year + "-" + month + "-" + day);
                        Date date = null;
                        try {
                            date = DateUtils.formatterYmd.parse(year + "-" + month + "-" + day);
                            cancelCaseEntity = DateEntity.target(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        viewModel.xiaoanSubmitEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                CancelCaseBean.DataDTO bean = new CancelCaseBean.DataDTO();
                bean.setUserid(PersonInfoManager.getInstance().getUserId());
                bean.setTrid(trid);
                bean.setClosedate(binding.xiaoanDate.getText().toString());
                bean.setCloseview(binding.xiaoanSupport.getText().toString());
                String modelJson = JSON.toJSONString(bean);
                viewModel.insertCaseclose(modelJson);
            }
        });

        viewModel.insertCasecloseEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}