package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CustomResponse;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.EnclosureBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.model.HomeModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class RiskRegisterViewModel extends ToolbarViewModel<HomeModel> {
    public SingleLiveEvent rectificationClick = new SingleLiveEvent();
    public SingleLiveEvent rectificationPersonnelEvent = new SingleLiveEvent();
    public SingleLiveEvent acceptancePersonnelEvent = new SingleLiveEvent();
    public SingleLiveEvent riskSourceEvent = new SingleLiveEvent();
    public SingleLiveEvent riskCategoryEvent = new SingleLiveEvent();
    public SingleLiveEvent riskAddressEvent = new SingleLiveEvent();
    public ObservableField<String> riskDescribeTv = new ObservableField<>();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.YHLYDTO>> yhlyEvent = new SingleLiveEvent<>();
    public Map<String, String> yhlyMap = new HashMap<>();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.YHLBDTO>> yhlbEvent = new SingleLiveEvent<>();
    public Map<String, String> yhlbMap = new HashMap<>();
    public SingleLiveEvent<List<AccidentDetailBean.DataDTO.ZGLXDTO>> zglxEvent = new SingleLiveEvent<>();
    public Map<String, String> zglxMap = new HashMap<>();
    public SingleLiveEvent<List<DepartmentBean.CellsDTO.DateDTO>> departMentEvent = new SingleLiveEvent();
    public SingleLiveEvent departCommandEvent = new SingleLiveEvent();
    public SingleLiveEvent troubleshootingManEvent = new SingleLiveEvent();
    public SingleLiveEvent riskFindDateEvent = new SingleLiveEvent();
    public SingleLiveEvent rectificationTypeEvent = new SingleLiveEvent();
    public SingleLiveEvent insertSuccessEvent = new SingleLiveEvent();
    public ObservableInt loadingVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent<List<EnclosureBean.DataDTO>> enclosureEvent = new SingleLiveEvent();
    public ObservableInt zdyhDataVisible = new ObservableInt(View.GONE);
    public SingleLiveEvent beianClickEvent = new SingleLiveEvent();
    public SingleLiveEvent saveRiskClickEvent = new SingleLiveEvent();
    public SingleLiveEvent submitRiskClickEvent = new SingleLiveEvent();


    public ObservableList<Object> items = new ObservableArrayList<>();

    public RiskRegisterViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        getDictionaries("YHLY,YHLB,ZGLX");
        getDepartment();
    }

    /**
     * 整改期限
     */
    public BindingCommand rectificationDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            rectificationClick.call();
        }
    });

    /**
     * 隐患排查人
     */
    public BindingCommand troubleshootingManCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            troubleshootingManEvent.call();
        }
    });

    /**
     * 整改人员
     */
    public BindingCommand rectificationPersonnelCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            rectificationPersonnelEvent.call();
        }
    });


    /**
     * 验收人员
     */
    public BindingCommand acceptanceCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            acceptancePersonnelEvent.call();
        }
    });


    /**
     * 隐患来源
     */
    public BindingCommand riskSourceCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            riskSourceEvent.call();
        }
    });

    /**
     * 隐患类别
     */
    public BindingCommand riskCategoryCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            riskCategoryEvent.call();
        }
    });

//    /**
//     * 隐患等级
//     */
//    public BindingCommand riskLevelCommand = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            riskLevelEvent.call();
//        }
//    });

    /**
     * 隐患发现时间
     */
    public BindingCommand riskFindDateCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            riskFindDateEvent.call();
        }
    });

    /**
     * 隐患地点
     */
    public BindingCommand riskAddressCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            riskAddressEvent.call();
        }
    });

    /**
     * 隐患所在单位
     */
    public BindingCommand riskCompanyCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            departCommandEvent.call();
        }
    });

    /**
     * 整改类型
     */
    public BindingCommand rectificationTypeCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            rectificationTypeEvent.call();
        }
    });

    /**
     * 备案日期
     */
    public BindingCommand beianClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            beianClickEvent.call();
        }
    });

    public BindingCommand saveRiskClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            saveRiskClickEvent.call();
        }
    });

    public BindingCommand submitRiskClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            submitRiskClickEvent.call();
        }
    });


    private void getDictionaries(String dicttypeids) {
        model.getAccidentDictionaries(dicttypeids)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskRegisterViewModel.this)
                .subscribe(new Consumer<AccidentDetailBean>() {
                    @Override
                    public void accept(AccidentDetailBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            yhlyEvent.setValue(baseResponse.getData().getyHLY());
                            for (int i = 0; i < baseResponse.getData().getyHLY().size(); i++) {
                                yhlyMap.put(baseResponse.getData().getyHLY().get(i).getParamname(),
                                        baseResponse.getData().getyHLY().get(i).getCode());
                            }
                            yhlbEvent.setValue(baseResponse.getData().getyHLB());
                            for (int i = 0; i < baseResponse.getData().getyHLB().size(); i++) {
                                yhlbMap.put(baseResponse.getData().getyHLB().get(i).getParamname(),
                                        baseResponse.getData().getyHLB().get(i).getCode());
                            }
//                          yhjbEvent.setValue(baseResponse.getData().getyHJB());
                            zglxEvent.setValue(baseResponse.getData().getzGLX());
                            for (int i = 0; i < baseResponse.getData().getzGLX().size(); i++) {
                                zglxMap.put(baseResponse.getData().getzGLX().get(i).getParamname(),
                                        baseResponse.getData().getzGLX().get(i).getCode());
                            }

                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Dictionaries", throwable.toString());
                    }
                });
    }

    /**
     * 获取附件
     */
    public void getEnclosure(String businessid){
        model.selectAttachmentApp(businessid)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskRegisterViewModel.this)
                .subscribe(new Consumer<EnclosureBean>() {
                    @Override
                    public void accept(EnclosureBean baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE2, baseResponse.getCode())) {
                            enclosureEvent.setValue(baseResponse.getData());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("RegisterViewModel", throwable.toString());
                    }
                });

    }


    public void insertRegister(String modelJson, List<File> files) {
        loadingVisible.set(View.VISIBLE);
        model.insertRegister(modelJson, PersonInfoManager.getInstance().getUserId(), files)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskRegisterViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            insertSuccessEvent.call();
                            ToastUtils.showShort(baseResponse.getMessage());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("RegisterViewModel", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }

    public void saveRegister(String modelJson, List<File> files) {
        loadingVisible.set(View.VISIBLE);
        model.saveRegister(modelJson, PersonInfoManager.getInstance().getUserId(), files)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskRegisterViewModel.this)
                .subscribe(new Consumer<CustomResponse>() {
                    @Override
                    public void accept(CustomResponse baseResponse) throws Exception {
                        if (TextUtils.equals(Constants.SUCCESS_CODE, baseResponse.getCode())) {
                            insertSuccessEvent.call();
                            ToastUtils.showShort(baseResponse.getMessage());
                        } else {
                            ToastUtils.showShort(baseResponse.getMessage());
                        }
                        loadingVisible.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("RegisterViewModel", throwable.toString());
                        loadingVisible.set(View.GONE);
                    }
                });
    }


    public void getDepartment() {
        //请求部门单位
        model.getDepartment()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(RiskRegisterViewModel.this)
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


}
