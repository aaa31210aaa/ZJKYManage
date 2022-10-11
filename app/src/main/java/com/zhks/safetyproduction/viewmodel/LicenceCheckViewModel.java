package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.LicenceCheckBean;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class LicenceCheckViewModel extends ToolbarViewModel<HomeModel> {
    private Context context;
    private List<LicenceCheckBean.DataDTO> licenceDatas = new ArrayList<>();

    public LicenceCheckViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
        this.context = context;
        getLicenceCheckList();
    }

    public ItemBinding<LicenceCheckItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.licence_check_item_layout);

    public ObservableList<LicenceCheckItemViewModel> itemViewModels = new ObservableArrayList<>();

    private void getLicenceCheckList() {
        model.getLicenceCheck()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(LicenceCheckViewModel.this)
                .subscribe(new Consumer<LicenceCheckBean>() {
                    @Override
                    public void accept(LicenceCheckBean baseResponse) throws Exception {
                        if (TextUtils.equals("success", baseResponse.getCode())) {
                            licenceDatas = baseResponse.getData();
                            for (int i = 0; i < licenceDatas.size(); i++) {
                                itemViewModels.add(new LicenceCheckItemViewModel(LicenceCheckViewModel.this, licenceDatas.get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("LicenceCheckViewModel", throwable.toString());
                    }
                });
    }
}
