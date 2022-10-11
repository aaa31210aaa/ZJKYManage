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
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.entity.SafetyTrainingBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class SafetyTrainModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<SafetyTrainItemModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.safety_train_item_layout);
    public ObservableList<SafetyTrainItemModel> itemViewModels = new ObservableArrayList<>();
    public ObservableInt loadingVisible = new ObservableInt(View.VISIBLE);
    private List<SafetyTrainingBean.CellsDTO> cells;
    private Context context;

    public SafetyTrainModel(@NonNull Application application, HomeModel model, Context context) {
        super(application,model);
        this.context = context;
        getData();
    }

    private void getData() {
        model.getTrainRecordList(PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(SafetyTrainModel.this)
                .subscribe(new Consumer<SafetyTrainingBean>() {
                    @Override
                    public void accept(SafetyTrainingBean baseResponse) throws Exception {
                        loadingVisible.set(View.GONE);
                        if (TextUtils.equals(Constants.SUCCESS_STR, baseResponse.getSuccess())) {
                            cells = baseResponse.getCells();
                            for (int i = 0; i < cells.size(); i++) {
                                itemViewModels.add(new SafetyTrainItemModel(SafetyTrainModel.this, cells.get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingVisible.set(View.GONE);
                        Log.d("SafetyTrainModel", throwable.toString());
                    }
                });

    }
}
