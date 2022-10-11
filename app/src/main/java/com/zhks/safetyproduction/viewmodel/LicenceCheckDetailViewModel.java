package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.ApprovalhisBean;
import com.zhks.safetyproduction.entity.LicenceCheckBean;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class LicenceCheckDetailViewModel extends ToolbarViewModel<HomeModel> {
    public ObservableField<String> workContent = new ObservableField<>();
    public ObservableField<String> analysis = new ObservableField<>();
    public ObservableField<String> measures = new ObservableField<>();
    public ItemBinding<LicenceCheckDetailItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.licence_check_detail_rv_item);
    public ObservableList<LicenceCheckDetailItemViewModel> itemViewModels = new ObservableArrayList<>();
    private LicenceCheckBean.DataDTO licence;
    private List<ApprovalhisBean.RowsDTO> approvalhisRows = new ArrayList<>();

    public LicenceCheckDetailViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);

    }

    public void setLicence(LicenceCheckBean.DataDTO mlicence) {
        this.licence = mlicence;
        workContent.set(licence.getWorkcontent());
        analysis.set(licence.getAnalysis());
        measures.set(licence.getMeasures());

        model.getApprovalhis(licence.getWalevel(), licence.getDangerworkid())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(LicenceCheckDetailViewModel.this)
                .subscribe(new Consumer<ApprovalhisBean>() {
                    @Override
                    public void accept(ApprovalhisBean baseResponse) throws Exception {
                        approvalhisRows.addAll(baseResponse.getRows());
                        for (int i = 0; i < approvalhisRows.size(); i++) {
                            itemViewModels.add(new LicenceCheckDetailItemViewModel(LicenceCheckDetailViewModel.this, approvalhisRows.get(i)));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("LoginViewModel", throwable.toString());
                    }
                });
    }
}
