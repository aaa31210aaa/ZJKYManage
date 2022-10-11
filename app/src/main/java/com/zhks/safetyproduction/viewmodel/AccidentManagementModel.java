package com.zhks.safetyproduction.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.ui.activity.AccidentDetailActivity;
import com.zhks.safetyproduction.ui.activity.InvestigationActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class AccidentManagementModel extends ToolbarViewModel{
    public AccidentManagementModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 事故快报
     */
    public BindingCommand accidentReportingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(AccidentDetailActivity.class);
        }
    });

    /**
     * 现场调查取证
     */
    public BindingCommand investigationCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(InvestigationActivity.class);
        }
    });
}
