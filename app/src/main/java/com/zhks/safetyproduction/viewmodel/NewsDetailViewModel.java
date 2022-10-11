package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.APPFLAGN00;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO1;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO2;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO3;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO4;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO5;
import static com.zhks.safetyproduction.constants.Constants.MESSAGE_TYPE1;
import static com.zhks.safetyproduction.constants.Constants.SUCCESS_STR;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.NewsBean;
import com.zhks.safetyproduction.entity.NewsDetailBean;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class NewsDetailViewModel extends ToolbarViewModel<HomeModel> {

    public ObservableField<String> newsDetailTitle = new ObservableField<>();
    public ObservableField<String> newsDetailContent = new ObservableField<>();
    public ObservableInt newsDetailVisible = new ObservableInt(View.GONE);
    public ObservableInt loadingVisible = new ObservableInt(View.VISIBLE);
    private String messageId;
    private NewsDetailBean newsDetailBean;

    public NewsDetailViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        initToolBar();
    }

    private void initToolBar() {
        setRightIconVisible(View.GONE);
        setTitleText("消息详情");
    }

    public void setMessageId(String messageId) {
        model.getNewsDetail(messageId)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(NewsDetailViewModel.this)
                .subscribe(new Consumer<NewsDetailBean>() {
                    @Override
                    public void accept(NewsDetailBean baseResponse) throws Exception {
                        loadingVisible.set(View.GONE);
                        if (TextUtils.equals(SUCCESS_STR, baseResponse.isSuccess())) {
                            if (!baseResponse.getCells().isEmpty()) {
                                newsDetailBean = baseResponse.getCells().get(0);
                                newsDetailTitle.set(newsDetailBean.getMessagetitle());
                                newsDetailContent.set(newsDetailBean.getMescontent());
                                if (TextUtils.equals(MESSAGE_TYPE1, newsDetailBean.getMestype())) {
                                    newsDetailVisible.set(View.GONE);
                                } else {
                                    newsDetailVisible.set(View.VISIBLE);
                                }
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingVisible.set(View.GONE);
                        Log.d("NewsDetailViewModel", throwable.toString());
                    }
                });

    }


    /**
     * 跳转处理界面
     */
    public BindingCommand newsDetailHandleClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //跳转对应的处理页面
            switch (newsDetailBean.getAppflagno()) {
                case APPFLAGN00:
                    break;
                case APPFLAGNO1:
                    break;
                case APPFLAGNO2:
                    break;
                case APPFLAGNO3:
                    break;
                case APPFLAGNO4:
                    break;
                case APPFLAGNO5:
                    break;
            }
        }
    });
}
