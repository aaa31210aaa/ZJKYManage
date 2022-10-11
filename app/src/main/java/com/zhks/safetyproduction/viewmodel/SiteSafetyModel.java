package com.zhks.safetyproduction.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.ui.activity.MinutesOfTheMeetingActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class SiteSafetyModel extends ToolbarViewModel{
    public SiteSafetyModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 班前会议记录
     */
    public BindingCommand meetingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(MinutesOfTheMeetingActivity.class);
        }
    });

    /**
     * 危险作业许可证
     */
    public BindingCommand licenceCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });

    /**
     * 井下工作面安全确认
     */
    public BindingCommand confirmationCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
}
