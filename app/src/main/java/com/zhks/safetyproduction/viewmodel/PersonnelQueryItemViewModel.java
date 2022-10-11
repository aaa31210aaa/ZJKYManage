package com.zhks.safetyproduction.viewmodel;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.ui.activity.PersonnelQueryActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class PersonnelQueryItemViewModel extends ItemViewModel<PersonnelQueryViewModel> {
    public ObservableField<String> personnelItemTv = new ObservableField<>();
    private DeptUserBean.CellsDTO bean;
    private Context context;
    private String dtid;

    public PersonnelQueryItemViewModel(@NonNull PersonnelQueryViewModel viewModel, DeptUserBean.CellsDTO bean, Context context,
                                       String dtid) {
        super(viewModel);
        this.bean = bean;
        this.context = context;
        this.dtid = dtid;
        personnelItemTv.set(bean.getUsername());
    }

    public BindingCommand personnelItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Intent intent = new Intent();
            intent.putExtra("personnelTv", bean.getUsername());
//            intent.putExtra("personnelId", )
            intent.putExtra("deptTv", bean.getDeptname());
            intent.putExtra("userId", bean.getUserid());
            intent.putExtra("dtid", dtid);
            ((PersonnelQueryActivity) context).setResult(RESULT_OK, intent);
            ((PersonnelQueryActivity) context).finish();
        }
    });
}
