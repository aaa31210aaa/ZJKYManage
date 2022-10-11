package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.CommonBean;
import com.zhks.safetyproduction.entity.SafetyTrainingBean;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class SafetyTrainItemModel extends ItemViewModel<SafetyTrainModel> {
    public ObservableField<String> safetyName = new ObservableField<>();
    public ObservableField<String> safetyStartDate = new ObservableField<>();
    public ObservableField<String> safetyEndDate = new ObservableField<>();
    public ObservableField<String> safetyTrainPlace = new ObservableField<>();
    public ObservableField<String> safetyTraineffect = new ObservableField<>();
    public ObservableField<String> safetyTrainhour = new ObservableField<>();
    public ObservableField<String> safetyTrainscore = new ObservableField<>();

    public SafetyTrainItemModel(@NonNull SafetyTrainModel viewModel, SafetyTrainingBean.CellsDTO bean) {
        super(viewModel);
        safetyName.set(bean.getPlanname());
        safetyStartDate.set(bean.getStartdate());
        safetyEndDate.set(bean.getEnddate());
        safetyTrainPlace.set(bean.getTrainSite());
        safetyTraineffect.set(bean.getTraineffect());
        safetyTrainhour.set(bean.getTrainhour());
        safetyTrainscore.set(bean.getTrainscore());
    }
}
