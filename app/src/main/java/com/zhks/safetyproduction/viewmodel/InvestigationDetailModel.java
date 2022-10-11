package com.zhks.safetyproduction.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

public class InvestigationDetailModel extends ToolbarViewModel{
    public ObservableField<String> accidentName = new ObservableField<>();
    public ObservableField<String> accidentType = new ObservableField<>();
    public ObservableField<String> accidentDate = new ObservableField<>();
    public ObservableField<String> accidentAddress = new ObservableField<>();
    public ObservableField<String> accidentSituation = new ObservableField<>();


    public InvestigationDetailModel(@NonNull Application application) {
        super(application);
    }
}
