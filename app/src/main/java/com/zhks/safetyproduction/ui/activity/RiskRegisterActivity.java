package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static com.zhks.safetyproduction.constants.Constants.MAXSELECTNUM;
import static com.zhks.safetyproduction.constants.Constants.REQUEST_CODE_CHOOSE;
import static com.zhks.safetyproduction.constants.Constants.TO_MULTI_CODE;
import static com.zhks.safetyproduction.constants.Constants.TO_PERSONNEL_CODE;
import static com.zhks.safetyproduction.constants.Constants.TO_PERSONNEL_CODE_PCR;
import static com.zhks.safetyproduction.constants.Constants.TO_PERSONNEL_CODE_YSR;
import static com.zhks.safetyproduction.constants.PermissionConstants.permissionsPhotoGroup;

import static me.goldze.mvvmhabit.utils.DonwloadSaveImg.saveFileList;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.MediaUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.adapter.GridImageAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityRiskRegisterBinding;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CustomPopBean;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.EnclosureBean;
import com.zhks.safetyproduction.entity.HiddenDBean;
import com.zhks.safetyproduction.entity.RiskRegisterBean;
import com.zhks.safetyproduction.entity.ToBeRectifiedBean;
import com.zhks.safetyproduction.manager.DaoManager;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.manager.PictureSelectorManager;
import com.zhks.safetyproduction.utils.CommonDaoUtils;
import com.zhks.safetyproduction.utils.DaoUtilsStore;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.GlideUtil;
import com.zhks.safetyproduction.utils.NetUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.utils.StringUtils;
import com.zhks.safetyproduction.utils.UUIDUtils;
import com.zhks.safetyproduction.viewmodel.RiskRectificationViewModel;
import com.zhks.safetyproduction.viewmodel.RiskRegisterViewModel;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.zhks.safetyproduction.wight.FullyGridLayoutManager;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.DonwloadSaveImg;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 隐患登记
 */
public class RiskRegisterActivity extends BaseActivity<ActivityRiskRegisterBinding, RiskRegisterViewModel>
        implements View.OnClickListener {
    private View riskSourcePopView;
    private View riskCategoryPopView;
    //    private View riskLevelPopView;
    private View riskAddressPopView;
    private View riskCompanyPopView;
    private View zglxPopView;

    private CustomPopWindow riskSourcePop;
    private CustomPopWindow riskCategoryPop;
    //    private CustomPopWindow riskLevelPop;
    private CustomPopWindow riskCompanyPop;
    private CustomPopWindow zglxPop;

    private RecyclerView riskSourceRv;
    private RecyclerView riskCategoryRv;
    //    private RecyclerView riskLevelRv;
    private RecyclerView riskCompanyRv;
    private RecyclerView zglxRv;

    private CustomPopAdapter riskSourceAdapter;
    private CustomPopAdapter riskCategoryAdapter;
    //    private CustomPopAdapter riskLevelAdapter;
    private CustomPopAdapter riskCompanyAdapter;
    private CustomPopAdapter zglxAdapter;

    private List<AccidentDetailBean.DataDTO.YHLYDTO> riskSourceDatas;
    private List<AccidentDetailBean.DataDTO.YHLBDTO> riskCategoryDatas;
    //    private List<AccidentDetailBean.DataDTO.YHJBDTO> riskLevelDatas;
    private List<DepartmentBean.CellsDTO.DateDTO> riskCompanyDatas;
    private List<AccidentDetailBean.DataDTO.ZGLXDTO> zglxDatas;
    private CustomPopWindow uploadImageWindow;
    private View uploadImageView;
    private static GridImageAdapter mAdapter;
    private ActivityResultLauncher<Intent> launcherResult;
    private int viewId;
    private DateEntity dateEntity;
    private DateEntity riskFindDateEntity;
    private String riskItemStr;
    private String checkTypeTv;
    private String checkRegionTv;
    private String checkUnqualified;
    private ToBeRectifiedBean.DataDTO riskItem;
    private String selectDeptId;
    private String zgzrrId;//整改责任人id
    private String yszrrId;//验收责任人id
    private static List<File> fileList = new ArrayList<>();
    private List<LocalMedia> exFiles = new ArrayList<>();
    private List<File> fileListReady = new ArrayList<>();
    private long systemMill;
    private HiddenDBean hiddenBean; //草稿传递的对象
    private List<LocalMedia> ylList = new ArrayList<>();
    private static final String[] permissionsGroup = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private List<String> imgList = new ArrayList<>();
    private String tag;
    private DateEntity beianEntity;
    private String lscsCode, baztCode, gpdbCode;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_risk_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("隐患登记");
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Intent intent = getIntent();
        if (null != intent) {
            tag = intent.getStringExtra("tag");
            riskItemStr = intent.getStringExtra("riskItem");
            riskItem = JSON.parseObject(riskItemStr, ToBeRectifiedBean.DataDTO.class);
            checkTypeTv = intent.getStringExtra("checkTypeTv");
            checkRegionTv = intent.getStringExtra("checkRegionTv");
            checkUnqualified = intent.getStringExtra("checkUnqualified");
            hiddenBean = (HiddenDBean) intent.getSerializableExtra("hiddenBean");
        }
    }

    @Override
    public void initData() {
        if (TextUtils.equals("0", tag)) {
            viewModel.zdyhDataVisible.set(View.GONE);
        } else {
            viewModel.zdyhDataVisible.set(View.VISIBLE);
            beianEntity = DateEntity.today();
            binding.beianDate.setText(DateUtils.getCurrentDate());
            if (null != riskItem) {
                lscsCode = riskItem.getCqlscs();
                if (TextUtils.equals("0", lscsCode)) {
                    binding.lscsNo.setChecked(true);
                } else {
                    binding.lscsYes.setChecked(true);
                }
                baztCode = riskItem.getBazt();
                if (TextUtils.equals("0", baztCode)) {
                    binding.baztWba.setChecked(true);
                } else if (TextUtils.equals("1", baztCode)) {
                    binding.baztYba.setChecked(true);
                } else if (TextUtils.equals("2", baztCode)) {
                    binding.baztYhx.setChecked(true);
                }
                gpdbCode = riskItem.getGpdb();
                if (TextUtils.equals("0", gpdbCode)) {
                    binding.gpdbNo.setChecked(true);
                } else {
                    binding.gpdbYes.setChecked(true);
                }
                binding.sourceOfFunds.setText(riskItem.getMoneysource());
                binding.governanceCosts.setText(riskItem.getMoney());
                binding.filingNum.setText(riskItem.getBabh());
                binding.acceptanceUnit.setText(riskItem.getSlunit());
                binding.beianDate.setText(riskItem.getBarq());
            } else {
                lscsCode = "1";
                baztCode = "0";
                gpdbCode = "1";
                binding.lscsYes.setChecked(true);
                binding.baztWba.setChecked(true);
                binding.gpdbYes.setChecked(true);
            }


            binding.lscsRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if (checkedId == R.id.lscs_yes) {
                        lscsCode = "1";
                    } else if (checkedId == R.id.lscs_no) {
                        lscsCode = "0";
                    }
                }
            });


            binding.baztRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if (checkedId == R.id.bazt_wba) {
                        baztCode = "0";
                    } else if (checkedId == R.id.bazt_yba) {
                        baztCode = "1";
                    } else if (checkedId == R.id.bazt_yhx) {
                        baztCode = "2";
                    }
                }
            });


            binding.gpdbRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if (checkedId == R.id.gpdb_yes) {
                        gpdbCode = "1";
                    } else if (checkedId == R.id.gpdb_no) {
                        gpdbCode = "0";
                    }
                }
            });

        }
        systemMill = System.currentTimeMillis();
        riskSourceDatas = new ArrayList<>();
        riskCategoryDatas = new ArrayList<>();
//        riskLevelDatas = new ArrayList<>();
        riskCompanyDatas = new ArrayList<>();
        zglxDatas = new ArrayList<>();

        dateEntity = DateEntity.today();
        riskFindDateEntity = DateEntity.today();
        riskSourcePopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        riskCategoryPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
//        riskLevelPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        riskAddressPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        riskCompanyPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        zglxPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        uploadImageView = View.inflate(this, R.layout.upload_image_pop_layout, null);
        uploadImageView.findViewById(R.id.tvTakePhoto).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvChooseImage).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvDismiss).setOnClickListener(this);

        riskSourceRv = riskSourcePopView.findViewById(R.id.spinner_pop_rv);
        riskCategoryRv = riskCategoryPopView.findViewById(R.id.spinner_pop_rv);
//        riskLevelRv = riskLevelPopView.findViewById(R.id.spinner_pop_rv);
        riskCompanyRv = riskCompanyPopView.findViewById(R.id.spinner_pop_rv);
        zglxRv = zglxPopView.findViewById(R.id.spinner_pop_rv);

        riskSourceRv.setLayoutManager(new LinearLayoutManager(this));
        riskCategoryRv.setLayoutManager(new LinearLayoutManager(this));
//        riskLevelRv.setLayoutManager(new LinearLayoutManager(this));
        riskCompanyRv.setLayoutManager(new LinearLayoutManager(this));
        zglxRv.setLayoutManager(new LinearLayoutManager(this));

        riskSourceAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, riskSourceDatas);
        riskCategoryAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, riskCategoryDatas);
//        riskLevelAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, riskLevelDatas);
        riskCompanyAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, riskCompanyDatas);
        zglxAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, zglxDatas);

        riskSourceRv.setAdapter(riskSourceAdapter);
        riskCategoryRv.setAdapter(riskCategoryAdapter);
//        riskLevelRv.setAdapter(riskLevelAdapter);
        riskCompanyRv.setAdapter(riskCompanyAdapter);
        zglxRv.setAdapter(zglxAdapter);

        if (null != riskItem) {
            binding.riskAddress.setText(riskItem.getZgdutyunit());
            viewModel.riskDescribeTv.set(riskItem.getTrdescribe());
            binding.troubleshootingMan.setText(riskItem.getTrfoundman());
            binding.riskFindDate.setText(riskItem.getTrfounddate());
            binding.rectificationDate.setText(riskItem.getZgterm());
            binding.rectificationType.setText(riskItem.getZgtype());
            binding.rectificationPersonnel.setText(riskItem.getZgdutyman());
            binding.acceptancePersonnel.setText(riskItem.getYsdutyman());
            binding.acceptanceDept.setText(riskItem.getYsdutyunit());
            binding.rectificationMeasures.setText(riskItem.getZgmeasure());
            binding.riskCompany.setText(riskItem.getTrsitename());
            viewModel.getEnclosure(riskItem.getTrid());
        } else {
            if (!TextUtils.isEmpty(checkUnqualified)) {
                viewModel.riskDescribeTv.set("检查项目具体内容：不合格");
            }
            if (!TextUtils.isEmpty(checkRegionTv)) {
                binding.riskAddress.setText(checkRegionTv);
            }

            binding.riskFindDate.setText(DateUtils.getCurrentDate());
            binding.rectificationDate.setText(DateUtils.getCurrentDate());
        }


        riskSourceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.riskSource.setText(riskSourceDatas.get(position).getParamname());
                if (null != riskSourcePop) {
                    riskSourcePop.dissmiss();
                }
            }
        });

        riskCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.riskCategory.setText(riskCategoryDatas.get(position).getParamname());
                if (null != riskCategoryPop) {
                    riskCategoryPop.dissmiss();
                }
            }
        });

//        riskLevelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                binding.riskLevel.setText(riskLevelDatas.get(position).getParamname());
//                if (null != riskLevelPop) {
//                    riskLevelPop.dissmiss();
//                }
//            }
//        });

        riskCompanyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.riskCompany.setText(riskCompanyDatas.get(position).getDtname());
                selectDeptId = riskCompanyDatas.get(position).getDtid();
                if (null != riskCompanyPop) {
                    riskCompanyPop.dissmiss();
                }
            }
        });

        zglxAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.rectificationType.setText(zglxDatas.get(position).getParamname());
                if (null != zglxPop) {
                    zglxPop.dissmiss();
                }
            }
        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.riskRegisterRv.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        binding.riskRegisterRv.setAdapter(mAdapter);
        // 注册需要写在onCreate或Fragment onAttach里，否则会报java.lang.IllegalStateException异常
        launcherResult = createActivityResultLauncher();

        //数据库草稿赋值
        if (null != hiddenBean) {
            binding.troubleshootingMan.setText(hiddenBean.getTrfoundman());
            binding.riskFindDate.setText(hiddenBean.getTrfounddate());
            binding.riskAddress.setText(hiddenBean.getScregion());
            viewModel.riskDescribeTv.set(hiddenBean.getTrdescribe());
            binding.rectificationDate.setText(hiddenBean.getZgterm());
            binding.rectificationPersonnel.setText(hiddenBean.getZgdutyman());
            binding.acceptancePersonnel.setText(hiddenBean.getYsdutyman());
            binding.acceptanceDept.setText(hiddenBean.getYsdutyunit());
            binding.rectificationMeasures.setText(hiddenBean.getZgmeasure());
        }
    }

    @Override
    public RiskRegisterViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(RiskRegisterViewModel.class);
    }

    @Override
    public void initViewObservable() {
        //隐患来源
        viewModel.yhlyEvent.observe(this, new Observer<List<AccidentDetailBean.DataDTO.YHLYDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.YHLYDTO> yhlydtos) {
                riskSourceDatas = yhlydtos;
                if (null == riskItem) {
                    for (int i = 0; i < yhlydtos.size(); i++) {
                        if (TextUtils.equals(riskSourceDatas.get(i).getParamname(), checkTypeTv)) {
                            binding.riskSource.setText(riskSourceDatas.get(i).getParamname());
                            break;
                        } else {
                            binding.riskSource.setText(riskSourceDatas.get(0).getParamname());
                        }
                    }
                } else {
                    binding.riskSource.setText(riskItem.getTrsourcename());
                }

                //如果是从草稿箱跳进来，从草稿取数据
                if (null != hiddenBean) {
                    binding.riskSource.setText(hiddenBean.getTrsource());
                }
                riskSourceAdapter.setNewData(riskSourceDatas);
            }
        });

        //隐患类型
        viewModel.yhlbEvent.observe(this, new Observer<List<AccidentDetailBean.DataDTO.YHLBDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.YHLBDTO> yhlbdtos) {
                riskCategoryDatas = yhlbdtos;
                if (null == riskItem) {
                    binding.riskCategory.setText(riskCategoryDatas.get(0).getParamname());
                } else {
                    binding.riskCategory.setText(riskItem.getTrcategoryname());
                }

                if (null != hiddenBean) {
                    binding.riskCategory.setText(hiddenBean.getTrcategory());
                }

                riskCategoryAdapter.setNewData(riskCategoryDatas);
            }
        });

//        //隐患级别
//        viewModel.yhjbEvent.observe(this, new Observer<List<AccidentDetailBean.DataDTO.YHJBDTO>>() {
//            @Override
//            public void onChanged(List<AccidentDetailBean.DataDTO.YHJBDTO> yhjbdtos) {
//                riskLevelDatas = yhjbdtos;
//                if (null == riskItem) {
//                    binding.riskLevel.setText(riskLevelDatas.get(0).getParamname());
//                } else {
//                    binding.riskLevel.setText(riskItem.getTrlevelname());
//                }
//                riskLevelAdapter.setNewData(riskLevelDatas);
//            }
//        });

        //整改类型
        viewModel.zglxEvent.observe(this, new Observer<List<AccidentDetailBean.DataDTO.ZGLXDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.ZGLXDTO> zglxdtos) {
                zglxDatas = zglxdtos;
                if (null != hiddenBean) {
                    binding.rectificationType.setText(hiddenBean.getZgtype());
                } else {
                    binding.rectificationType.setText(zglxDatas.get(0).getParamname());
                }

                zglxAdapter.setNewData(zglxdtos);
            }
        });

        viewModel.rectificationClick.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(RiskRegisterActivity.this, true, dateEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.rectificationDate.setText(year + "-" + month + "-" + day);
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

        viewModel.rectificationPersonnelEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(RiskRegisterActivity.this, PersonnelQueryActivity.class);
                startActivityForResult(intent, TO_PERSONNEL_CODE);
            }
        });


        viewModel.acceptancePersonnelEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(RiskRegisterActivity.this, PersonnelQueryActivity.class);
                startActivityForResult(intent, TO_PERSONNEL_CODE_YSR);
            }
        });

        viewModel.riskSourceEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showRiskSourcePop();
            }
        });

        viewModel.riskCategoryEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showRiskCategoryPop();
            }
        });

//        viewModel.riskLevelEvent.observe(this, new Observer() {
//            @Override
//            public void onChanged(Object o) {
//                showRiskLevelPop();
//            }
//        });

        viewModel.riskAddressEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                //隐患区域
                Intent intent = new Intent(RiskRegisterActivity.this, MultistageActivity.class);
                intent.putExtra(Constants.MULTI_TITLE, "区域选择");
                startActivityForResult(intent, TO_MULTI_CODE);
            }
        });

        //隐患所在单位
        viewModel.departMentEvent.observe(this, new Observer<List<DepartmentBean.CellsDTO.DateDTO>>() {
            @Override
            public void onChanged(List<DepartmentBean.CellsDTO.DateDTO> dataDTOS) {
                riskCompanyDatas = dataDTOS;
                if (null != hiddenBean) {
                    binding.riskCompany.setText(hiddenBean.getTrsitename());
                    selectDeptId = hiddenBean.getTrsite();
                } else {
                    binding.riskCompany.setText(dataDTOS.get(0).getDtname());
                    selectDeptId = dataDTOS.get(0).getDtid();
                }

                riskCompanyAdapter.setNewData(dataDTOS);
            }
        });

        viewModel.departCommandEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showRiskCompanyPopWindow();
            }
        });

        //隐患排查人
        viewModel.troubleshootingManEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(RiskRegisterActivity.this, PersonnelQueryActivity.class);
                startActivityForResult(intent, TO_PERSONNEL_CODE_PCR);
            }
        });

        //隐患发现时间
        viewModel.riskFindDateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(RiskRegisterActivity.this, false, riskFindDateEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.riskFindDate.setText(year + "-" + month + "-" + day);
                        Date date = null;
                        try {
                            date = DateUtils.formatterYmd.parse(year + "-" + month + "-" + day);
                            riskFindDateEntity = DateEntity.target(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        //整改类型
        viewModel.rectificationTypeEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showZglxPop();
            }
        });

        viewModel.saveRiskClickEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                submit(true);
            }
        });


        //提交隐患
        viewModel.submitRiskClickEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
//                if (NetUtil.isNetworkAvailable(RiskRegisterActivity.this)) {
                    //有网络
                    submit(false);
//                } else {
//                    if (TextUtils.isEmpty(binding.riskDescribe.getText().toString())) {
//                        ToastUtils.showShort("请填写隐患描述");
//                        return;
//                    }
//                    //无网络
//                    HiddenDBean hiddenDBean = new HiddenDBean();
//                    hiddenDBean.setHiddenId(systemMill);
//                    hiddenDBean.setTrsource(binding.riskSource.getText().toString());
//                    hiddenDBean.setTrcategory(binding.riskCategory.getText().toString());
//                    hiddenDBean.setTrlevel("一般隐患");
//                    hiddenDBean.setTrsite(selectDeptId);
//                    hiddenDBean.setTrsitename(binding.riskCompany.getText().toString());
//                    hiddenDBean.setScregion(binding.riskAddress.getText().toString());
//                    hiddenDBean.setTrfoundman(binding.troubleshootingMan.getText().toString());
//                    hiddenDBean.setTrfounddate(binding.riskFindDate.getText().toString());
//                    hiddenDBean.setTrdescribe(binding.riskDescribe.getText().toString());
//                    hiddenDBean.setZgtype(binding.rectificationType.getText().toString());
//                    hiddenDBean.setZgterm(binding.rectificationDate.getText().toString());
//                    hiddenDBean.setZgdutyman(binding.rectificationPersonnel.getText().toString());
////                  hiddenDBean.setZgdutyunit(binding.);
//                    hiddenDBean.setZgmeasure(binding.rectificationMeasures.getText().toString());
//                    hiddenDBean.setYsdutyman(binding.acceptancePersonnel.getText().toString());
//                    hiddenDBean.setYsdutyunit(binding.acceptanceDept.getText().toString());
//                    hiddenDBean.setInputDate(DateUtils.getCurrentDateYMDHMS());
//                    hiddenDBean.setIsCheck(false);
//                    String fileListStr = getFileListString(mAdapter.getData());
//                    hiddenDBean.setFileList(fileListStr);
//                    DaoUtilsStore.getInstance().getHiddenDaoUtils().insertOrReplace(hiddenDBean);
//                    ToastUtils.showShort("保存草稿成功");
//                }
            }
        });

        viewModel.insertSuccessEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                setResult(RESULT_OK);
                finish();
            }
        });

        /**
         * 获取附件
         */
        viewModel.enclosureEvent.observe(this, new Observer<List<EnclosureBean.DataDTO>>() {
            @Override
            public void onChanged(List<EnclosureBean.DataDTO> dataDTOS) {
                if (dataDTOS.isEmpty()) {
                    return;
                }
                for (int i = 0; i < dataDTOS.size(); i++) {
                    LocalMedia localMedia = new LocalMedia();
                    String filePath = Constants.IP_URL + dataDTOS.get(i).getAttachmenturl();
                    localMedia.setRealPath(filePath);
                    long time = System.currentTimeMillis();
                    localMedia.setId(time);
                    localMedia.setPath(Constants.LOCALADDRESS + time);
                    imgList.add(filePath);
//                    File file = new File(filePath);
                    exFiles.add(localMedia);
//                    gdList.add(localMedia);
                    fileListReady.add(new File(filePath));
                }
                ylList = exFiles;
                getPermission(imgList);
//                mAdapter.setFileList(fileListReady);
//                mAdapter.setFileListReady(fileListReady);
                mAdapter.setList(exFiles);
//                mAdapter.setExFiles(exFiles);
                mAdapter.notifyDataSetChanged();
            }
        });

        viewModel.beianClickEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(RiskRegisterActivity.this, false, beianEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.beianDate.setText(year + "-" + month + "-" + day);
                        Date date = null;
                        try {
                            date = DateUtils.formatterYmd.parse(year + "-" + month + "-" + day);
                            beianEntity = DateEntity.target(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * 删除本地文件
     */
    private void delete(List<File> fileList) {
        if (fileList.isEmpty()) {
            return;
        }
        for (File file : fileList) {
            if (file.exists()) {
                if (file.delete()) {
                    Log.i("FileDelete", "文件已删除");
                }
            }
        }
        saveFileList.clear();
    }

    /**
     * 获取读写文件权限
     */
    public void getPermission(List<String> filePaths) {
        new RxPermissions(this).request(permissionsGroup).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    DonwloadSaveImg.donwloadImg(RiskRegisterActivity.this, filePaths);
                } else {
                    ToastUtils.showShort("获取权限失败，请在设置中打开相关权限");
                }
            }
        });
    }

    private String getFileListString(List<LocalMedia> fileList) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            stringList.add(fileList.get(i).getRealPath());
        }
        return StringUtils.setTextStrComma(stringList);
    }

    /**
     * 提交隐患
     */
    private void submit(boolean isSave) {
        if (TextUtils.isEmpty(binding.riskCompany.getText().toString())) {
            ToastUtils.showShort("请选择隐患所在单位");
            return;
        }

        if (TextUtils.isEmpty(binding.troubleshootingMan.getText().toString())) {
            ToastUtils.showShort("请选择隐患排查人");
            return;
        }

        if (TextUtils.isEmpty(binding.riskAddress.getText().toString())) {
            ToastUtils.showShort("请选择隐患地点");
            return;
        }

        if (TextUtils.isEmpty(binding.riskDescribe.getText().toString())) {
            ToastUtils.showShort("请填写隐患描述");
            return;
        }

        if (TextUtils.isEmpty(binding.rectificationPersonnel.getText().toString())) {
            ToastUtils.showShort("请选择整改责任人");
            return;
        }

        if (TextUtils.isEmpty(binding.acceptancePersonnel.getText().toString())) {
            ToastUtils.showShort("请选择验收责任人");
            return;
        }

        if (TextUtils.isEmpty(binding.acceptanceDept.getText().toString())) {
            ToastUtils.showShort("请选择验收部门");
            return;
        }


        if (TextUtils.isEmpty(binding.rectificationMeasures.getText().toString())) {
            ToastUtils.showShort("请填写整改措施");
            return;
        }

        if (TextUtils.equals(tag,"1")) {
            if (TextUtils.isEmpty(binding.sourceOfFunds.getText().toString())) {
                ToastUtils.showShort("请输入资金来源");
                return;
            }

            if (TextUtils.isEmpty(binding.governanceCosts.getText().toString())) {
                ToastUtils.showShort("请输入治理费用");
                return;
            }

            if (TextUtils.isEmpty(binding.filingNum.getText().toString())) {
                ToastUtils.showShort("请输入备案编号");
                return;
            }

            if (TextUtils.isEmpty(binding.acceptanceUnit.getText().toString())) {
                ToastUtils.showShort("请输入受理单位");
                return;
            }
        }

        RiskRegisterBean riskRegisterBean = new RiskRegisterBean();
        riskRegisterBean.setTrsource(viewModel.yhlyMap.get(binding.riskSource.getText().toString()));
        riskRegisterBean.setTrcategory(viewModel.yhlbMap.get(binding.riskCategory.getText().toString()));
        if (TextUtils.equals("0", tag)) {
            riskRegisterBean.setTrlevel("YHJB001");
        } else {
            riskRegisterBean.setTrlevel("YHJB002");
        }
        riskRegisterBean.setTrsite(selectDeptId);
        riskRegisterBean.setTrsitename(binding.riskCompany.getText().toString());
        riskRegisterBean.setScregion(binding.riskAddress.getText().toString());
        riskRegisterBean.setTrfoundman(binding.troubleshootingMan.getText().toString());
        riskRegisterBean.setTrfounddate(binding.riskFindDate.getText().toString());
        riskRegisterBean.setTrdescribe(binding.riskDescribe.getText().toString());
        riskRegisterBean.setZgtype(viewModel.zglxMap.get(binding.rectificationType.getText().toString()));
        riskRegisterBean.setZgterm(binding.rectificationDate.getText().toString());
        riskRegisterBean.setZgdutyman(zgzrrId);
//                riskRegisterBean.setZgdutyunit(binding.rectificationDepartment.getText().toString());
        riskRegisterBean.setZgmeasure(binding.rectificationMeasures.getText().toString());
        riskRegisterBean.setYsdutyman(yszrrId);
        riskRegisterBean.setYsdutyunit(binding.acceptanceDept.getText().toString());
        String modelJson = JSON.toJSONString(riskRegisterBean);

        if (isSave) {
            viewModel.saveRegister(modelJson, mAdapter.getFileList());
        } else {
            viewModel.insertRegister(modelJson, mAdapter.getFileList());
        }
    }


    private void showRiskSourcePop() {
        riskSourcePop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(riskSourcePopView)
                .setOutsideTouchable(true)
                .setFocusable(false)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(binding.riskSource.getWidth(), WRAP_CONTENT)
                .create()
                .showAsDropDown(binding.riskSource);
    }

    private void showRiskCategoryPop() {
        riskCategoryPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(riskCategoryPopView)
                .setOutsideTouchable(true)
                .setFocusable(false)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(binding.riskCategory.getWidth(), WRAP_CONTENT)
                .create()
                .showAsDropDown(binding.riskCategory);
    }

//    private void showRiskLevelPop() {
//        if (null == riskLevelPop) {
//            riskLevelPop = new CustomPopWindow.PopupWindowBuilder(this)
//                    .setView(riskLevelPopView)
//                    .setOutsideTouchable(true)
//                    .setFocusable(false)
//                    .size(binding.riskLevel.getWidth(), WRAP_CONTENT)
//                    .create()
//                    .showAsDropDown(binding.riskLevel);
//        } else {
//            riskLevelPop.showAsDropDown(binding.riskLevel);
//        }
//    }

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

    private void showRiskCompanyPopWindow() {
        riskCompanyPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(riskCompanyPopView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(binding.riskCompany.getWidth(), WRAP_CONTENT)
                .create()
                .showAsDropDown(binding.riskCompany);
    }

    private void showZglxPop() {
        zglxPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(zglxPopView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(binding.rectificationType.getWidth(), WRAP_CONTENT)
                .create()
                .showAsDropDown(binding.rectificationType);
    }

    @SuppressLint("CheckResult")
    private void chooseImage() {
        new RxPermissions(this).request(permissionsPhotoGroup).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    if (viewId == R.id.tvTakePhoto) {
                        PictureSelectorManager.openCamera(RiskRegisterActivity.this, MAXSELECTNUM, mAdapter.getData(), mAdapter, new RiskRegisterActivity.MyResultCallback(mAdapter));
                    } else if (viewId == R.id.tvChooseImage) {
//                        ylList.removeAll(gdList);
                        PictureSelectorManager.openAlbum(RiskRegisterActivity.this,
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            for (LocalMedia media : selectList) {
                if (media.getWidth() == 0 || media.getHeight() == 0) {
                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                        media.setWidth(imageExtraInfo.getWidth());
                        media.setHeight(imageExtraInfo.getHeight());
                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(this, media.getPath());
                        media.setWidth(videoExtraInfo.getWidth());
                        media.setHeight(videoExtraInfo.getHeight());
                    }
                }
            }
            mAdapter.setList(selectList);
            mAdapter.notifyDataSetChanged();
        } else if (resultCode == Activity.RESULT_OK && requestCode == TO_MULTI_CODE) {
            String areaName = data.getStringExtra("areaName");
            binding.riskAddress.setText(areaName);
        } else if (requestCode == TO_PERSONNEL_CODE && resultCode == RESULT_OK) {
            binding.rectificationPersonnel.setText(data.getStringExtra("personnelTv"));
//            binding.rectificationDepartment.setText(data.getStringExtra("deptTv"));
            zgzrrId = data.getStringExtra("userId");
        } else if (requestCode == TO_PERSONNEL_CODE_YSR && resultCode == RESULT_OK) {
            binding.acceptancePersonnel.setText(data.getStringExtra("personnelTv"));
            binding.acceptanceDept.setText(data.getStringExtra("deptTv"));
            yszrrId = data.getStringExtra("userId");
        } else if (requestCode == TO_PERSONNEL_CODE_PCR && resultCode == RESULT_OK) {
            binding.troubleshootingMan.setText(data.getStringExtra("personnelTv"));
        }
    }


    /**
     * 增加图片按钮
     */
    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            new RxPermissions(RiskRegisterActivity.this).request(permissionsGroup).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        showUploadImagePopWindow();
                    } else {
                        ToastUtils.showShort("获取权限失败，请在设置中打开相关权限");
                    }
                }
            });
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
                            ylList = selectList;
                            // 例如 LocalMedia 里面返回五种path
                            // 1.media.getPath(); 原图path
                            // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                            // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                            // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                            // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
                            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                            fileList.clear();
                            for (LocalMedia media : selectList) {
                                if (media.getWidth() == 0 || media.getHeight() == 0) {
                                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                                        media.setWidth(imageExtraInfo.getWidth());
                                        media.setHeight(imageExtraInfo.getHeight());
                                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(RiskRegisterActivity.this, media.getPath());
                                        media.setWidth(videoExtraInfo.getWidth());
                                        media.setHeight(videoExtraInfo.getHeight());
                                    }
                                }

                                fileList.add(new File(media.getRealPath()));
                                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
                            }
//                            if (!mAdapter.getExFiles().isEmpty()) {
//                                selectList.addAll(mAdapter.getExFiles());
//                            }

//                            if (!mAdapter.getFileListReady().isEmpty()) {
//                                fileList.addAll(mAdapter.getFileListReady());
//                            }
                            mAdapter.setList(selectList);
                            mAdapter.setFileList(fileList);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (launcherResult != null) {
            launcherResult.unregister();
        }
        delete(saveFileList);
    }
}