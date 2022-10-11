package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.MenuBean;
import com.zhks.safetyproduction.entity.RectificationBean;
import com.zhks.safetyproduction.model.HomeModel;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeItemCommonViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<HomeItemCommonItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.common_item_layout);
    public ObservableList<HomeItemCommonItemViewModel> itemViewModels = new ObservableArrayList<>();
    private MenuBean.CellsDTO cells;
    private HomeModel model;
    private Context context;

    public HomeItemCommonViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
        this.context = context;
        initToolBar();
    }

    public void setCellsBean(MenuBean.CellsDTO cells) {
        this.cells = cells;
        getData();
    }

    private void initToolBar() {
        setRightIconVisible(View.GONE);
    }

    private void getData() {
        setTitleText(cells.getModule());
        for (int i = 0; i < cells.getObject().size(); i++) {
            itemViewModels.add(new HomeItemCommonItemViewModel(this, cells.getObject().get(i), context));
        }
    }

}
