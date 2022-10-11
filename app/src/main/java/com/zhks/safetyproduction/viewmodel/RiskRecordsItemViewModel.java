package com.zhks.safetyproduction.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.RiskCheckRecordsBean;
import com.zhks.safetyproduction.ui.activity.RiskCheckDetailActivity;
import com.zhks.safetyproduction.utils.DateUtils;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.Utils;

public class RiskRecordsItemViewModel extends ItemViewModel<RiskInspectionRecordsViewModel> {
    public ObservableField<String> checkman = new ObservableField<>();
    public ObservableField<String> evaname = new ObservableField<>();
    public ObservableField<String> state = new ObservableField<>();
    public ObservableField<String> locationname = new ObservableField<>();
    public ObservableField<String> checkdate = new ObservableField<>();
    public ObservableField<String> deptname = new ObservableField<>();
    public RiskCheckRecordsBean.DataDTO dataDTO;
    private Context mContext;


    public RiskRecordsItemViewModel(@NonNull RiskInspectionRecordsViewModel viewModel, RiskCheckRecordsBean.DataDTO data, Context context) {
        super(viewModel);
        mContext = context;
        dataDTO = data;
        checkman.set(data.getUsername());
        evaname.set("(" + data.getEvaname() + ")");
        if ("0".equals(data.getState())) {
            state.set("未提交");
        } else {
            state.set("已提交");
        }

        locationname.set(data.getLocationname());
        checkdate.set(DateUtils.timeStamp2Date(data.getCheckdate()));
        deptname.set(data.getDeptname());
    }

    public BindingCommand itemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("index", "1");
            bundle.putSerializable("itemBean", dataDTO);
            Intent intent = new Intent(mContext, RiskCheckDetailActivity.class);
            ((Activity) mContext).startActivityForResult(intent, Constants.GWJL_LIST_CODE, bundle);
        }
    });

}
