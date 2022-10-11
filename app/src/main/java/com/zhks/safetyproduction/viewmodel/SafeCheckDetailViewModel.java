package com.zhks.safetyproduction.viewmodel;

import static com.zhks.safetyproduction.constants.Constants.SUCCESS_CODE;
import static com.zhks.safetyproduction.constants.Constants.SUCCESS_STR;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.entity.SafeCheckTermBean;
import com.zhks.safetyproduction.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SafeCheckDetailViewModel extends ToolbarViewModel<HomeModel> {
    public ObservableInt loadingVisible = new ObservableInt(View.VISIBLE);
    public List<AccidentDetailBean.DataDTO.JCLXDTO> jclxDatas = new ArrayList<>();
    public List<AccidentDetailBean.DataDTO.JCJBDTO> jcjbDatas = new ArrayList<>();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.JCLXDTO>> dictionariesEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.JCJBDTO>> jcjbEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DepartmentBean.CellsDTO.DateDTO>> departMentEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DeptUserBean.CellsDTO>> deptUserEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<SafeCheckTermBean.CellsDTO>> safeCheckEvent = new SingleLiveEvent<>();
    public SingleLiveEvent rightEvent = new SingleLiveEvent();

    public SafeCheckDetailViewModel(@NonNull Application application, HomeModel model, Context context) {
        super(application, model);
        getDictionaries("JCLX,JCJB");
//        getDepartment();

    }

    private void getDictionaries(String dicttypeids) {
        model.getAccidentDictionaries(dicttypeids)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(SafeCheckDetailViewModel.this)
                .subscribe(new Consumer<AccidentDetailBean>() {
                    @Override
                    public void accept(AccidentDetailBean baseResponse) throws Exception {
                        loadingVisible.set(View.GONE);
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            jclxDatas.addAll(baseResponse.getData().getJCLX());
                            jcjbDatas.addAll(baseResponse.getData().getJCJB());
                            dictionariesEvent.setValue(jclxDatas);
                            jcjbEvent.setValue(jcjbDatas);
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingVisible.set(View.GONE);
                        Log.d("Dictionaries", throwable.toString());
                    }
                });
    }

    private void getDepartment() {
        //请求部门单位
        model.getDepartment()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(SafeCheckDetailViewModel.this)
                .subscribe(new Consumer<DepartmentBean>() {
                    @Override
                    public void accept(DepartmentBean baseResponse) throws Exception {
                        if (TextUtils.equals("0000", baseResponse.getCode())) {
                            departMentEvent.setValue(baseResponse.getCells().get(0).getDate());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Department", throwable.toString());
                    }
                });
    }


    public void getDeptUser(String pageId, SmartRefreshLayout refreshLayout) {
        model.getListUser("", pageId)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(SafeCheckDetailViewModel.this)
                .subscribe(new Consumer<DeptUserBean>() {
                    @Override
                    public void accept(DeptUserBean baseResponse) throws Exception {
                        if (TextUtils.equals(SUCCESS_STR, baseResponse.getSuccess())) {
                            deptUserEvent.setValue(baseResponse.getCells());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        refreshLayout.finishLoadMore();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("DeptUser", throwable.toString());
                    }
                });
    }

    public void getSafeCheckTerm(String scmid) {
        model.getSafeCheckTerm(scmid)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(SafeCheckDetailViewModel.this)
                .subscribe(new Consumer<SafeCheckTermBean>() {
                    @Override
                    public void accept(SafeCheckTermBean baseResponse) throws Exception {
                        if (TextUtils.equals(SUCCESS_CODE, baseResponse.getCode())) {
                            for (int i = 0; i < baseResponse.getCells().size(); i++) {
                                baseResponse.getCells().get(i).setClickble(true);
                            }
                            safeCheckEvent.setValue(baseResponse.getCells());
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("DeptUser", throwable.toString());
                    }
                });
    }

    @Override
    protected void rightTextOnClick() {
        rightEvent.call();
    }

    public void insertCheckreg(String modelJson) {
        model.insertCheckreg(modelJson)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(SafeCheckDetailViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        loadingVisible.set(View.GONE);
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            ToastUtils.showShort(baseResponse.getMessage());
                            finish();
                        } else {
                            ToastUtils.showShort(baseResponse.getErrormessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingVisible.set(View.GONE);
                        Log.d("LoginViewModel", throwable.toString());
                    }
                });
    }
}
