package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.QuestionListBean;
import com.zhks.safetyproduction.utils.DateUtils;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class QuestionListItemViewModel extends ItemViewModel<QuestionListViewModel> {
    public ObservableField<String> ksjh = new ObservableField<>();
    public ObservableField<String> tkmc = new ObservableField<>();
    public ObservableField<String> kssj = new ObservableField<>();
    public ObservableField<String> jssj = new ObservableField<>();
    private QuestionListBean.DataDTO bean;


    public QuestionListItemViewModel(@NonNull QuestionListViewModel viewModel, QuestionListBean.DataDTO bean) {
        super(viewModel);
        setText(bean);
        this.bean = bean;
    }

    private void setText(QuestionListBean.DataDTO bean) {
        ksjh.set(bean.getPlanname());
        tkmc.set(bean.getBankname());
        kssj.set(DateUtils.timeStamp2Date(bean.getStartdate()));
        jssj.set(DateUtils.timeStamp2Date(bean.getEnddate()));
    }

    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.setItemClick(bean);
        }
    });
}
