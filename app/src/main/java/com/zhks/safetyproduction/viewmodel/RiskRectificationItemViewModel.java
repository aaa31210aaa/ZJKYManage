package com.zhks.safetyproduction.viewmodel;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.zhouwei.library.CustomPopWindow;
import com.zhks.safetyproduction.entity.RectificationBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.ui.activity.ConfirmRectificationActivity;
import com.zhks.safetyproduction.ui.activity.DelayRectificationActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.Utils;

public class RiskRectificationItemViewModel extends ItemViewModel<RiskRectificationViewModel> {
    public ObservableField<String> rectificationCotent = new ObservableField<>();
    public ObservableField<String> zgqxDate = new ObservableField<>();
    public ObservableField<String> rectificationReason = new ObservableField<>();
    public ObservableInt delayButtonVisible = new ObservableInt();
    public ObservableField<String> trsitename = new ObservableField<>();
    private ToBeRectifiedBean.DataDTO rectificationBean;

    public RiskRectificationItemViewModel(@NonNull RiskRectificationViewModel viewModel, ToBeRectifiedBean.DataDTO bean) {
        super(viewModel);
        this.rectificationBean = bean;
        rectificationCotent.set(rectificationBean.getTrcategoryname());
        zgqxDate.set(rectificationBean.getZgterm());
        rectificationReason.set(rectificationBean.getTrdescribe());
        String deptId = PersonInfoManager.getInstance().getDeptid();
        if (TextUtils.equals("0",deptId) || TextUtils.equals("100000",deptId) || TextUtils.equals("208000",deptId)) {
            delayButtonVisible.set(View.VISIBLE);
        } else {
            delayButtonVisible.set(View.GONE);
        }
        trsitename.set(rectificationBean.getTrsitename());
    }

    /**
     * 延期整改
     */
    public BindingCommand delayRectificationCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("trid", rectificationBean.getTrid());
            viewModel.startActivity(DelayRectificationActivity.class, bundle);
        }
    });

    /**
     * 确认整改
     */
    public BindingCommand commitRectificationCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("trid", rectificationBean.getTrid());
            viewModel.startActivity(ConfirmRectificationActivity.class, bundle);
        }
    });


}
