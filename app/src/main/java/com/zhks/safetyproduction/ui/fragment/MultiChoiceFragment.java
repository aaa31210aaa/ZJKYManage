package com.zhks.safetyproduction.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.ui.activity.QuestionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultiChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiChoiceFragment extends Fragment {
    private View view;
    private ListQuestionsBean.DataDTO dataDTO;
    private TextView choiceNo;
    private TextView questionType;
    private TextView multiTitle;
    private CheckBox multiCheckA;
    private CheckBox multiCheckB;
    private CheckBox multiCheckC;
    private CheckBox multiCheckD;
    private CheckBox multiCheckE;
    private boolean isCheckA;
    private boolean isCheckB;
    private boolean isCheckC;
    private boolean isCheckD;
    private boolean isCheckE;

    public MultiChoiceFragment() {
        // Required empty public constructor
    }

    public static MultiChoiceFragment newInstance(ListQuestionsBean.DataDTO dataDTO) {
        MultiChoiceFragment fragment = new MultiChoiceFragment();
        Bundle args = new Bundle();
        args.putSerializable("multi", dataDTO);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_multi_choice, container, false);
        dataDTO = (ListQuestionsBean.DataDTO) getArguments().getSerializable("multi");
        init();
        return view;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
////        super.onSaveInstanceState(outState);
//    }

    private void init() {
        choiceNo = view.findViewById(R.id.choiceNo);
        questionType = view.findViewById(R.id.question_type);
        multiTitle = view.findViewById(R.id.multiTitle);
        multiCheckA = view.findViewById(R.id.multiCheckA);
        multiCheckB = view.findViewById(R.id.multiCheckB);
        multiCheckC = view.findViewById(R.id.multiCheckC);
        multiCheckD = view.findViewById(R.id.multiCheckD);
        multiCheckE = view.findViewById(R.id.multiCheckE);

        if (null != dataDTO) {
            choiceNo.setText(dataDTO.getQuestionNo() + ".");
            questionType.setText("多选");
            multiTitle.setText(dataDTO.getQuestioncontent());
            if (dataDTO.getAnswerList().size() >= 4) {
                multiCheckA.setText(dataDTO.getAnswerList().get(0).getAnswercontent());
                multiCheckB.setText(dataDTO.getAnswerList().get(1).getAnswercontent());
                multiCheckC.setText(dataDTO.getAnswerList().get(2).getAnswercontent());
                multiCheckD.setText(dataDTO.getAnswerList().get(3).getAnswercontent());

                if (dataDTO.getAnswerList().size() == 5) {
                    multiCheckE.setVisibility(View.VISIBLE);
                    multiCheckE.setText(dataDTO.getAnswerList().get(4).getAnswercontent());
                } else {
                    multiCheckE.setVisibility(View.GONE);
                }
            }
        }

        multiCheckA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean a) {
                isCheckA = a;
                if (isCheckA || isCheckB || isCheckC || isCheckD || isCheckE) {
                    dataDTO.setCheck(true);
                } else {
                    dataDTO.setCheck(false);
                }
                dataDTO.getAnswerList().get(0).setAnswerCheck(isCheckA);
                ((QuestionActivity) getActivity()).refreshPop(dataDTO);
            }
        });

        multiCheckB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckB = b;
                if (isCheckA || isCheckB || isCheckC || isCheckD || isCheckE) {
                    dataDTO.setCheck(true);
                } else {
                    dataDTO.setCheck(false);
                }
                dataDTO.getAnswerList().get(1).setAnswerCheck(isCheckB);
                ((QuestionActivity) getActivity()).refreshPop(dataDTO);
            }
        });

        multiCheckC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean c) {
                isCheckC = c;
                if (isCheckA || isCheckB || isCheckC || isCheckD || isCheckE) {
                    dataDTO.setCheck(true);
                } else {
                    dataDTO.setCheck(false);
                }
                dataDTO.getAnswerList().get(2).setAnswerCheck(isCheckC);
                ((QuestionActivity) getActivity()).refreshPop(dataDTO);
            }
        });

        multiCheckD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean d) {
                isCheckD = d;
                if (isCheckA || isCheckB || isCheckC || isCheckD || isCheckE) {
                    dataDTO.setCheck(true);
                } else {
                    dataDTO.setCheck(false);
                }
                dataDTO.getAnswerList().get(3).setAnswerCheck(isCheckD);
                ((QuestionActivity) getActivity()).refreshPop(dataDTO);
            }
        });

        multiCheckE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean e) {
                isCheckE = e;
                if (isCheckA || isCheckB || isCheckC || isCheckD || isCheckE) {
                    dataDTO.setCheck(true);
                } else {
                    dataDTO.setCheck(false);
                }
                dataDTO.getAnswerList().get(4).setAnswerCheck(isCheckD);
                ((QuestionActivity) getActivity()).refreshPop(dataDTO);
            }
        });
    }
}