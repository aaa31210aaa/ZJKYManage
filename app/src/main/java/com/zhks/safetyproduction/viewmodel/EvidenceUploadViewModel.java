package com.zhks.safetyproduction.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class EvidenceUploadViewModel extends ToolbarViewModel {
    public ObservableField<String> acceptanceSituation = new ObservableField<>();

    public EvidenceUploadViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 提交
     */
    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();

    }

    /**
     * 验收人员
     */
    public BindingCommand acceptancePersonnelCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
}
