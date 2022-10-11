package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.example.zhouwei.library.CustomPopWindow;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.CurrentUserBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
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

public class PersonnelMutilViewModel extends ToolbarViewModel<HomeModel> {
    public ItemBinding<PersonnelMutilItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.person_mutil_item);
    public ObservableList<PersonnelMutilItemViewModel> itemViewModels = new ObservableArrayList<>();
    private List<PersonnelMutilItemViewModel> itemModelDatas = new ArrayList<>();
    public List<CurrentUserBean.DataDTO> selectDatas = new ArrayList<>();
    public SingleLiveEvent<String> multiSubmitEvent = new SingleLiveEvent();
    private SmartRefreshLayout refreshLayout;
    private List<CurrentUserBean.DataDTO> searchDatas = new ArrayList<>();
    private List<CurrentUserBean.DataDTO> userList = new ArrayList<>();

    public PersonnelMutilViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public void getPersonData(String pageId, SmartRefreshLayout refreshLayout, boolean isLoad) {
        this.refreshLayout = refreshLayout;
        model.getCurrentListUser(pageId, PersonInfoManager.getInstance().getUserId())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(PersonnelMutilViewModel.this)
                .subscribe(new Consumer<CurrentUserBean>() {
                    @Override
                    public void accept(CurrentUserBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            if (!isLoad) {
                                itemViewModels.clear();
                                itemModelDatas.clear();
                                userList.clear();
                                selectDatas.clear();
                            }
                            userList.addAll(baseResponse.getData());
                            for (int i = 0; i < baseResponse.getData().size(); i++) {
                                PersonnelMutilItemViewModel itemModel = new PersonnelMutilItemViewModel(PersonnelMutilViewModel.this, baseResponse.getData().get(i));
                                itemViewModels.add(itemModel);
                                itemModelDatas.add(itemModel);
                            }
                        } else {
                            ToastUtils.showShort("接口请求错误,请重试");
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("PersonnelMutilViewModel", throwable.toString());
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                });
    }

    /**
     * 确定人员选择
     */
    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        multiSubmitEvent.setValue(setChoiceText(selectDatas));
    }

    public void setSelectDatas(CurrentUserBean.DataDTO data) {
        selectDatas.add(data);
    }

    public String setChoiceText(List<CurrentUserBean.DataDTO> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                builder.append(list.get(i).getUsername() + ",");
            } else {
                builder.append(list.get(i).getUsername());
            }
        }
        return builder.toString();
    }

    public BindingCommand<String> searchTextChange = new BindingCommand<String>(new BindingConsumer<String>() {
        @Override
        public void call(String s) {
            itemViewModels.clear();
            if (s.length() > 0) {
                if (null != refreshLayout) {
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableLoadMore(false);
                }
                search(s.trim());
            } else {
                if (null != refreshLayout) {
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableLoadMore(true);
                }
                itemViewModels.addAll(itemModelDatas);
            }
        }
    });

    private void search(String str) {
        if (!searchDatas.isEmpty()) {
            searchDatas.clear();
        }
        if (!userList.isEmpty()) {
            for (CurrentUserBean.DataDTO entity : userList) {
                try {
                    if (entity.getUsername().contains(str)) {
                        itemViewModels.add(new PersonnelMutilItemViewModel(PersonnelMutilViewModel.this, entity));
                        searchDatas.add(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
