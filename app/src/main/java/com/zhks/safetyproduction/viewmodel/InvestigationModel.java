package com.zhks.safetyproduction.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.CommonBean;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class InvestigationModel extends ToolbarViewModel {
    public InvestigationModel(@NonNull Application application) {
        super(application);
        initData();
    }

    public ItemBinding<InvestigationItemModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.investigation_item_layout);

    public ObservableList<InvestigationItemModel> itemViewModels = new ObservableArrayList<>();

    private void initData() {
        for (int i = 0; i < 5 ; i++) {
            CommonBean bean = new CommonBean();
            bean.setName("测试测试测试标题"+i);
            bean.setType("下人民币了"+i);
            bean.setDate("2022-1-1 20:20:20  一号井");
            bean.setAddress("一号井");
            itemViewModels.add(new InvestigationItemModel(this,bean));
        }
    }
}
