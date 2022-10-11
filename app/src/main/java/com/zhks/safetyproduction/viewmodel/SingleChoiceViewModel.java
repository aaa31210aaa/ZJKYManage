package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.QuestionBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;

public class SingleChoiceViewModel extends MultiItemViewModel<QuestionViewPagerViewModel> {
    public ObservableField<String> questionType = new ObservableField<>("单选");
    public ObservableField<String> questionTitle = new ObservableField<>();
    public ObservableField<String> singleAnswerA = new ObservableField<>();
    public ObservableField<String> singleAnswerB = new ObservableField<>();
    public ObservableField<String> singleAnswerC = new ObservableField<>();
    public ObservableField<String> singleAnswerD = new ObservableField<>();

    public SingleChoiceViewModel(@NonNull QuestionViewPagerViewModel viewModel, ListQuestionsBean.DataDTO questionBean) {
        super(viewModel);
        setData(questionBean);
    }

    private void setData(ListQuestionsBean.DataDTO questionBean) {
        questionType.set("单选");
        questionTitle.set(questionBean.getQuestioncontent());
        singleAnswerA.set(questionBean.getAnswerList().get(0).getAnswercontent());
        singleAnswerB.set(questionBean.getAnswerList().get(1).getAnswercontent());
        singleAnswerC.set(questionBean.getAnswerList().get(2).getAnswercontent());
        singleAnswerD.set(questionBean.getAnswerList().get(3).getAnswercontent());
    }

    public BindingCommand<Integer> singleGroup = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer id) {
        }
    });

}
