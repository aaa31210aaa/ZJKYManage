package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.QuestionBean;
import com.zhks.safetyproduction.entity.QuestionResultBean;
import com.zhks.safetyproduction.manager.DaoManager;
import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.utils.DaoUtilsStore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class QuestionViewPagerViewModel extends ToolbarViewModel<HomeModel> {
    //给ViewPager添加ObservableList
//    public ObservableList<MultiItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding

    public List<QuestionBean> questionBeanList = new ArrayList<>();
    public SingleLiveEvent nextQuestionEvent = new SingleLiveEvent();
    public SingleLiveEvent lastQuestionEvent = new SingleLiveEvent();
    public SingleLiveEvent questionListEvent = new SingleLiveEvent();
    public List<QuestionBean> allList = new ArrayList<>();
    public List<ListQuestionsBean.DataDTO> questionDatas = new ArrayList<>();
    public List<ListQuestionsBean.DataDTO.AnswerListDTO> answerDatas = new ArrayList<>();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent<List<ListQuestionsBean.DataDTO>> getQuestionListEvent = new SingleLiveEvent();
    public SingleLiveEvent<Integer> multipleChoiceRadioEvent = new SingleLiveEvent();
    public SingleLiveEvent<Integer> judgeChoiceEvent = new SingleLiveEvent();
    public SingleLiveEvent backOnClickEvent = new SingleLiveEvent();
    public SingleLiveEvent rightClickEvent = new SingleLiveEvent();
    public SingleLiveEvent<QuestionResultBean> questionSubmitEvent = new SingleLiveEvent();

    public QuestionViewPagerViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
//        initQuestionBank();
//        initViewpager();
    }

    @Override
    protected void backOnClick() {
        backOnClickEvent.call();
    }

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        rightClickEvent.call();
    }

    /**
     * 下一题
     */
    public BindingCommand nextQuestion = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            nextQuestionEvent.call();
        }
    });

    /**
     * 上一题
     */
    public BindingCommand lastQuestion = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            lastQuestionEvent.call();
        }
    });

    /**
     * 题目列表
     */
    public BindingCommand questionListCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            questionListEvent.call();
        }
    });

//    public final OnItemBind<Object> itemBinding = new OnItemBind<Object>() {
//        @Override
//        public void onItemBind(@NonNull ItemBinding itemBinding, int position, Object item) {
//            if (item.getClass().equals(SingleChoiceViewModel.class)) {
//                itemBinding.set(BR.viewModel, R.layout.single_choice_item);
//            } else if (item.getClass().equals(MultipleChoiceViewModel.class)) {
//                itemBinding.set(BR.viewModel, R.layout.multiple_choice_item);
//            } else if (item.getClass().equals(JudgeViewModel.class)) {
//                itemBinding.set(BR.viewModel, R.layout.judge_choice_item);
//            }
//        }
//    };

    public void getQuestionList(String testid) {
        loadingVisible.set(View.VISIBLE);
        model.listQuestions(testid)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(QuestionViewPagerViewModel.this)
                .subscribe(new Consumer<ListQuestionsBean>() {
                    @Override
                    public void accept(ListQuestionsBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            questionDatas.addAll(baseResponse.getData());
                            getQuestionListEvent.setValue(baseResponse.getData());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("getQuestionList", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }

    //    private void initQuestionBank() {
//        for (int i = 0; i < 20; i++) {
//            QuestionBean questionBean = new QuestionBean();
//            questionBean.setId(Long.valueOf(i + 1));
//            questionBean.setQuestionNo(i);
//            questionBean.setAnswer("A");
//            questionBean.setQue("这是问题的题目" + i);
//            if (i % 2 == 1) {
//                questionBean.setType("多选");
//            } else if (i % 3 == 1) {
//                questionBean.setType("单选");
//            } else if (i % 4 == 1) {
//                questionBean.setType("判断");
//            }
//            questionBean.setChoiceA("这是A选项" + i);
//            questionBean.setChoiceB("这是B选项" + i);
//            questionBean.setChoiceC("这是C选项" + i);
//            questionBean.setChoiceD("这是D选项" + i);
//            questionBean.setDetail("");
//            questionBean.setKind("");
//            questionBeanList.add(questionBean);
//        }
//        DaoUtilsStore.getInstance().getQuestionDaoUtils().insertMultiple(questionBeanList);
//    }
//        List joes = DaoUtilsStore.getInstance().getQuestionDaoUtils().queryByQueryBuilder(QuestionBeanDao.Properties.Id.eq);
    //        allList = DaoUtilsStore.getInstance().getQuestionDaoUtils().queryAll();
    private void initViewpager() {
        if (!questionDatas.isEmpty()) {
            //多少个viewpager页面
            for (int i = 0; i < questionDatas.size(); i++) {
//                MultiItemViewModel multiItemViewModel;
//                if (questionDatas.get(i).getQuestionmodel().equals(Constants.TX001)) {
//                    multiItemViewModel = new SingleChoiceViewModel(this, questionDatas.get(i));
//                } else if (questionDatas.get(i).getQuestionmodel().equals(Constants.TX002)) {
//                    multiItemViewModel = new MultipleChoiceViewModel(this, questionDatas.get(i));
//                } else {
//                    multiItemViewModel = new JudgeViewModel(this, questionDatas.get(i));
//                }
//                items.add(multiItemViewModel);

            }
        }
    }


//    //给ViewPager添加PageTitle
//    public final BindingViewPagerAdapter.PageTitles<SingleChoiceViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<SingleChoiceViewModel>() {
//        @Override
//        public CharSequence getPageTitle(int position, SingleChoiceViewModel item) {
//            return "条目" + position;
//        }
//    };
//
//    //ViewPager切换监听
//    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
//        @Override
//        public void call(Integer index) {
////            ToastUtils.showShort("ViewPager切换：" + index);
//        }
//    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        DaoManager.getInstance().closeConnection();
    }

    public void questionSubmit(String modelJson) {
        loadingVisible.set(View.VISIBLE);
        model.questionCommit(modelJson)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(QuestionViewPagerViewModel.this)
                .subscribe(new Consumer<QuestionResultBean>() {
                    @Override
                    public void accept(QuestionResultBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            ToastUtils.showShort(baseResponse.getMessage());
                            loadingVisible.set(View.GONE);
                            questionSubmitEvent.setValue(baseResponse);
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                            loadingVisible.set(View.GONE);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("questionSubmit", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }
}
