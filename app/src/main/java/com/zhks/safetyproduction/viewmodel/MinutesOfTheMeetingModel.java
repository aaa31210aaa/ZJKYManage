package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.SUCCESS_CODE;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.entity.CurrentUserBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.utils.DateUtils;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MinutesOfTheMeetingModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent rightTextEvent = new SingleLiveEvent();
    public ObservableField<String> startDateTv = new ObservableField<>(DateUtils.getCurrentDateyyMMddHHmm());
    public ObservableField<String> endTextTv = new ObservableField<>(DateUtils.getCurrentDateyyMMddHHmm());
    public SingleLiveEvent startDateEvent = new SingleLiveEvent();
    public SingleLiveEvent endDateEvent = new SingleLiveEvent();
    public SingleLiveEvent shiftEvent = new SingleLiveEvent();
    public SingleLiveEvent hostEvent = new SingleLiveEvent();
    public SingleLiveEvent participantsEvent = new SingleLiveEvent();
    public SingleLiveEvent leadersEvent = new SingleLiveEvent();
    public SingleLiveEvent<List<CurrentUserBean.DataDTO>> hostUserEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<CurrentUserBean.DataDTO>> ParticipantsUserEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<CurrentUserBean.DataDTO>> LeadersUserEvent = new SingleLiveEvent<>();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);


    public MinutesOfTheMeetingModel(@NonNull Application application, HomeModel model) {
        super(application, model);

    }


    /**
     * 班次
     */
    public BindingCommand shiftCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            shiftEvent.call();
        }
    });

    /**
     * 主持人
     */
    public BindingCommand hostCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            hostEvent.call();
        }
    });

    /**
     * 与会人员
     */
    public BindingCommand participantsCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            participantsEvent.call();
        }
    });

    /**
     * 参与领导
     */
    public BindingCommand leadersCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            leadersEvent.call();
        }
    });

    /**
     * 开始时间
     */
    public BindingCommand startDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startDateEvent.call();
        }
    });

    /**
     * 结束时间
     */
    public BindingCommand endDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            endDateEvent.call();
        }
    });


    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        rightTextEvent.call();
    }

    public void uploadMeetingRecord(String modelJson, List<File> files) {
        loadingVisible.set(View.VISIBLE);
        model.uploadMeetingRecord(modelJson, files)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(MinutesOfTheMeetingModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(SUCCESS_CODE, baseResponse.getCode())) {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                            finish();
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingVisible.set(View.GONE);
                        Log.d("LoginViewModel", throwable.toString());
                    }
                });
    }
}
