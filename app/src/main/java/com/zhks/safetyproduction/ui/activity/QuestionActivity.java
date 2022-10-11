package com.zhks.safetyproduction.ui.activity;


import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.alibaba.fastjson.JSON;
import com.example.zhouwei.library.CustomPopWindow;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.MyViewPagerAdapter;
import com.zhks.safetyproduction.adapter.QuestionPopAdapter;
import com.zhks.safetyproduction.adapter.ViewPagerAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityQuestionBinding;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.QuestionBean;
import com.zhks.safetyproduction.entity.QuestionResultBean;
import com.zhks.safetyproduction.entity.QuestionSubmitBean;
import com.zhks.safetyproduction.manager.DaoManager;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.ui.fragment.JugChoiceFragment;
import com.zhks.safetyproduction.ui.fragment.MultiChoiceFragment;
import com.zhks.safetyproduction.ui.fragment.SingleChoiceFragment;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.QuestionViewPagerViewModel;
import com.zhks.safetyproduction.wight.DividerGridViewItemDecoration;
import com.zhks.safetyproduction.wight.FullyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class QuestionActivity extends BaseActivity<ActivityQuestionBinding, QuestionViewPagerViewModel> implements View.OnClickListener {
    private View questionPopView;
    private CustomPopWindow questionPop;
    public QuestionPopAdapter questionPopAdapter;
    private RecyclerView questionRv;
    private List<ListQuestionsBean.DataDTO> questionPopList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private PagerAdapter mPagerAdapter;//适配器
    private CountDownTimer countDownTimer;
    private CustomPopWindow timeOutPop; //超时弹窗
    private View timeOutView;
    private CustomPopWindow outTipsPop; //退出页面弹窗提示
    private View outTipsView;
    private CustomPopWindow resultPop; //考试结果弹窗
    private View resultView;
    private TextView timeOutSubmit;
    private TextView outTipsSubmit;
    private TextView outTipsCancel;
    private TextView resultText;
    private TextView resultScore;
    private TextView resultTipsSubmit;
    private long countDownTime = 3600000;
    private String testid;
    private long currentUseSecond;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_question;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        testid = getIntent().getStringExtra("testid");
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("问卷测试");
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("提交");
    }

    @Override
    public QuestionViewPagerViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(QuestionViewPagerViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.getQuestionList(testid);
        //60分钟倒计时
        countDownTimer = new CountDownTimer(countDownTime, 1000) {
            @Override
            public void onTick(long l) {
                binding.countDownTimerTv.setText(DateUtils.getTimeStr(l) + "");
                currentUseSecond = countDownTime - l;
            }

            @Override
            public void onFinish() {
                if (null != outTipsPop && outTipsPop.getPopupWindow().isShowing()) {
                    outTipsPop.dissmiss();
                }
                showTimeOutPop();
            }
        };
        questionPopView = View.inflate(this, R.layout.question_popview_layout, null);
        questionRv = questionPopView.findViewById(R.id.question_pop_rv);
        questionRv.addItemDecoration(new DividerGridViewItemDecoration(this));
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        questionRv.setLayoutManager(manager);
        questionPopAdapter = new QuestionPopAdapter(R.layout.question_popview_item_layout, questionPopList);
        questionRv.setAdapter(questionPopAdapter);

        questionPopAdapter.setQuestionItemClick(new QuestionPopAdapter.QuestionItemClick() {
            @Override
            public void setQuestionItemClick(int position) {
                binding.questionVp.setCurrentItem(position);
                questionPop.dissmiss();
            }
        });

        timeOutView = View.inflate(this, R.layout.time_out_pop, null);
        timeOutSubmit = timeOutView.findViewById(R.id.timeOutSubmit);
        timeOutSubmit.setOnClickListener(this);
        outTipsView = View.inflate(this, R.layout.out_tips_pop, null);
        outTipsSubmit = outTipsView.findViewById(R.id.outTipsSubmit);
        outTipsSubmit.setOnClickListener(this);
        outTipsCancel = outTipsView.findViewById(R.id.outTipsCancel);
        outTipsCancel.setOnClickListener(this);
        resultView = View.inflate(this, R.layout.result_tips_pop, null);
        resultText = resultView.findViewById(R.id.resultText);
        resultScore = resultView.findViewById(R.id.resultScore);
        resultTipsSubmit = resultView.findViewById(R.id.resultTipsSubmit);
        resultTipsSubmit.setOnClickListener(this);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.nextQuestionEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.questionVp.setCurrentItem(binding.questionVp.getCurrentItem() + 1, false);
            }
        });

        viewModel.lastQuestionEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.questionVp.setCurrentItem(binding.questionVp.getCurrentItem() - 1, false);
            }
        });

        viewModel.questionListEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showQuestionPop();
            }
        });

        viewModel.getQuestionListEvent.observe(this, new Observer<List<ListQuestionsBean.DataDTO>>() {
            @Override
            public void onChanged(List<ListQuestionsBean.DataDTO> dataDTOS) {
                for (int i = 0; i < dataDTOS.size(); i++) {
                    dataDTOS.get(i).setPosition(i);
                    dataDTOS.get(i).setQuestionNo(String.valueOf(i + 1));
                    questionPopList.add(dataDTOS.get(i));
                    questionPopAdapter.setNewData(questionPopList);

                    //添加viewpager布局
                    if (dataDTOS.get(i).getQuestionmodel().equals(Constants.TX001)) {
                        fragmentList.add(SingleChoiceFragment.newInstance(dataDTOS.get(i)));
                    } else if (dataDTOS.get(i).getQuestionmodel().equals(Constants.TX002)) {
                        fragmentList.add(MultiChoiceFragment.newInstance(dataDTOS.get(i)));
                    } else {
                        fragmentList.add(JugChoiceFragment.newInstance(dataDTOS.get(i)));
                    }
                }
                mPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
                binding.questionVp.setAdapter(mPagerAdapter);//设置适配器
                countDownTimer.start();
            }
        });


        viewModel.backOnClickEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showOutTipsPop();
            }
        });

        viewModel.rightClickEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (questionPopList.isEmpty()) {
                    return;
                }

                for (int i = 0; i < questionPopList.size(); i++) {
                    if (!questionPopList.get(i).isCheck()) {
                        ToastUtils.showShort("还有题目未作答");
                        return;
                    }
                }

                List<QuestionSubmitBean.QuestionsDTO> datas = new ArrayList<>();
                for (int i = 0; i < questionPopList.size(); i++) {
                    QuestionSubmitBean.QuestionsDTO bean = new QuestionSubmitBean.QuestionsDTO();
                    bean.setQuestionid(questionPopList.get(i).getQuestionid());
                    List<String> answers = new ArrayList<>();
                    for (int j = 0; j < questionPopList.get(i).getAnswerList().size(); j++) {
                        if (questionPopList.get(i).getAnswerList().get(j).isAnswerCheck()) {
                            answers.add(questionPopList.get(i).getAnswerList().get(j).getAnswerid());
                        }
                    }
                    bean.setAnswers(answers);
                    datas.add(bean);
                }

                QuestionSubmitBean bean = new QuestionSubmitBean();
                bean.setTestid(testid);
                bean.setUserid(PersonInfoManager.getInstance().getUserId());
                bean.setDeptid(PersonInfoManager.getInstance().getDeptid());

                bean.setDuration(String.valueOf(currentUseSecond / 1000));
                bean.setQuestions(datas);
                String modelJson = JSON.toJSONString(bean);
                viewModel.questionSubmit(modelJson);
            }
        });

        viewModel.questionSubmitEvent.observe(this, new Observer<QuestionResultBean>() {
            @Override
            public void onChanged(QuestionResultBean questionResultBean) {
                resultText.setText(questionResultBean.getMessage());
                resultScore.setText(questionResultBean.getData());
                showResultTipsPop();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && null != timeOutPop && timeOutPop.getPopupWindow().isShowing()) {
            return false;
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && null == outTipsPop) {
            showOutTipsPop();
            return true;
        } else {
            if (outTipsPop.getPopupWindow().isShowing()) {
                return false;
            } else {
                showOutTipsPop();
                return true;
            }
        }
    }

    private void showQuestionPop() {
        questionPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(questionPopView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(400))
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DaoManager.getInstance().closeConnection();
        EventBus.getDefault().unregister(this);
        countDownTimer.cancel();
    }

    public void refreshPop(ListQuestionsBean.DataDTO dataDTO) {
        questionPopList.set(dataDTO.getPosition(), dataDTO);
        questionPopAdapter.setNewData(questionPopList);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.timeOutSubmit) {
            timeOutPop.dissmiss();
            finish();
        } else if (id == R.id.outTipsSubmit) {
            outTipsPop.dissmiss();
            finish();
        } else if (id == R.id.outTipsCancel) {
            outTipsPop.dissmiss();
        } else if (id == R.id.resultTipsSubmit) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private void showTimeOutPop() {
        timeOutPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(timeOutView)
                .setOutsideTouchable(false)
                .setFocusable(false)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(MATCH_PARENT, MATCH_PARENT)
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    private void showOutTipsPop() {
        outTipsPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(outTipsView)
                .setOutsideTouchable(false)
                .setFocusable(false)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(MATCH_PARENT, MATCH_PARENT)
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void showResultTipsPop() {
        resultPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(resultView)
                .setOutsideTouchable(false)
                .setFocusable(false)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(MATCH_PARENT, MATCH_PARENT)
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}