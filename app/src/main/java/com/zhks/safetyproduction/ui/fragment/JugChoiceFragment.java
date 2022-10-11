package com.zhks.safetyproduction.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.ui.activity.QuestionActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JugChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JugChoiceFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private View view;
    private ListQuestionsBean.DataDTO dataDTO;
    private TextView choiceNo;
    private TextView questionType;
    private TextView jugTitle;
    private RadioGroup judgeChoiceRg;
    private RadioButton jugAnswerA;
    private RadioButton jugAnswerB;

    public JugChoiceFragment() {
        // Required empty public constructor
    }

    public static JugChoiceFragment newInstance(ListQuestionsBean.DataDTO dataDTO) {
        JugChoiceFragment fragment = new JugChoiceFragment();
        Bundle args = new Bundle();
        args.putSerializable("jug", dataDTO);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jug_choice, container, false);
        init();
        return view;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
////        super.onSaveInstanceState(outState);
//    }

    private void init() {
        dataDTO = (ListQuestionsBean.DataDTO) getArguments().getSerializable("jug");
        choiceNo = view.findViewById(R.id.choiceNo);
        questionType = view.findViewById(R.id.question_type);
        jugTitle = view.findViewById(R.id.jugTitle);
        judgeChoiceRg = view.findViewById(R.id.judgeChoiceRg);
        judgeChoiceRg.setOnCheckedChangeListener(this);
        jugAnswerA = view.findViewById(R.id.jugAnswerA);
        jugAnswerB = view.findViewById(R.id.jugAnswerB);
        if (null != dataDTO) {
            choiceNo.setText(dataDTO.getQuestionNo() + ".");
            questionType.setText("判断");
            jugTitle.setText(dataDTO.getQuestioncontent());
            jugAnswerA.setText(dataDTO.getAnswerList().get(0).getAnswercontent());
            jugAnswerB.setText(dataDTO.getAnswerList().get(1).getAnswercontent());
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.jugAnswerA) {
            dataDTO.getAnswerList().get(0).setAnswerCheck(true);
            dataDTO.getAnswerList().get(1).setAnswerCheck(false);
        } else if (i == R.id.jugAnswerB) {
            dataDTO.getAnswerList().get(0).setAnswerCheck(false);
            dataDTO.getAnswerList().get(1).setAnswerCheck(true);
        }
        dataDTO.setCheck(true);
        ((QuestionActivity) getActivity()).refreshPop(dataDTO);
    }


}