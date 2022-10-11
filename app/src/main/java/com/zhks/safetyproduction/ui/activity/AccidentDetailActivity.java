package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.zhks.safetyproduction.constants.Constants.MAXSELECTNUM;
import static com.zhks.safetyproduction.constants.Constants.TO_DWZZR_PERSONNEL_CODE;
import static com.zhks.safetyproduction.constants.Constants.TO_PERSONNEL_CODE_YSR;
import static com.zhks.safetyproduction.constants.PermissionConstants.permissionsPhotoGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.MediaUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.AccidentDetailCategoryAdapter;
import com.zhks.safetyproduction.adapter.AccidentDetailLevelAdapter;
import com.zhks.safetyproduction.adapter.AccidentDetailTypeAdapter;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.adapter.GridImageAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.databinding.ActivityAccidentDetailBinding;
import com.zhks.safetyproduction.entity.AccidentBean;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CustomPopBean;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.manager.PictureSelectorManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.KeyboardUtils;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.AccidentDetailModel;
import com.zhks.safetyproduction.wight.FullyGridLayoutManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 事故快报填写
 */
public class AccidentDetailActivity extends BaseActivity<ActivityAccidentDetailBinding, AccidentDetailModel> implements View.OnClickListener {
    private String titleName;
    private View accidentCategoryPopView;
    private View accidentTypePopView;
    private View accidentLevelPopView;
    private View accidentCompanyPopView;
    private View accidentLeaderPopView;
    private View accidentCauseAnalysisPopView;

    private CustomPopWindow accidentCategoryPop;
    private CustomPopWindow accidentTypePop;
    private CustomPopWindow accidentLevelPop;
    private CustomPopWindow accidentCompanyPop;
    private CustomPopWindow accidentLeaderPop;
    private CustomPopWindow accidentCauseAnalysisPop;
    private CustomPopWindow uploadImageWindow;

    private RecyclerView accidentCategoryRv;
    private RecyclerView accidentTypeRv;
    private RecyclerView accidentLevelRv;
    private RecyclerView accidentCompanyRv;
    private RecyclerView accidentLeaderRv;
    private RecyclerView accidentCauseAnalysisRv;

    private AccidentDetailCategoryAdapter accidentCategoryAdapter;
    private AccidentDetailTypeAdapter accidentTypeAdapter;
    private AccidentDetailLevelAdapter accidentLevelAdapter;
    private CustomPopAdapter accidentCompanyAdapter;
    private CustomPopAdapter accidentLeaderAdapter;
    private CustomPopAdapter accidentCauseAnalysisAdapter;

    //事故类别
    private List<AccidentDetailBean.DataDTO.SGLXDTO> accidentCategoryDatas;
    //事故种类
    private List<AccidentDetailBean.DataDTO.SGFLDTO> accidentTypeDatas;
    //事故等级
    private List<AccidentDetailBean.DataDTO.SGDJDTO> accidentLevelDatas;
    private List<DepartmentBean.CellsDTO.DateDTO> accidentCompanyDatas;
    private List<DeptUserBean.CellsDTO> accidentLeaderDatas;
    private List<CustomPopBean> accidentCauseAnalysisDatas;
    private View uploadImageView;
    private static GridImageAdapter mAdapter;
    private ActivityResultLauncher<Intent> launcherResult;
    private int viewId;
    private static List<File> fileList = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    public int personalPageIndex = 1;
    private String selectDeptId;
    private DateEntity dateEntity;
    private String deptId;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_accident_detail;
    }


    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("提交");
        viewModel.setTitleText(titleName);
    }


    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Bundle bundle = getIntent().getExtras();
        titleName = bundle.getString("titleName");
    }

    @Override
    public void initData() {
        super.initData();
        accidentCategoryDatas = new ArrayList<>();
        accidentTypeDatas = new ArrayList<>();
        accidentLevelDatas = new ArrayList<>();
        accidentCompanyDatas = new ArrayList<>();
        accidentLeaderDatas = new ArrayList<>();
        accidentCauseAnalysisDatas = new ArrayList<>();
        dateEntity = DateEntity.today();
        accidentCategoryPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        accidentTypePopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        accidentLevelPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        accidentCompanyPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        accidentLeaderPopView = View.inflate(this, R.layout.personnel_pop_layout, null);
        refreshLayout = accidentLeaderPopView.findViewById(R.id.refreshLayout);
        initRefreshLayout();
        accidentCauseAnalysisPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        uploadImageView = View.inflate(this, R.layout.upload_image_pop_layout, null);
        uploadImageView.findViewById(R.id.tvTakePhoto).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvChooseImage).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvDismiss).setOnClickListener(this);

        accidentCategoryRv = accidentCategoryPopView.findViewById(R.id.spinner_pop_rv);
        accidentTypeRv = accidentTypePopView.findViewById(R.id.spinner_pop_rv);
        accidentLevelRv = accidentLevelPopView.findViewById(R.id.spinner_pop_rv);
        accidentCompanyRv = accidentCompanyPopView.findViewById(R.id.spinner_pop_rv);
        accidentLeaderRv = accidentLeaderPopView.findViewById(R.id.spinner_pop_rv);
        accidentCauseAnalysisRv = accidentCauseAnalysisPopView.findViewById(R.id.spinner_pop_rv);

        accidentCategoryRv.setLayoutManager(new LinearLayoutManager(this));
        accidentTypeRv.setLayoutManager(new LinearLayoutManager(this));
        accidentLevelRv.setLayoutManager(new LinearLayoutManager(this));
        accidentCompanyRv.setLayoutManager(new LinearLayoutManager(this));
        accidentLeaderRv.setLayoutManager(new LinearLayoutManager(this));
        accidentCauseAnalysisRv.setLayoutManager(new LinearLayoutManager(this));

        accidentCategoryAdapter = new AccidentDetailCategoryAdapter(R.layout.safe_check_type_item_layout, accidentCategoryDatas);
        accidentTypeAdapter = new AccidentDetailTypeAdapter(R.layout.safe_check_type_item_layout, accidentTypeDatas);
        accidentLevelAdapter = new AccidentDetailLevelAdapter(R.layout.safe_check_type_item_layout, accidentLevelDatas);
        accidentCompanyAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, accidentCompanyDatas);
        accidentLeaderAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, accidentLeaderDatas);
//        accidentCauseAnalysisAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, accidentCauseAnalysisDatas);
//        accidentPlaceAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, accidentPlaceDatas);

        accidentCategoryRv.setAdapter(accidentCategoryAdapter);
        accidentTypeRv.setAdapter(accidentTypeAdapter);
        accidentLevelRv.setAdapter(accidentLevelAdapter);
        accidentCompanyRv.setAdapter(accidentCompanyAdapter);
        accidentLeaderRv.setAdapter(accidentLeaderAdapter);
//        accidentCauseAnalysisRv.setAdapter(accidentCauseAnalysisAdapter);
//        accidentPlaceRv.setAdapter(accidentPlaceAdapter);
        binding.accidentDate.setText(DateUtils.getCurrentDate());

        accidentCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.accidentCategory.setText(accidentCategoryDatas.get(position).getParamname());
                if (null != accidentCategoryPop) {
                    accidentCategoryPop.dissmiss();
                }
            }
        });

        accidentTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.accidentType.setText(accidentTypeDatas.get(position).getParamname());
                if (null != accidentTypePop) {
                    accidentTypePop.dissmiss();
                }
            }
        });

        accidentLevelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.accidentLevel.setText(accidentLevelDatas.get(position).getParamname());
                if (null != accidentLevelPop) {
                    accidentLevelPop.dissmiss();
                }
            }
        });

        accidentCompanyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.accidentCompany.setText(accidentCompanyDatas.get(position).getDtname());
                selectDeptId = accidentCompanyDatas.get(position).getDtid();
                if (null != accidentCompanyPop) {
                    accidentCompanyPop.dissmiss();
                }
            }
        });

        accidentLeaderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.accidentLeader.setText(accidentLeaderDatas.get(position).getUsername());
                if (null != accidentLeaderPop) {
                    accidentLeaderPop.dissmiss();
                }
            }
        });
//
//        accidentCauseAnalysisAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                binding.accidentCauseAnalysis.setText(accidentCauseAnalysisDatas.get(position).getParamname());
//                if (null != accidentCauseAnalysisPop) {
//                    accidentCauseAnalysisPop.dissmiss();
//                }
//            }
//        });
//
//        accidentPlaceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                binding.accidentPlace.setText(accidentPlaceDatas.get(position).getParamname());
//                if (null != accidentPlacePop) {
//                    accidentPlacePop.dissmiss();
//                }
//            }
//        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.riskRegisterRv.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        binding.riskRegisterRv.setAdapter(mAdapter);
        // 注册需要写在onCreate或Fragment onAttach里，否则会报java.lang.IllegalStateException异常
        launcherResult = createActivityResultLauncher();
        binding.accidentName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    KeyboardUtils.toggleSoftInput(view);
                }
            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableScrollContentWhenLoaded(true);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout mRefreshLayout) {
//                personalPageIndex++;
//                viewModel.getDeptUser(selectDeptId, String.valueOf(personalPageIndex), refreshLayout);
            }
        });
    }


    private void showAccidentCategoryPop() {
        if (null == accidentCategoryPop) {
            accidentCategoryPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(accidentCategoryPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.accidentCategory.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.accidentCategory);
        } else {
            accidentCategoryPop.showAsDropDown(binding.accidentCategory);
        }
    }

    private void showAccidentTypePop() {
        if (null == accidentTypePop) {
            accidentTypePop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(accidentTypePopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.accidentType.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.accidentType);
        } else {
            accidentTypePop.showAsDropDown(binding.accidentType);
        }
    }

    private void showAccidentLevelPop() {
        if (null == accidentLevelPop) {
            accidentLevelPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(accidentLevelPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.accidentLevel.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.accidentLevel);
        } else {
            accidentLevelPop.showAsDropDown(binding.accidentLevel);
        }
    }

    private void showAccidentCompanyPop() {
        if (null == accidentCompanyPop) {
            accidentCompanyPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(accidentCompanyPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.accidentCompany.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.accidentCompany);
        } else {
            accidentCompanyPop.showAsDropDown(binding.accidentCompany);
        }
    }


    private void showAccidentLeaderPop() {
        if (null == accidentLeaderPop) {
            accidentLeaderPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(accidentLeaderPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.accidentLeader.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.accidentLeader);
        } else {
            accidentLeaderPop.showAsDropDown(binding.accidentLeader);
        }
    }


    private void showAccidentCauseAnalysisPop() {
        if (null == accidentCauseAnalysisPop) {
            accidentCauseAnalysisPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(accidentCauseAnalysisPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.accidentCauseAnalysis.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.accidentCauseAnalysis);
        } else {
            accidentCauseAnalysisPop.showAsDropDown(binding.accidentCauseAnalysis);
        }
    }

    private void showUploadImagePopWindow() {
        //创建并显示popWindow
        uploadImageWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(uploadImageView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(150))
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public AccidentDetailModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(AccidentDetailModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.accidentCategoryEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showAccidentCategoryPop();
            }
        });

        viewModel.accidentTypeEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showAccidentTypePop();
            }
        });

        viewModel.accidentLevelEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showAccidentLevelPop();
            }
        });

        viewModel.accidentCompanyEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showAccidentCompanyPop();
            }
        });

        viewModel.accidentLeaderEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
//                showAccidentLeaderPop();
                Intent intent = new Intent(AccidentDetailActivity.this, PersonnelQueryActivity.class);
                intent.putExtra("deptName", binding.accidentCompany.getText().toString());
                startActivityForResult(intent, TO_DWZZR_PERSONNEL_CODE);
            }
        });

        viewModel.accidentCauseAnalysisEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showAccidentCauseAnalysisPop();
            }
        });

        viewModel.accidentDateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(AccidentDetailActivity.this, true, dateEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.accidentDate.setText(year + "-" + month + "-" + day);
                        try {
                            Date date = DateUtils.formatterYmd.parse(year + "-" + month + "-" + day);
                            dateEntity = DateEntity.target(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        viewModel.rightTextClick.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(binding.accidentName.getText())) {
                    ToastUtils.showShort("请填写事故名称");
                    return;
                }

                if (TextUtils.isEmpty(binding.accidentCauseAnalysis.getText())) {
                    ToastUtils.showShort("请填写初步原因");
                    return;
                }

                if (TextUtils.isEmpty(binding.accidentBriefProcess.getText())) {
                    ToastUtils.showShort("请填写简要经过");
                    return;
                }

                if (TextUtils.isEmpty(binding.accidentSiteTreatment.getText())) {
                    ToastUtils.showShort("请填写事故现场情况");
                    return;
                }

                if (TextUtils.isEmpty(binding.accidentMeasuresTaken.getText())) {
                    ToastUtils.showShort("请填写已采取措施");
                    return;
                }

                AccidentBean accidentBean = new AccidentBean();
                accidentBean.setArname(binding.accidentName.getText().toString());
                accidentBean.setArtype(binding.accidentType.getText().toString());
                accidentBean.setArcategory(binding.accidentCategory.getText().toString());
                accidentBean.setArgrade(binding.accidentLevel.getText().toString());
                accidentBean.setAroccurdate(binding.accidentDate.getText().toString());
                accidentBean.setArsite(binding.accidentPlace.getText().toString());
                accidentBean.setArdutyman(binding.accidentLeader.getText().toString());
                accidentBean.setAroccurunit(binding.accidentCompany.getText().toString());
                accidentBean.setArreason(binding.accidentCauseAnalysis.getText().toString());
                accidentBean.setArcourse(binding.accidentBriefProcess.getText().toString());
                accidentBean.setArsituation(binding.accidentSiteTreatment.getText().toString());
                accidentBean.setArmeasure(binding.accidentMeasuresTaken.getText().toString());
                accidentBean.setQsrs(binding.minorWoundNumOfPeople.getText().toString());
                accidentBean.setZsrs(binding.seriouslyInjured.getText().toString());
                accidentBean.setSzrs(binding.missing.getText().toString());
                accidentBean.setSwrs(binding.death.getText().toString());
                accidentBean.setLossmoney(binding.estimateLoss.getText().toString());
                accidentBean.setCreateid(PersonInfoManager.getInstance().getUserId());
                String accidentJson = JSON.toJSONString(accidentBean);
                viewModel.accidentInfo(accidentJson, fileList);
            }
        });

        viewModel.accidentTypeCall.observe(this, new Observer<List<AccidentDetailBean.DataDTO.SGFLDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.SGFLDTO> sgfldtos) {
                accidentTypeDatas = sgfldtos;
                accidentTypeAdapter.setNewData(accidentTypeDatas);
                binding.accidentType.setText(accidentTypeDatas.get(0).getParamname());
            }
        });

        viewModel.accidentCategoryCall.observe(this, new Observer<List<AccidentDetailBean.DataDTO.SGLXDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.SGLXDTO> sglxdtos) {
                accidentCategoryDatas = sglxdtos;
                accidentCategoryAdapter.setNewData(accidentCategoryDatas);
                binding.accidentCategory.setText(accidentCategoryDatas.get(0).getParamname());
            }
        });

        viewModel.accidentLevelCall.observe(this, new Observer<List<AccidentDetailBean.DataDTO.SGDJDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.SGDJDTO> sgdjdtos) {
                accidentLevelDatas = sgdjdtos;
                accidentLevelAdapter.setNewData(accidentLevelDatas);
                binding.accidentLevel.setText(accidentLevelDatas.get(0).getParamname());
            }
        });

        viewModel.departMentEvent.observe(this, new Observer<List<DepartmentBean.CellsDTO.DateDTO>>() {
            @Override
            public void onChanged(List<DepartmentBean.CellsDTO.DateDTO> dateDTOS) {
//                viewModel.getDeptUser(dateDTOS.get(0).getDtid(), "1", refreshLayout);
                accidentCompanyDatas = dateDTOS;
                accidentCompanyAdapter.setNewData(accidentCompanyDatas);
                binding.accidentCompany.setText(accidentCompanyDatas.get(0).getDtname());
                selectDeptId = accidentCompanyDatas.get(0).getDtid();
                accidentCompanyAdapter.setNewData(accidentCompanyDatas);
            }
        });

//        viewModel.deptUserEvent.observe(this, new Observer<List<DeptUserBean.CellsDTO>>() {
//            @Override
//            public void onChanged(List<DeptUserBean.CellsDTO> cellsDTOS) {
//                accidentLeaderDatas.addAll(cellsDTOS);
//                accidentLeaderAdapter.setNewData(accidentLeaderDatas);
//                binding.accidentLeader.setText(accidentLeaderDatas.get(0).getUsername());
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//            for (LocalMedia media : selectList) {
//                if (media.getWidth() == 0 || media.getHeight() == 0) {
//                    if (PictureMimeType.isHasImage(media.getMimeType())) {
//                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
//                        media.setWidth(imageExtraInfo.getWidth());
//                        media.setHeight(imageExtraInfo.getHeight());
//                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
//                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(this, media.getPath());
//                        media.setWidth(videoExtraInfo.getWidth());
//                        media.setHeight(videoExtraInfo.getHeight());
//                    }
//                }
//            }
//            mAdapter.setList(selectList);
//            mAdapter.notifyDataSetChanged();
//        }
        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == TO_DWZZR_PERSONNEL_CODE) {
                deptId = data.getStringExtra("dtid");
                binding.accidentLeader.setText(data.getStringExtra("personnelTv"));
            }
        }

    }


    /**
     * 增加图片按钮
     */
    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            showUploadImagePopWindow();
        }
    };

    /**
     * 创建一个ActivityResultLauncher
     *
     * @return
     */
    private ActivityResultLauncher<Intent> createActivityResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == RESULT_OK) {
                            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(result.getData());
                            fileList.clear();
                            for (LocalMedia media : selectList) {
                                if (media.getWidth() == 0 || media.getHeight() == 0) {
                                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                                        media.setWidth(imageExtraInfo.getWidth());
                                        media.setHeight(imageExtraInfo.getHeight());
                                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(AccidentDetailActivity.this, media.getPath());
                                        media.setWidth(videoExtraInfo.getWidth());
                                        media.setHeight(videoExtraInfo.getHeight());
                                    }
                                }
                                fileList.add(new File(media.getRealPath()));
                                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
                            }
                            mAdapter.setFileList(fileList);
                            mAdapter.setList(selectList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    /**
     * 返回结果回调
     */
    public static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                if (media.getWidth() == 0 || media.getHeight() == 0) {
                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                        media.setWidth(imageExtraInfo.getWidth());
                        media.setHeight(imageExtraInfo.getHeight());
                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(PictureAppMaster.getInstance().getAppContext(), media.getPath());
                        media.setWidth(videoExtraInfo.getWidth());
                        media.setHeight(videoExtraInfo.getHeight());
                    }
                }
                fileList.add(new File(media.getRealPath()));
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapter.setFileList(fileList);
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
        }
    }

    @SuppressLint("CheckResult")
    private void chooseImage() {
        new RxPermissions(this).request(permissionsPhotoGroup).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    if (viewId == R.id.tvTakePhoto) {
                        PictureSelectorManager.openCamera(AccidentDetailActivity.this, MAXSELECTNUM, mAdapter.getData(), mAdapter, new MyResultCallback(mAdapter));
                    } else if (viewId == R.id.tvChooseImage) {
                        PictureSelectorManager.openAlbum(AccidentDetailActivity.this,
                                PictureMimeType.ofImage(), MAXSELECTNUM, mAdapter.getData(), launcherResult);
                    }
                } else {
                    ToastUtils.showShort("获取权限失败，请在设置中打开相关权限");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        viewId = view.getId();
        if (viewId == R.id.tvTakePhoto) {
            if (null != uploadImageWindow) {
                chooseImage();
                uploadImageWindow.dissmiss();
            }
        } else if (viewId == R.id.tvChooseImage) {
            chooseImage();
            if (null != uploadImageWindow) {
                uploadImageWindow.dissmiss();
            }
        } else if (viewId == R.id.tvDismiss) {
            if (null != uploadImageWindow) {
                uploadImageWindow.dissmiss();
            }
        }
    }
}