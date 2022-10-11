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

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.RiskCheckRecordsBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.ui.activity.PatrolPostActivity;
import com.zhks.safetyproduction.ui.activity.RiskCheckDetailActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 风险检查详情页面
 */
public class RiskInspectionRecordsViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<RiskRecordsItemViewModel> itemBinding;
    public ObservableList<RiskRecordsItemViewModel> itemViewModels = new ObservableArrayList<>();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent rightEvent = new SingleLiveEvent();
    private Context mContext;

    public RiskInspectionRecordsViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
        mContext = context;
    }

    public void getRecordsData(SmartRefreshLayout refreshLayout, boolean isFresh) {
        loadingVisible.set(View.VISIBLE);
        if (isFresh) {
            itemViewModels.clear();
        }
        model.getRiskRecords(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskInspectionRecordsViewModel.this)
                .subscribe(new Consumer<RiskCheckRecordsBean>() {
                    @Override
                    public void accept(RiskCheckRecordsBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            if (baseResponse.getData().isEmpty()) {
                                itemBinding = ItemBinding.of(BR.viewModel, R.layout.empty_view);
                            } else {
                                itemBinding = ItemBinding.of(BR.viewModel, R.layout.risk_inspection_records_item_layout);
                                for (int i = 0; i < baseResponse.getData().size(); i++) {
                                    itemViewModels.add(new RiskRecordsItemViewModel(RiskInspectionRecordsViewModel.this, baseResponse.getData().get(i), mContext));
                                }
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        if (isFresh) {
                            refreshLayout.finishRefresh();
                        } else {
                            refreshLayout.finishLoadMore();
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("getRecordsData", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        rightEvent.call();
    }
}
