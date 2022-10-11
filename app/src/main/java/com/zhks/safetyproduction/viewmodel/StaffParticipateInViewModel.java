package com.zhks.safetyproduction.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.ui.activity.HiddenReportDetailActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class StaffParticipateInViewModel extends ToolbarViewModel{
    public StaffParticipateInViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 隐患举报
     */
    public BindingCommand HiddenDangerReport = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(HiddenReportDetailActivity.class);
        }
    });


}
