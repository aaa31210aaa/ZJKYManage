package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.JobTaskByDeptBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class PatrolPostViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent<List<DepartmentBean.CellsDTO.DateDTO>> deptEvent = new SingleLiveEvent();
    public ItemBinding<PatrolPostItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.patrol_post_item_layout);
    public ObservableList<PatrolPostItemViewModel> itemViewModels = new ObservableArrayList<>();
    public SingleLiveEvent deptCommandEvent = new SingleLiveEvent();
    public SingleLiveEvent jobCommandEvent = new SingleLiveEvent();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);

    public PatrolPostViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void getDept() {
        model.getDepartment()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(PatrolPostViewModel.this)
                .subscribe(new Consumer<DepartmentBean>() {
                    @Override
                    public void accept(DepartmentBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            deptEvent.setValue(baseResponse.getCells().get(0).getDate());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("PatrolPostViewModel", throwable.toString());
                    }
                });
    }

    public BindingCommand deptCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            deptCommandEvent.call();
        }
    });

    public BindingCommand jobTypeCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            jobCommandEvent.call();
        }
    });

    public void getJobtaskByDept(String evatype, String deptid) {
        loadingVisible.set(View.VISIBLE);
        itemViewModels.clear();
        model.getJobtaskByDept(evatype, deptid)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(PatrolPostViewModel.this)
                .subscribe(new Consumer<JobTaskByDeptBean>() {
                    @Override
                    public void accept(JobTaskByDeptBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            for (int i = 0; i < baseResponse.getData().size(); i++) {
                                itemViewModels.add(new PatrolPostItemViewModel(PatrolPostViewModel.this, baseResponse.getData().get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("PatrolPostViewModel", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }
}
