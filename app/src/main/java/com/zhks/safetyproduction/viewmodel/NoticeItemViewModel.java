package com.zhks.safetyproduction.viewmodel;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.entity.NoticeBean;
import com.zhks.safetyproduction.ui.activity.NoticeDetailActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class NoticeItemViewModel extends ItemViewModel<NoticeListViewModel> {
    public ObservableField<String> noticeTitle = new ObservableField<>();
    public ObservableField<String> noticeSubTitle = new ObservableField<>();
    public ObservableField<String> noticeDate = new ObservableField<>();
    public ObservableInt unredVisible = new ObservableInt();
    private NoticeBean mNoticeBean;

    public NoticeItemViewModel(@NonNull NoticeListViewModel viewModel, NoticeBean bean) {
        super(viewModel);
        this.mNoticeBean = bean;
//        noticeTitle.set(mNoticeBean.getTitle());
//        noticeSubTitle.set(mNoticeBean.getSubTitle());
//        noticeDate.set(mNoticeBean.getDate());
//        if (TextUtils.equals(mNoticeBean.getState(),"1")) {
//            unredVisible.set(View.VISIBLE);
//        } else {
//            unredVisible.set(View.GONE);
//        }
    }

    public BindingCommand noticeItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.startActivity(NoticeDetailActivity.class);
        }
    });


}
