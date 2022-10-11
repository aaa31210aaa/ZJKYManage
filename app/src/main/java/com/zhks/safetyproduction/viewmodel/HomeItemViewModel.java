package com.zhks.safetyproduction.viewmodel;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.ui.activity.HomeItemCommonActivity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.Utils;

import com.zhks.safetyproduction.entity.MenuBean.CellsDTO;
import com.zhks.safetyproduction.utils.GlideUtil;


public class HomeItemViewModel extends ItemViewModel<HomeViewModel> {

    public ObservableField<String> itemText = new ObservableField<>();
    public ObservableField<String> homeItemImage = new ObservableField();
    public ObservableField<Integer> homeItemDefaultImage = new ObservableField<>(R.drawable.default_image);
    public ObservableField<String> angleMark = new ObservableField<>();
    public ObservableInt angleMarkVisilble = new ObservableInt(View.GONE);
    private CellsDTO mCells;
    private Context context;

    public HomeItemViewModel(@NonNull HomeViewModel viewModel, CellsDTO cellsDatas, Context context) {
        super(viewModel);
        this.mCells = cellsDatas;
        itemText.set(cellsDatas.getModule());
        homeItemImage.set(cellsDatas.getAppicaddr());
        this.context = context;
    }

    public BindingCommand homeItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Intent intent = new Intent(Utils.getContext(), HomeItemCommonActivity.class);
            intent.putExtra("cells", mCells);
            context.startActivity(intent);
//            if (TextUtils.equals(itemText.get(), viewModel.homeTitles[0])) {
//                viewModel.startActivity(NoticeListActivity.class);
//            } else if (TextUtils.equals(itemText.get(), viewModel.homeTitles[1])) {
//                viewModel.startActivity(SafeCheckActivity.class);
//            } else if (TextUtils.equals(itemText.get(), viewModel.homeTitles[2])) {
//                viewModel.startActivity(StaffParticipateInActivity.class);
//            } else if (TextUtils.equals(itemText.get(), viewModel.homeTitles[3])) {
//                viewModel.startActivity(RiskManagementActivity.class);
//            } else if (TextUtils.equals(itemText.get(), viewModel.homeTitles[4])) {
//                viewModel.startActivity(AccidentManagementActivity.class);
//            } else if (TextUtils.equals(itemText.get(), viewModel.homeTitles[5])) {
//                viewModel.startActivity(SafetyTrainingActivity.class);
//            } else if (TextUtils.equals(itemText.get(), viewModel.homeTitles[6])) {
//                viewModel.startActivity(SiteSafetyActivity.class);
//            }

        }
    });
}
