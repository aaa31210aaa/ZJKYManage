package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.QuestionBean;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;

public class JudgeViewModel extends MultiItemViewModel {
    public ObservableField<String> questionType = new ObservableField<>();
    public ObservableField<String> questionTitle = new ObservableField<>();
    public ObservableField<String> judgeAnswerA = new ObservableField<>();
    public ObservableField<String> judgeAnswerB = new ObservableField<>();

    public JudgeViewModel(@NonNull QuestionViewPagerViewModel viewModel, ListQuestionsBean.DataDTO questionBean) {
        super(viewModel);
        questionType.set("判断");
        questionTitle.set(questionBean.getQuestioncontent());
        judgeAnswerA.set(questionBean.getAnswerList().get(0).getAnswercontent());
        judgeAnswerB.set(questionBean.getAnswerList().get(1).getAnswercontent());
    }
}
