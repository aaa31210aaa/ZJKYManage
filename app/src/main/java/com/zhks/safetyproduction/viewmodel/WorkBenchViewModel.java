package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AppVersionUpdateBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.ui.activity.ChangePwdActivity;
import com.zhks.safetyproduction.ui.activity.LoginActivity;
import com.zhks.safetyproduction.ui.activity.SettingActivity;
import com.zhks.safetyproduction.ui.activity.MultistageActivity;
import com.zhks.safetyproduction.ui.activity.TodoListActivity;
import com.zhks.safetyproduction.utils.SystemUtils;

import org.w3c.dom.Text;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class WorkBenchViewModel extends BaseViewModel<HomeModel> {
    private Context context;
    private HomeModel model;
    public WorkBenchViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application);
        this.context = context;
        this.model = model;
    }

    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> deptName = new ObservableField<>();
    public ObservableInt headImagePlaceHolder = new ObservableInt(R.drawable.login_default_headimg);
    public ObservableField<String> headImageUrl = new ObservableField<>();
    public ObservableInt signOutVisible = new ObservableInt();
    public SingleLiveEvent toLoginEvent = new SingleLiveEvent();
    public SingleLiveEvent signOutEvent = new SingleLiveEvent();
    public SingleLiveEvent<AppVersionUpdateBean.DataDTO> appUpdateEvent = new SingleLiveEvent<>();


//    /**
//     * 设置
//     */
//    public BindingCommand mineSettingClick = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            startActivity(SettingActivity.class);
//        }
//    });

    /**
     * 待办事项
     */
    public BindingCommand todoClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(PersonInfoManager.getInstance().getUserId())) {
                startActivity(LoginActivity.class);
            } else {
                startActivity(TodoListActivity.class);
            }
        }
    });

    /**
     * 简介
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
            getUpdateVersion(context);
        }
    });

    /**
     * 登录
     */
    public BindingCommand toLoginCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(PersonInfoManager.getInstance().getUserId())) {
                toLoginEvent.call();
            }
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
     * 退出登录
     */
    public BindingCommand signOutCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            signOutEvent.call();
        }
    });

    /**
     * 检查版本更新
     */
    public void getUpdateVersion(Context context) {
        model.updateapp()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(WorkBenchViewModel.this)
                .subscribe(new Consumer<AppVersionUpdateBean>() {
                    @Override
                    public void accept(AppVersionUpdateBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            if (SystemUtils.compareVersion(baseResponse.getData().getVerCode(), String.valueOf(SystemUtils.getVersionCode(context))) == 1) {
                                appUpdateEvent.setValue(baseResponse.getData());
                            } else {
                                ToastUtils.showShort("当前版本是最新版本");
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("getUpdateVersion", throwable.toString());
                    }
                });
    }

}
