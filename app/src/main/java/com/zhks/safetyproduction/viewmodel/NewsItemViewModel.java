package com.zhks.safetyproduction.viewmodel;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.entity.NewsBean;
import com.zhks.safetyproduction.ui.activity.NewsDetailActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class NewsItemViewModel extends ItemViewModel<NewsViewModel> {
    private NewsBean.CellsDTO newsDto;
    public ObservableField<String> newsItemTitle = new ObservableField<>();
    public ObservableField<String> newsItemDate = new ObservableField<>();
    public ObservableInt newsIsReadVisible = new ObservableInt();

    public NewsItemViewModel(@NonNull NewsViewModel viewModel, NewsBean.CellsDTO bean) {
        super(viewModel);
        this.newsDto = bean;
        initData();
    }

    private void initData() {
        newsItemTitle.set(newsDto.getMessagetitle());
        newsItemDate.set(newsDto.getMestime());
        if (TextUtils.equals("0", newsDto.getIsreaded())) {
            newsIsReadVisible.set(View.VISIBLE);
        } else {
            newsIsReadVisible.set(View.GONE);
        }
    }

    public BindingCommand newsItemCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            viewModel.startActivity(QuestionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("messageid", newsDto.getMessageid());
            viewModel.startActivity(NewsDetailActivity.class, bundle);
        }
    });

}
