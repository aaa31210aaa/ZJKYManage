package com.zhks.safetyproduction.config;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zhks.safetyproduction.model.MineModel;
import com.zhks.safetyproduction.viewmodel.LoginViewModel;


public class MineViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile MineViewModelFactory INSTANCE;
    private final Application mApplication;
    private final MineModel mineModel;

    public static MineViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (MineViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MineViewModelFactory(application, MineInjection.provideRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private MineViewModelFactory(Application application, MineModel model) {
        this.mApplication = application;
        this.mineModel = model;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mineModel);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
