package com.zhks.safetyproduction.config;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zhks.safetyproduction.model.HomeModel;
import com.zhks.safetyproduction.viewmodel.AcceptConfirmViewModel;
import com.zhks.safetyproduction.viewmodel.AcceptanceCheckViewModel;
import com.zhks.safetyproduction.viewmodel.AccidentDetailModel;
import com.zhks.safetyproduction.viewmodel.AqqrpbDetailViewModel;
import com.zhks.safetyproduction.viewmodel.AqqrpbListViewModel;
import com.zhks.safetyproduction.viewmodel.CancelCaseDetailViewModel;
import com.zhks.safetyproduction.viewmodel.CancelCaseListViewModel;
import com.zhks.safetyproduction.viewmodel.ChangePwdViewModel;
import com.zhks.safetyproduction.viewmodel.ConfirmRectificationViewModel;
import com.zhks.safetyproduction.viewmodel.DelayRectificationViewModel;
import com.zhks.safetyproduction.viewmodel.HiddenReportDetailViewModel;
import com.zhks.safetyproduction.viewmodel.HomeActivityViewModel;
import com.zhks.safetyproduction.viewmodel.HomeItemCommonViewModel;
import com.zhks.safetyproduction.viewmodel.HomeViewModel;
import com.zhks.safetyproduction.viewmodel.LicenceCheckDetailViewModel;
import com.zhks.safetyproduction.viewmodel.LicenceCheckViewModel;
import com.zhks.safetyproduction.viewmodel.MinutesOfTheMeetingModel;
import com.zhks.safetyproduction.viewmodel.MultistageViewModel;
import com.zhks.safetyproduction.viewmodel.NewsDetailViewModel;
import com.zhks.safetyproduction.viewmodel.NewsViewModel;
import com.zhks.safetyproduction.viewmodel.PatrolPostViewModel;
import com.zhks.safetyproduction.viewmodel.PersonnelMutilViewModel;
import com.zhks.safetyproduction.viewmodel.PersonnelQueryViewModel;
import com.zhks.safetyproduction.viewmodel.QuestionListViewModel;
import com.zhks.safetyproduction.viewmodel.QuestionViewPagerViewModel;
import com.zhks.safetyproduction.viewmodel.RiskCheckDetailViewModel;
import com.zhks.safetyproduction.viewmodel.RiskCheckOptionsViewModel;
import com.zhks.safetyproduction.viewmodel.RiskInspectionRecordsViewModel;
import com.zhks.safetyproduction.viewmodel.RiskListViewModel;
import com.zhks.safetyproduction.viewmodel.RiskRectificationViewModel;
import com.zhks.safetyproduction.viewmodel.RiskRegisterViewModel;
import com.zhks.safetyproduction.viewmodel.SafeCheckDetailViewModel;
import com.zhks.safetyproduction.viewmodel.SafeCheckListViewModel;
import com.zhks.safetyproduction.viewmodel.SafetyTrainModel;
import com.zhks.safetyproduction.viewmodel.TodoListViewModel;
import com.zhks.safetyproduction.viewmodel.WorkBenchViewModel;

public class HomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile HomeViewModelFactory INSTANCE;
    private final Application mApplication;
    private final HomeModel homeModel;
    private Context context;
    private String messageId;

    public static HomeViewModelFactory getInstance(Application application, Context context) {
        if (INSTANCE == null) {
            synchronized (HomeViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HomeViewModelFactory(application, HomeInjection.provideRepository(), context);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HomeViewModelFactory(Application application, HomeModel model, Context context) {
        this.mApplication = application;
        this.homeModel = model;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeActivityViewModel.class)) {
            return (T) new HomeActivityViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(NewsViewModel.class)) {
            return (T) new NewsViewModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel.class)) {
            return (T) new NewsDetailViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(HomeItemCommonViewModel.class)) {
            return (T) new HomeItemCommonViewModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(AccidentDetailModel.class)) {
            return (T) new AccidentDetailModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(SafetyTrainModel.class)) {
            return (T) new SafetyTrainModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(SafeCheckListViewModel.class)) {
            return (T) new SafeCheckListViewModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(SafeCheckDetailViewModel.class)) {
            return (T) new SafeCheckDetailViewModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(LicenceCheckViewModel.class)) {
            return (T) new LicenceCheckViewModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(MultistageViewModel.class)) {
            return (T) new MultistageViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(MinutesOfTheMeetingModel.class)) {
            return (T) new MinutesOfTheMeetingModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(LicenceCheckDetailViewModel.class)) {
            return (T) new LicenceCheckDetailViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(RiskCheckDetailViewModel.class)) {
            return (T) new RiskCheckDetailViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(HiddenReportDetailViewModel.class)) {
            return (T) new HiddenReportDetailViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(PersonnelQueryViewModel.class)) {
            return (T) new PersonnelQueryViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(RiskRectificationViewModel.class)) {
            return (T) new RiskRectificationViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(DelayRectificationViewModel.class)) {
            return (T) new DelayRectificationViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(RiskRegisterViewModel.class)) {
            return (T) new RiskRegisterViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(RiskListViewModel.class)) {
            return (T) new RiskListViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(ConfirmRectificationViewModel.class)) {
            return (T) new ConfirmRectificationViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(AcceptanceCheckViewModel.class)) {
            return (T) new AcceptanceCheckViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(RiskInspectionRecordsViewModel.class)) {
            return (T) new RiskInspectionRecordsViewModel(mApplication, homeModel, context);
        } else if (modelClass.isAssignableFrom(RiskCheckOptionsViewModel.class)) {
            return (T) new RiskCheckOptionsViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(PatrolPostViewModel.class)) {
            return (T) new PatrolPostViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(AcceptConfirmViewModel.class)) {
            return (T) new AcceptConfirmViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(CancelCaseListViewModel.class)) {
            return (T) new CancelCaseListViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(CancelCaseDetailViewModel.class)) {
            return (T) new CancelCaseDetailViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(AqqrpbListViewModel.class)) {
            return (T) new AqqrpbListViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(AqqrpbDetailViewModel.class)) {
            return (T) new AqqrpbDetailViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(QuestionViewPagerViewModel.class)) {
            return (T) new QuestionViewPagerViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(QuestionListViewModel.class)) {
            return (T) new QuestionListViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(ChangePwdViewModel.class)) {
            return (T) new ChangePwdViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(TodoListViewModel.class)) {
            return (T) new TodoListViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(PersonnelMutilViewModel.class)) {
            return (T) new PersonnelMutilViewModel(mApplication, homeModel);
        } else if (modelClass.isAssignableFrom(WorkBenchViewModel.class)) {
            return (T) new WorkBenchViewModel(mApplication, homeModel, context);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
