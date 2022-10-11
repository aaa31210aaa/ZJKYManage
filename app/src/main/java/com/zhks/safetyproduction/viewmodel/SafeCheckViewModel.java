package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.ui.activity.AcceptanceCheckActivity;
import com.zhks.safetyproduction.ui.activity.RiskRectificationActivity;
import com.zhks.safetyproduction.ui.activity.RiskRegisterActivity;
import com.zhks.safetyproduction.ui.activity.SafeCheckListActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class SafeCheckViewModel extends ToolbarViewModel {
    public SafeCheckViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 安全检查
     */
    public BindingCommand safeCheckClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("titleName","安全检查表");
            startActivity(SafeCheckListActivity.class,bundle);
        }
    });

    /**
     * 隐患登记
     */
    public BindingCommand riskRegisterClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RiskRegisterActivity.class);
        }
    });


    /**
     * 风险整改
     */
    public BindingCommand riskRectificationClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(RiskRectificationActivity.class);
        }
    });

    /**
     * 复查验收
     */
    public BindingCommand reviewClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(AcceptanceCheckActivity.class);
        }
    });
}
