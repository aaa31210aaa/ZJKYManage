package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.EnclosureBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class AqqrpbDetailViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent<List<AqqrpbBean.RowsDTO>> mDatas = new SingleLiveEvent<>();
    public SingleLiveEvent submitEvent = new SingleLiveEvent();
    public SingleLiveEvent<List<EnclosureBean.DataDTO>> enclosureEvent = new SingleLiveEvent();
    public SingleLiveEvent workingFaceUpdateEvent = new SingleLiveEvent();

    public AqqrpbDetailViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        submitEvent.call();
    }

    /**
     * 获取安全确认内容
     *
     * @param csid
     */
    public void workingFaceDetail(String csid) {
        model.workingFaceDetail(csid)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AqqrpbDetailViewModel.this)
                .subscribe(new Consumer<AqqrpbBean>() {
                    @Override
                    public void accept(AqqrpbBean baseResponse) throws Exception {
                        if (!baseResponse.getRows().isEmpty()) {
                            for (int i = 0; i < baseResponse.getRows().size(); i++) {
                                baseResponse.getRows().get(i).setClickble(true);
                            }
                            mDatas.setValue(baseResponse.getRows());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("workingFaceDetail", throwable.toString());
                    }
                });
    }

    /**
     * 获取安全确认内容附件
     */
    public void getEnclosure(String businessid) {
        model.selectAttachmentApp(businessid)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AqqrpbDetailViewModel.this)
                .subscribe(new Consumer<EnclosureBean>() {
                    @Override
                    public void accept(EnclosureBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            enclosureEvent.setValue(baseResponse.getData());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("AqqrpbDetailViewModel", throwable.toString());
                    }
                });

    }


    /**
     * 安全确认修改
     * @param csid
     * @param detailids
     * @param files
     */
    public void workingFaceUpdate(String csid, String detailids, List<File> files) {
        model.workingFaceUpdate(csid,detailids, files)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AqqrpbDetailViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            ToastUtils.showShort(baseResponse.getMessage());
                            workingFaceUpdateEvent.call();
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("workingFaceUpdate", throwable.toString());
                    }
                });
    }
}
