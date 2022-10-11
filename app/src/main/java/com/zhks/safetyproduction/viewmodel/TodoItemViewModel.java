package com.zhks.safetyproduction.viewmodel;


import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.NewsBean;
import com.zhks.safetyproduction.entity.TodoBean;
import com.zhks.safetyproduction.ui.activity.AcceptanceCheckActivity;
import com.zhks.safetyproduction.ui.activity.AqqrpbjlListActivity;
import com.zhks.safetyproduction.ui.activity.CancelCaseListActivity;
import com.zhks.safetyproduction.ui.activity.QuestionListActivity;
import com.zhks.safetyproduction.ui.activity.RiskCheckOptionsActivity;
import com.zhks.safetyproduction.ui.activity.RiskInspectionRecordsActivity;
import com.zhks.safetyproduction.ui.activity.RiskRectificationActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class TodoItemViewModel extends ItemViewModel<TodoListViewModel> {
    public ObservableField<String> dbsx = new ObservableField<>();
    public ObservableField<String> nr = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public NewsBean.CellsDTO bean;


    public TodoItemViewModel(@NonNull TodoListViewModel viewModel, NewsBean.CellsDTO todoBean) {
        super(viewModel);
        this.bean = todoBean;
        dbsx.set(bean.getMessagetitle());
        nr.set(bean.getMescontent());
        date.set(bean.getMestime());

    }

    /**
     * 待办列表子项点击
     * 考试，风险点检查，隐患整改，验收，销案，工作面安全确认
     */
    public BindingCommand todoItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (null != bean) {
                if (TextUtils.equals(Constants.APPFLAGNO3000, bean.getAppflagno())) {
                    viewModel.startActivity(QuestionListActivity.class);
                } else if (TextUtils.equals(Constants.APPFLAGNO4, bean.getAppflagno())) {
                    viewModel.startActivity(RiskCheckOptionsActivity.class);
                }  else if (TextUtils.equals(Constants.APPFLAGNO10, bean.getAppflagno())) {
                    viewModel.startActivity(RiskRectificationActivity.class);
                } else if (TextUtils.equals(Constants.APPFLAGNO11, bean.getAppflagno())) {
                    viewModel.startActivity(AcceptanceCheckActivity.class);
                } else if (TextUtils.equals(Constants.APPFLAGNO12, bean.getAppflagno())) {
                    viewModel.startActivity(CancelCaseListActivity.class);
                }  else if (TextUtils.equals(Constants.APPFLAGNO3001, bean.getAppflagno())) {
                    viewModel.startActivity(AqqrpbjlListActivity.class);
                }
            }
        }
    });
}
