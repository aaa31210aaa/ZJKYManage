package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AppVersionUpdateBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.entity.MenuBean;
import com.zhks.safetyproduction.model.HomeModel;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

import com.zhks.safetyproduction.entity.MenuBean.CellsDTO;
import com.zhks.safetyproduction.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ToolbarViewModel<HomeModel> {
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    private List<CellsDTO> cellsDatas = new ArrayList<>();
    private Context context;
    public SingleLiveEvent<AppVersionUpdateBean.DataDTO> appUpdateEvent = new SingleLiveEvent<>();

    public HomeViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
        initToolbar();
        initHomeData(PersonInfoManager.getInstance().getUserId());
        this.context = context;
    }


    public ItemBinding<HomeItemViewModel> itemBinding = ItemBinding.of(BR.homeItemViewModel, R.layout.home_item_layout);

    public ObservableList<HomeItemViewModel> itemViewModels = new ObservableArrayList<>();

    public void initHomeData(String userId) {
        if (!TextUtils.isEmpty(PersonInfoManager.getInstance().getUserId())) {
            loadingVisible.set(View.VISIBLE);
            //调用登录接口
            itemViewModels.clear();
            cellsDatas.clear();
            model.getHomeMenu(userId)
                    .compose(RxUtils.schedulersTransformer())
                    .doOnSubscribe(HomeViewModel.this)
                    .subscribe(new Consumer<MenuBean>() {
                        @Override
                        public void accept(MenuBean baseResponse) throws Exception {
                            loadingVisible.set(View.GONE);
                            if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                                cellsDatas.addAll(baseResponse.getCells());
                                for (int i = 0; i < cellsDatas.size(); i++) {
                                    itemViewModels.add(new HomeItemViewModel(HomeViewModel.this, cellsDatas.get(i), context));
                                }
                            } else {
                                ToastUtils.showShort(baseResponse.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            loadingVisible.set(View.GONE);
                            ToastUtils.showShort(throwable.getMessage());
                        }
                    });
        }
    }

    /**
     * 检查版本更新
     */
    public void getUpdateVersion(Context context) {
        model.updateapp()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(HomeViewModel.this)
                .subscribe(new Consumer<AppVersionUpdateBean>() {
                    @Override
                    public void accept(AppVersionUpdateBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            if (SystemUtils.compareVersion(baseResponse.getData().getVerCode(), String.valueOf(SystemUtils.getVersionCode(context))) == 1) {
                                appUpdateEvent.setValue(baseResponse.getData());
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

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        setRightIconVisible(View.GONE);
        setBackImage(null);
        setTitleText("首页");
    }

}
