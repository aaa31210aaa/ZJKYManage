package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.SUCCESS_STR;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class PersonnelQueryViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<PersonnelQueryItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.personnel_item_tv);
    public ObservableList<PersonnelQueryItemViewModel> itemViewModels = new ObservableArrayList<>();
    private List<DeptUserBean.CellsDTO> userList = new ArrayList<>();
    public SingleLiveEvent<String> searchTextChangeEvent = new SingleLiveEvent<>();
    private Context context;
    private List<DeptUserBean.CellsDTO> searchDatas = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    private List<PersonnelQueryItemViewModel> itemModelDatas = new ArrayList<>();
    public SingleLiveEvent deptEvent = new SingleLiveEvent();
    public SingleLiveEvent<List<DepartmentBean.CellsDTO.DateDTO>> departDataEvent = new SingleLiveEvent();
    private String deptId;
    private String deptStr;

    public PersonnelQueryViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        getDept();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getPersonnelData(String deptId, String pageId, SmartRefreshLayout refreshLayout, boolean isLoad, String deptStr) {
        this.refreshLayout = refreshLayout;
        this.deptId = deptId;
        this.deptStr = deptStr;
        model.getListUser(deptId, pageId)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(PersonnelQueryViewModel.this)
                .subscribe(new Consumer<DeptUserBean>() {
                    @Override
                    public void accept(DeptUserBean baseResponse) throws Exception {
                        if (TextUtils.equals(SUCCESS_STR, baseResponse.getSuccess())) {
                            if (!isLoad) {
                                itemViewModels.clear();
                                itemModelDatas.clear();
                                userList.clear();
                            }
                            userList.addAll(baseResponse.getCells());
                            for (int i = 0; i < baseResponse.getCells().size(); i++) {
                                PersonnelQueryItemViewModel itemModel = new PersonnelQueryItemViewModel(PersonnelQueryViewModel.this, baseResponse.getCells().get(i), context, deptId);
                                itemViewModels.add(itemModel);
                                itemModelDatas.add(itemModel);
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("PersonnelQueryViewModel", throwable.toString());
                    }
                });
    }

    public BindingCommand<String> searchTextChange = new BindingCommand<String>(new BindingConsumer<String>() {
        @Override
        public void call(String s) {
            itemViewModels.clear();
            if (s.length() > 0) {
                refreshLayout.setEnableRefresh(false);
                refreshLayout.setEnableLoadMore(false);
                search(s.trim());
            } else {
                refreshLayout.setEnableRefresh(true);
                refreshLayout.setEnableLoadMore(true);
                itemViewModels.addAll(itemModelDatas);
            }
        }
    });

    private void search(String str) {
        if (!searchDatas.isEmpty()) {
            searchDatas.clear();
        }
        if (!userList.isEmpty()) {
            for (DeptUserBean.CellsDTO entity : userList) {
                try {
                    if (entity.getUsername().contains(str)) {
                        itemViewModels.add(new PersonnelQueryItemViewModel(PersonnelQueryViewModel.this, entity, context, deptId));
                        searchDatas.add(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 选择部门
     */
    public BindingCommand deptCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            deptEvent.call();
        }
    });


    public void getDept() {
        //请求部门单位
        model.getDepartment()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(PersonnelQueryViewModel.this)
                .subscribe(new Consumer<DepartmentBean>() {
                    @Override
                    public void accept(DepartmentBean baseResponse) throws Exception {
                        if (TextUtils.equals("0000", baseResponse.getCode())) {
                            departDataEvent.setValue(baseResponse.getCells().get(0).getDate());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("AccidentDetailModel", throwable.toString());
                    }
                });
    }
}
