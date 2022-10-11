package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.ui.activity.SafeCheckListActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class RiskManagerMentModel extends ToolbarViewModel{
    public RiskManagerMentModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 危险源点检
     */
    public BindingCommand HazardSourceCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("titleName","危险源点检");
            startActivity(SafeCheckListActivity.class,bundle);
        }
    });

    /**
     * 关键任务观察
     */
    public BindingCommand KeyTaskObservationCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("titleName","关键任务观察");
            startActivity(SafeCheckListActivity.class,bundle);
        }
    });
}
