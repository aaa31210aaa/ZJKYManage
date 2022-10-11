package com.zhks.safetyproduction.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.ui.activity.QuestionActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleChoiceFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private View view;
    private ListQuestionsBean.DataDTO dataDTO;
    private TextView choiceNo;
    private TextView questionType;
    private TextView singleTitle;
    private RadioGroup singleChoiceRg;
    private RadioButton singleAnswerA;
    private RadioButton singleAnswerB;
    private RadioButton singleAnswerC;
    private RadioButton singleAnswerD;

    public SingleChoiceFragment() {
        // Required empty public constructor
    }

    public static SingleChoiceFragment newInstance(ListQuestionsBean.DataDTO dataDTO) {
        SingleChoiceFragment fragment = new SingleChoiceFragment();
        Bundle args = new Bundle();
        args.putSerializable("single", dataDTO);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
////        super.onSaveInstanceState(outState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataDTO = (ListQuestionsBean.DataDTO) getArguments().getSerializable("single");
        view = inflater.inflate(R.layout.fragment_single_choice, container, false);
        init();
        return view;
    }

    private void init() {
        choiceNo = view.findViewById(R.id.choiceNo);
        questionType = view.findViewById(R.id.question_type);
        singleTitle = view.findViewById(R.id.singleTitle);
        singleChoiceRg = view.findViewById(R.id.single_choice_rg);
        singleAnswerA = view.findViewById(R.id.single_answer_A);
        singleAnswerB = view.findViewById(R.id.single_answer_B);
        singleAnswerC = view.findViewById(R.id.single_answer_C);
        singleAnswerD = view.findViewById(R.id.single_answer_D);

        if (null != dataDTO) {
            choiceNo.setText(dataDTO.getQuestionNo() + ".");
            questionType.setText("单选");
            singleTitle.setText(dataDTO.getQuestioncontent());
            if (dataDTO.getAnswerList().size() == 2) {
                singleAnswerA.setText(dataDTO.getAnswerList().get(0).getAnswercontent());
                singleAnswerB.setText(dataDTO.getAnswerList().get(1).getAnswercontent());
                singleAnswerC.setVisibility(View.GONE);
                singleAnswerD.setVisibility(View.GONE);
            } else if (dataDTO.getAnswerList().size() == 3) {
                singleAnswerA.setText(dataDTO.getAnswerList().get(0).getAnswercontent());
                singleAnswerB.setText(dataDTO.getAnswerList().get(1).getAnswercontent());
                singleAnswerC.setText(dataDTO.getAnswerList().get(2).getAnswercontent());
                singleAnswerD.setVisibility(View.GONE);
            } else if (dataDTO.getAnswerList().size() == 4) {
                singleAnswerA.setText(dataDTO.getAnswerList().get(0).getAnswercontent());
                singleAnswerB.setText(dataDTO.getAnswerList().get(1).getAnswercontent());
                singleAnswerC.setText(dataDTO.getAnswerList().get(2).getAnswercontent());
                singleAnswerD.setText(dataDTO.getAnswerList().get(3).getAnswercontent());
            }
        }
        singleChoiceRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.single_answer_A) {
            dataDTO.getAnswerList().get(0).setAnswerCheck(true);
            dataDTO.getAnswerList().get(1).setAnswerCheck(false);
            dataDTO.getAnswerList().get(2).setAnswerCheck(false);
            dataDTO.getAnswerList().get(3).setAnswerCheck(false);
        } else if (i == R.id.single_answer_B) {
            dataDTO.getAnswerList().get(0).setAnswerCheck(false);
            dataDTO.getAnswerList().get(1).setAnswerCheck(true);
            dataDTO.getAnswerList().get(2).setAnswerCheck(false);
            dataDTO.getAnswerList().get(3).setAnswerCheck(false);
        } else if (i == R.id.single_answer_C) {
            dataDTO.getAnswerList().get(0).setAnswerCheck(false);
            dataDTO.getAnswerList().get(1).setAnswerCheck(false);
            dataDTO.getAnswerList().get(2).setAnswerCheck(true);
            dataDTO.getAnswerList().get(3).setAnswerCheck(false);
        } else if (i == R.id.single_answer_D) {
            dataDTO.getAnswerList().get(0).setAnswerCheck(false);
            dataDTO.getAnswerList().get(1).setAnswerCheck(false);
            dataDTO.getAnswerList().get(2).setAnswerCheck(false);
            dataDTO.getAnswerList().get(3).setAnswerCheck(true);
        }
        dataDTO.setCheck(true);
        ((QuestionActivity) getActivity()).refreshPop(dataDTO);
    }
}