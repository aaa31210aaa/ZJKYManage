package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.SUCCESS_STR;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.NewsBean;
import com.zhks.safetyproduction.entity.TodoBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TodoListViewModel extends ToolbarViewModel<HomeModel> {
    private List<NewsBean.CellsDTO> newsDatas = new ArrayList<>();
    public TodoListViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
    }

    public ItemBinding<TodoItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.todo_item);
    public ObservableList<TodoItemViewModel> itemViewModels = new ObservableArrayList<>();


    public void initData(SmartRefreshLayout refreshLayout) {
        itemViewModels.clear();
        model.getNewsList()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(TodoListViewModel.this)
                .subscribe(new Consumer<NewsBean>() {
                    @Override
                    public void accept(NewsBean baseResponse) throws Exception {
                        if (TextUtils.equals(SUCCESS_STR, baseResponse.isSuccess())) {
                            newsDatas.addAll(baseResponse.getCells());
                            for (int i = 0; i < newsDatas.size(); i++) {
                                itemViewModels.add(new TodoItemViewModel(TodoListViewModel.this, newsDatas.get(i)));
                            }
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refreshLayout.finishRefresh();
                        Log.d("NewsViewModel", throwable.toString());
                    }
                });
    }
}
