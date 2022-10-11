package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.SUCCESS_STR;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.NewsBean;
import com.zhks.safetyproduction.entity.NoticeBean;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

import com.zhks.safetyproduction.entity.NewsBean.CellsDTO;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<NewsItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.news_item_layout);
    public ObservableList<NewsItemViewModel> itemViewModels = new ObservableArrayList<>();
    private HomeModel homeModel;
    private Context context;

    public NewsViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
        this.homeModel = model;
        this.context = context;
        initToolBar();
    }

    private void initToolBar() {
        setRightIconVisible(View.GONE);
        setBackImage(null);
        setTitleText("消息");
    }

    public void initData(SmartRefreshLayout refreshLayout) {
        itemViewModels.clear();
        model.getNewsList()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(NewsViewModel.this)
                .subscribe(new Consumer<NewsBean>() {
                    @Override
                    public void accept(NewsBean baseResponse) throws Exception {
                        if (TextUtils.equals(SUCCESS_STR, baseResponse.isSuccess())) {
                            for (int i = 0; i < baseResponse.getCells().size(); i++) {
                                itemViewModels.add(new NewsItemViewModel(NewsViewModel.this, baseResponse.getCells().get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refreshLayout.finishRefresh();
                        Log.d("NewsViewModel", throwable.toString());
                    }
                });
    }
}
