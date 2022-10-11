package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.APPFLAGN00;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO1;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO10;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO11;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO12;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO2;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO3;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO3000;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO3001;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO4;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO5;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO6;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO7;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO8;
import static com.zhks.safetyproduction.constants.Constants.APPFLAGNO9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

import com.zhks.safetyproduction.entity.MenuBean.CellsDTO.ObjectDTO;
import com.zhks.safetyproduction.ui.activity.AcceptanceCheckActivity;
import com.zhks.safetyproduction.ui.activity.AccidentDetailActivity;
import com.zhks.safetyproduction.ui.activity.AqqrpbjlListActivity;
import com.zhks.safetyproduction.ui.activity.CancelCaseListActivity;
import com.zhks.safetyproduction.ui.activity.HiddenReportDetailActivity;
import com.zhks.safetyproduction.ui.activity.LicenceCheckListActivity;
import com.zhks.safetyproduction.ui.activity.MinutesOfTheMeetingActivity;
import com.zhks.safetyproduction.ui.activity.QuestionActivity;
import com.zhks.safetyproduction.ui.activity.QuestionListActivity;
import com.zhks.safetyproduction.ui.activity.RiskCheckOptionsActivity;
import com.zhks.safetyproduction.ui.activity.RiskListActivity;
import com.zhks.safetyproduction.ui.activity.RiskRectificationActivity;
import com.zhks.safetyproduction.ui.activity.SafeCheckListActivity;
import com.zhks.safetyproduction.ui.activity.SafetyTrainingActivity;

public class HomeItemCommonItemViewModel extends ItemViewModel<HomeItemCommonViewModel> {
    public ObservableField<String> commonItemTitle = new ObservableField<>();
    public ObservableInt commonItemImage = new ObservableInt();
    private ObjectDTO object;
    private Context context;
    private Bundle bundle;

    public HomeItemCommonItemViewModel(@NonNull HomeItemCommonViewModel viewModel, ObjectDTO object, Context context) {
        super(viewModel);
        this.object = object;
        this.context = context;
        commonItemTitle.set(object.getAppname());

//        GlideUtil.showImage(commonItemImage.get(), object.getAppicaddr(), context);

    }

    /**
     * 子菜单跳转
     */
    public BindingCommand commonItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (object.getAppflagno()) {
                case APPFLAGN00:
                    //安全检查表
                    bundle = new Bundle();
                    bundle.putString("titleName", object.getAppname());
                    viewModel.startActivity(SafeCheckListActivity.class, bundle);
                    break;
                case APPFLAGNO1:
                    //一般隐患登记 跳转隐患列表界面
                    bundle = new Bundle();
                    bundle.putString("tag", "0");//0为一般隐患， 1为重大隐患
                    viewModel.startActivity(RiskListActivity.class, bundle);
                    break;
                case APPFLAGNO2:
                    //重大隐患登记
                    bundle = new Bundle();
                    bundle.putString("tag", "1");//0为一般隐患， 1为重大隐患
                    viewModel.startActivity(RiskListActivity.class, bundle);
//                    viewModel.startActivity(QuestionActivity.class);
                    break;
                case APPFLAGNO3:
                    //隐患人人拍
                    viewModel.startActivity(HiddenReportDetailActivity.class);
                    break;
                case APPFLAGNO4:
                    //风险点检查
                    viewModel.startActivity(RiskCheckOptionsActivity.class);
                    break;
                case APPFLAGNO5:
                    //事故快报
                    bundle = new Bundle();
                    bundle.putString("titleName", object.getAppname());
                    viewModel.startActivity(AccidentDetailActivity.class, bundle);
                    break;
                case APPFLAGNO6:
                    //培训记录
                    viewModel.startActivity(SafetyTrainingActivity.class);
                    break;
                case APPFLAGNO7:
                    //班前会议记录
                    viewModel.startActivity(MinutesOfTheMeetingActivity.class);
                    break;
                case APPFLAGNO8:
                    //作业许可证
                    viewModel.startActivity(LicenceCheckListActivity.class);
                    break;
                case APPFLAGNO9:

                    break;
                case APPFLAGNO10:
                    //隐患整改
                    viewModel.startActivity(RiskRectificationActivity.class);
                    break;
                case APPFLAGNO11:
                    //隐患验收
                    viewModel.startActivity(AcceptanceCheckActivity.class);
                    break;
                case APPFLAGNO12:
//                    //隐患销案
                    viewModel.startActivity(CancelCaseListActivity.class);
                    break;
                case APPFLAGNO3000:
                    //考试列表
                    viewModel.startActivity(QuestionListActivity.class);
                    break;
                case APPFLAGNO3001:
                    //安全确认
                    viewModel.startActivity(AqqrpbjlListActivity.class);
                    break;
            }
        }
    });
}
