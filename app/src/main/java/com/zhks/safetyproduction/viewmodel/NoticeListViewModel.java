package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.NoticeBean;

import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class NoticeListViewModel extends ToolbarViewModel {
    public NoticeListViewModel(@NonNull Application application) {
        super(application);
        initData();
        initToolbar();
    }

    public ItemBinding<NoticeItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.notice_item_layout);

    public ObservableList<NoticeItemViewModel> itemViewModels = new ObservableArrayList<>();

    public void initData() {
        for (int i = 0; i < 10; i++) {
            NoticeBean bean = new NoticeBean();
//            bean.setTitle("测试标题测试标题测试标题测试标题" + i);
//            bean.setSubTitle("副标题副标题副标题" + i);
//            bean.setDate("2021-12-12 12:08:24");
//            if (i == 0 || i == 3 || i == 4) {
//                bean.setState("1");
//            } else {
//                bean.setState("0");
//            }
            itemViewModels.add(new NoticeItemViewModel(this, bean));
        }
    }

    public void initToolbar() {
        setRightIconVisible(View.GONE);
        setTitleText("通知公告");
    }

}
