package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.ui.activity.RiskInspectionRecordsActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class RiskCheckOptionsViewModel extends ToolbarViewModel<HomeModel> {
    public ObservableInt leaderPatrolVisible = new ObservableInt(View.GONE);

    public RiskCheckOptionsViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        leaderPatrolVisible.set(PersonInfoManager.getInstance().isAdmin().equals("true") ? View.VISIBLE : View.GONE);
    }

    /**
     * 岗位自查
     */
    public BindingCommand postSelfCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("titleName", "岗位自查记录");
            startActivity(RiskInspectionRecordsActivity.class, bundle);
        }
    });

    /**
     * 领导巡查
     */
    public BindingCommand leaderPatrolCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("titleName", "领导巡查记录");
            startActivity(RiskInspectionRecordsActivity.class, bundle);
        }
    });
}
