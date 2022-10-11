package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.SUCCESS_STR;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.model.HomeModel;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HiddenReportDetailViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent receiverEvent = new SingleLiveEvent();
    public SingleLiveEvent reportTypeEvent = new SingleLiveEvent();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.YHLXDTO>> yhflEvent = new SingleLiveEvent<>();
    public SingleLiveEvent choosePlaceEvent = new SingleLiveEvent();
    public SingleLiveEvent submitEvent = new SingleLiveEvent();

    public HiddenReportDetailViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        getAccidentDictionaries();
    }

    public void getAccidentDictionaries() {
        model.getAccidentDictionaries("YHLX")
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(HiddenReportDetailViewModel.this)
                .subscribe(new Consumer<AccidentDetailBean>() {
                    @Override
                    public void accept(AccidentDetailBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            yhflEvent.setValue(baseResponse.getData().getyHLX());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("hiddenReport", throwable.toString());
                    }
                });
    }

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        submitEvent.call();
    }

    public void addTroublereport(String modelJson, List<File> files) {
        model.addTroublereport(modelJson, files)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(HiddenReportDetailViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        ToastUtils.showShort(baseResponse.getMessage());
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("HiddenReport", throwable.toString());
                    }
                });
    }

    /**
     * 受理人
     */
    public BindingCommand receiverCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            receiverEvent.call();
        }
    });

    /**
     * 举报类型
     */
    public BindingCommand reportTypeCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            reportTypeEvent.call();
        }
    });

    /**
     * 地点选择
     */
    public BindingCommand choosePlace = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            choosePlaceEvent.call();
        }
    });
}
