package com.zhks.safetyproduction.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.LicenceCheckBean;
import com.zhks.safetyproduction.ui.activity.LicenceCheckDetailActivity;

import java.util.List;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class LicenceCheckItemViewModel extends ItemViewModel<LicenceCheckViewModel> {
    public ObservableField<String> startAndEndTime = new ObservableField<>();
    public ObservableField<String> workplace = new ObservableField<>();
    public ObservableField<String> waname = new ObservableField<>();
    private LicenceCheckBean.DataDTO licence;

    public LicenceCheckItemViewModel(@NonNull LicenceCheckViewModel viewModel, LicenceCheckBean.DataDTO licence) {
        super(viewModel);
        this.licence = licence;
        startAndEndTime.set(licence.getStartdate() + "    " + licence.getEnddate());
        workplace.set(licence.getWorkplace());
        waname.set(licence.getWaname());
    }

    public BindingCommand licenceCheckItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putSerializable("licence", licence);
            viewModel.startActivity(LicenceCheckDetailActivity.class, bundle);
        }
    });
}
