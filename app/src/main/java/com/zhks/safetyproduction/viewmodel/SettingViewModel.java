package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.ui.activity.ChangePwdActivity;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class SettingViewModel extends ToolbarViewModel {
    public ObservableInt outLoginVisible = new ObservableInt(View.GONE);

    public SettingViewModel(@NonNull Application application) {
        super(application);
        initToolBar();
    }

    public void initToolBar(){
        setRightIconVisible(View.GONE);
        setTitleText("设置");
    }

    /**
     * 版本信息
     */
    public BindingCommand versionInfoClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });

    /**
     * 检查更新
     */
    public BindingCommand checkUpdateClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });

    /**
     * 修改密码
     */
    public BindingCommand changePwdClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ChangePwdActivity.class);
        }
    });

    /**
     * 退出登录按钮
     */
    public BindingCommand outLoginBtnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            
        }
    });


}
