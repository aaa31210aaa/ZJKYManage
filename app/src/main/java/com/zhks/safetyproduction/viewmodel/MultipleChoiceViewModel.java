package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.QuestionBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;

public class MultipleChoiceViewModel extends MultiItemViewModel {
    public ObservableField<String> questionType = new ObservableField<>();
    public ObservableField<String> multipleQue = new ObservableField<>();
    public ObservableField<String> multipleAnswerA = new ObservableField<>();
    public ObservableField<String> multipleAnswerB = new ObservableField<>();
    public ObservableField<String> multipleAnswerC = new ObservableField<>();
    public ObservableField<String> multipleAnswerD = new ObservableField<>();

    public MultipleChoiceViewModel(@NonNull QuestionViewPagerViewModel viewModel, ListQuestionsBean.DataDTO questionBean) {
        super(viewModel);
        questionType.set("多选");
        multipleQue.set(questionBean.getQuestioncontent());
        multipleAnswerA.set(questionBean.getAnswerList().get(0).getAnswercontent());
        multipleAnswerB.set(questionBean.getAnswerList().get(1).getAnswercontent());
        multipleAnswerC.set(questionBean.getAnswerList().get(2).getAnswercontent());
        multipleAnswerD.set(questionBean.getAnswerList().get(3).getAnswercontent());
    }
}
