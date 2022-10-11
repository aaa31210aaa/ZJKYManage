package com.zhks.safetyproduction.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.CurrentUserBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class PersonnelMutilItemViewModel extends ItemViewModel<PersonnelMutilViewModel> {
    public ObservableField<String> multiItemName = new ObservableField<>();
    public ObservableInt multiItemCk = new ObservableInt(R.drawable.draft_item_unselect);
    private CurrentUserBean.DataDTO dataDTO;

    public PersonnelMutilItemViewModel(@NonNull PersonnelMutilViewModel viewModel, CurrentUserBean.DataDTO dataDTO) {
        super(viewModel);
        this.dataDTO = dataDTO;
        multiItemName.set(dataDTO.getUsername());
        if (dataDTO.isCheck()) {
            multiItemCk.set(R.drawable.draft_item_select);
        } else {
            multiItemCk.set(R.drawable.draft_item_unselect);
        }
    }

    public BindingCommand multiItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (dataDTO.isCheck()) {
                dataDTO.setCheck(false);
                multiItemCk.set(R.drawable.draft_item_unselect);
            } else {
                dataDTO.setCheck(true);
                multiItemCk.set(R.drawable.draft_item_select);
            }
            viewModel.setSelectDatas(dataDTO);
        }
    });
}
