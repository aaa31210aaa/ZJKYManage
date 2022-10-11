package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

public class NoticeDetailViewModel extends ToolbarViewModel {
    public NoticeDetailViewModel(@NonNull Application application) {
        super(application);
        initToolbar();

    }

    private void initToolbar() {
        setRightIconVisible(View.GONE);
        setTitleText("公告详情");
    }

    private void initData() {

    }
}
