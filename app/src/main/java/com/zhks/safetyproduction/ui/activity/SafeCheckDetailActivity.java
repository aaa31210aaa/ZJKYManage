package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static com.zhks.safetyproduction.constants.Constants.TO_MULTI_CODE;
import static com.zhks.safetyproduction.constants.Constants.TO_PERSONNEL_CODE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CheckDetailRvAdapter;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivitySafeCheckDetailBinding;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CurrentUserBean;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.entity.SafeCheckBean;
import com.zhks.safetyproduction.entity.SafeCheckSubmitBean;
import com.zhks.safetyproduction.entity.SafeCheckTermBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.SafeCheckDetailViewModel;
import com.example.zhouwei.library.CustomPopWindow;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SafeCheckDetailActivity extends BaseActivity<ActivitySafeCheckDetailBinding, SafeCheckDetailViewModel> implements View.OnClickListener {
    private String pageTitle;
    private CustomPopWindow checkTypePop;
    private CustomPopWindow checkRegionPop;
    private CustomPopWindow checkLevelPop;
    private CustomPopWindow checkInspectionPersonnelPop;
    private CustomPopWindow checkInspectionDepartmentPop;
    private CustomPopWindow checkToHiddenPop;
    private CustomPopWindow safeCheckTipsPop;
    private View checkTypePopView;
    private View checkRegionPopView;
    private View checkLevelPopView;
    private View checkInspectionPersonnelPopView;
    private View checkInspectionDepartmentPopView;
    private View checkToHiddenView;
    private View safeCheckTipsPopView;


    private RecyclerView safeCheckTypePopRv;
    private RecyclerView safeCheckRegionPopRv;
    private RecyclerView safeCheckLevelPopRv;
    private RecyclerView safeCheckInspectionPersonnelRv;
    private RecyclerView safeCheckInspectionDepartmentRv;
    private CustomPopAdapter checkTypeAdapter;
    private CustomPopAdapter checkRegionAdapter;
    private CustomPopAdapter checkLevelAdapter;
    private CustomPopAdapter checkInspectionPersonnelAdapter;
    private CustomPopAdapter checkInspectionDepartmentAdapter;
    private List<AccidentDetailBean.DataDTO.JCLXDTO> checkTypeDatas;
    private List<AccidentDetailBean.DataDTO.JCLXDTO> checkRegionDatas;
    private List<AccidentDetailBean.DataDTO.JCJBDTO> checkLevelDatas;
    private List<DeptUserBean.CellsDTO> checkInspectionPersonnelDatas;
    private List<DepartmentBean.CellsDTO.DateDTO> checkInspectionDepartmentDatas;
    private View headView;
    private TextView checkTypeTv;
    private TextView checkRegionTv;
    private TextView checkLevelTv;
    private TextView checkDateTv;
    private TextView checkPersonnelTv;
    private TextView checkDepartmentTv;
    private RecyclerView checkDetailRv;
    private List<SafeCheckTermBean.CellsDTO> mDatas;
    private CheckDetailRvAdapter adapter;
    public int personalPageIndex = 1;
    private String selectDeptId;
    private SmartRefreshLayout refreshLayout;
    private SafeCheckBean.CellsDTO safeBean;
    private List<String> unqualifiedList = new ArrayList<>();
    private List<String> safeCheckTermDatas = new ArrayList<>();
    private DateEntity dateEntity;
    private TextView checkInspectionPersonnelSubmit;
    private TextView checkToHiddenCancel;
    private TextView toYbHidden;
    private TextView toZdHidden;
    private TextView customPopTv;
    private TextView customPopCancel;
    private TextView customPopConfirm;
    private int selectIndex;
    private String selectItemId;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_safe_check_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        Bundle bundle = getIntent().getExtras();
        pageTitle = bundle.getString("pageTitle");
        safeBean = (SafeCheckBean.CellsDTO) bundle.getSerializable("bean");
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initToolBar() {
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("提交");
        viewModel.setTitleText(pageTitle);
    }

    @Override
    public SafeCheckDetailViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(SafeCheckDetailViewModel.class);
    }

    @Override
    public void initData() {
        checkDetailRv = findViewById(R.id.check_detail_rv);
        checkTypePopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        checkRegionPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        checkLevelPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        checkInspectionPersonnelPopView = View.inflate(this, R.layout.all_personnel_layout, null);
        checkInspectionDepartmentPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        safeCheckTipsPopView = View.inflate(this, R.layout.custom_pop_tv, null);
        customPopTv = safeCheckTipsPopView.findViewById(R.id.custom_pop_tv);
        customPopTv.setText("还有没检查完的项目，是否提交");
        customPopCancel = safeCheckTipsPopView.findViewById(R.id.custom_pop_cancel);
        customPopConfirm = safeCheckTipsPopView.findViewById(R.id.custom_pop_confirm);
        headView = View.inflate(this, R.layout.safe_check_detail_rv_headview, null);
        checkToHiddenView = View.inflate(this, R.layout.check_to_hidden_pop, null);
        checkToHiddenCancel = checkToHiddenView.findViewById(R.id.checkToHiddenCancel);
        checkToHiddenCancel.setOnClickListener(this);
        toYbHidden = checkToHiddenView.findViewById(R.id.toYbHidden);
        toZdHidden = checkToHiddenView.findViewById(R.id.toZdHidden);
        toYbHidden.setOnClickListener(this);
        toZdHidden.setOnClickListener(this);
        checkTypeTv = headView.findViewById(R.id.check_type_tv);
        checkTypeTv.setOnClickListener(this);
        checkRegionTv = headView.findViewById(R.id.check_region_tv);
        checkRegionTv.setOnClickListener(this);
        checkLevelTv = headView.findViewById(R.id.check_level_tv);
        checkLevelTv.setOnClickListener(this);
        checkDateTv = headView.findViewById(R.id.check_date_tv);
        checkDateTv.setOnClickListener(this);
        checkPersonnelTv = headView.findViewById(R.id.check_personnel_tv);
        checkPersonnelTv.setOnClickListener(this);
        checkDepartmentTv = headView.findViewById(R.id.check_department_tv);
        checkDepartmentTv.setOnClickListener(this);
        checkDateTv.setText(DateUtils.getCurrentDate());
        dateEntity = DateEntity.today();
        safeCheckTypePopRv = checkTypePopView.findViewById(R.id.spinner_pop_rv);
        safeCheckRegionPopRv = checkRegionPopView.findViewById(R.id.spinner_pop_rv);
        safeCheckLevelPopRv = checkLevelPopView.findViewById(R.id.spinner_pop_rv);
        safeCheckInspectionPersonnelRv = checkInspectionPersonnelPopView.findViewById(R.id.all_personnel_choice_rv);
        checkInspectionPersonnelSubmit = checkInspectionPersonnelPopView.findViewById(R.id.all_personnel_submit);
        checkInspectionPersonnelSubmit.setOnClickListener(this);
        safeCheckInspectionDepartmentRv = checkInspectionDepartmentPopView.findViewById(R.id.spinner_pop_rv);
        refreshLayout = checkInspectionPersonnelPopView.findViewById(R.id.refreshLayout);
        initRefreshLayout();
        checkDetailRv.setLayoutManager(new LinearLayoutManager(this));
        safeCheckTypePopRv.setLayoutManager(new LinearLayoutManager(this));
        safeCheckRegionPopRv.setLayoutManager(new LinearLayoutManager(this));
        safeCheckLevelPopRv.setLayoutManager(new LinearLayoutManager(this));
        safeCheckInspectionPersonnelRv.setLayoutManager(new LinearLayoutManager(this));
        safeCheckInspectionDepartmentRv.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        checkTypeDatas = new ArrayList<>();
        checkRegionDatas = new ArrayList<>();
        checkLevelDatas = new ArrayList<>();
        checkInspectionPersonnelDatas = new ArrayList<>();
        checkInspectionDepartmentDatas = new ArrayList<>();
        adapter = new CheckDetailRvAdapter(R.layout.safe_check_detail_item_layout, mDatas);
        checkTypeAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, checkTypeDatas);
        checkRegionAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, checkRegionDatas);
        checkLevelAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, checkLevelDatas);
        checkInspectionPersonnelAdapter = new CustomPopAdapter(R.layout.multi_choice_item_layout, checkInspectionPersonnelDatas);
        checkInspectionDepartmentAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, checkInspectionDepartmentDatas);
        safeCheckTypePopRv.setAdapter(checkTypeAdapter);
        safeCheckRegionPopRv.setAdapter(checkRegionAdapter);
        safeCheckLevelPopRv.setAdapter(checkLevelAdapter);
        safeCheckInspectionPersonnelRv.setAdapter(checkInspectionPersonnelAdapter);
        safeCheckInspectionDepartmentRv.setAdapter(checkInspectionDepartmentAdapter);
        adapter.addHeaderView(headView);
        checkDetailRv.setAdapter(adapter);

        adapter.setSafeCheckSwitchCall(new CheckDetailRvAdapter.SafeCheckSwitchCall() {
            @Override
            public void SafeCheckSwitchListener(SafeCheckTermBean.CellsDTO cellsDTO, boolean isCheck, int position) {
                if (!isCheck) {
                    selectItemId = cellsDTO.getItemid();
//                    safeCheckTermDatas.add(selectItemId);
                    //弹出提示  是否跳转到隐患填写
                    showToRiskRegister();
                    selectIndex = position;
                }
            }
        });
        getData();
    }

    private void getData() {
        checkRegionAdapter.setNewData(checkRegionDatas);
        checkLevelAdapter.setNewData(checkLevelDatas);
        checkInspectionPersonnelAdapter.setNewData(checkInspectionPersonnelDatas);
        checkInspectionDepartmentAdapter.setNewData(checkInspectionDepartmentDatas);

        viewModel.getDeptUser(String.valueOf(personalPageIndex), refreshLayout);

        checkTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                checkTypeTv.setText(checkTypeDatas.get(position).getParamname());
                if (null != checkTypePop) {
                    checkTypePop.dissmiss();
                }
            }
        });

        checkRegionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                checkRegionTv.setText("");
                if (null != checkRegionPop) {
                    checkRegionPop.dissmiss();
                }
            }
        });

        checkLevelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                checkLevelTv.setText("");
                if (null != checkLevelPop) {
                    checkLevelPop.dissmiss();
                }
            }
        });

        checkInspectionPersonnelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                checkPersonnelTv.setText("");
                if (null != checkInspectionPersonnelPop) {
                    checkInspectionPersonnelPop.dissmiss();
                }
            }
        });

        checkInspectionDepartmentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                checkDepartmentTv.setText(checkInspectionDepartmentDatas.get(position).getDtname());
                selectDeptId = checkInspectionDepartmentDatas.get(position).getDtid();
                checkInspectionPersonnelDatas.clear();
                personalPageIndex = 1;
                if (null != checkInspectionDepartmentPop) {
                    checkInspectionDepartmentPop.dissmiss();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getSafeCheckTerm(safeBean.getScmid());
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableScrollContentWhenLoaded(true);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout mRefreshLayout) {
                personalPageIndex++;
                viewModel.getDeptUser(String.valueOf(personalPageIndex), refreshLayout);
            }
        });
    }


    @Override
    public void initViewObservable() {
        viewModel.dictionariesEvent.observe(this, new Observer<List<AccidentDetailBean.DataDTO.JCLXDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.JCLXDTO> jclxdtos) {
                checkTypeDatas = jclxdtos;
                checkTypeAdapter.setNewData(checkTypeDatas);
                checkTypeTv.setText(checkTypeDatas.get(0).getParamname());
            }
        });

        viewModel.jcjbEvent.observe(this, new Observer<List<AccidentDetailBean.DataDTO.JCJBDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.JCJBDTO> jcjbdtos) {
                checkLevelDatas = jcjbdtos;
                checkLevelAdapter.setNewData(checkLevelDatas);
                checkLevelTv.setText(checkLevelDatas.get(0).getParamname());
            }
        });

//        viewModel.departMentEvent.observe(this, new Observer<List<DepartmentBean.CellsDTO>>() {
//            @Override
//            public void onChanged(List<DepartmentBean.CellsDTO> cellsDTOS) {
//                viewModel.getDeptUser(cellsDTOS.get(0).getDate().get(0).getDtid(), "1", refreshLayout);
//                checkInspectionDepartmentDatas = cellsDTOS.get(0).getDate();
//                checkInspectionDepartmentAdapter.setNewData(checkInspectionDepartmentDatas);
//                checkDepartmentTv.setText(checkInspectionDepartmentDatas.get(0).getDtname());
//            }
//        });

        viewModel.deptUserEvent.observe(this, new Observer<List<DeptUserBean.CellsDTO>>() {
            @Override
            public void onChanged(List<DeptUserBean.CellsDTO> cellsDTOS) {
                checkInspectionPersonnelDatas.addAll(cellsDTOS);
                checkInspectionPersonnelAdapter.setNewData(checkInspectionPersonnelDatas);
            }
        });

        viewModel.safeCheckEvent.observe(this, new Observer<List<SafeCheckTermBean.CellsDTO>>() {
            @Override
            public void onChanged(List<SafeCheckTermBean.CellsDTO> cellsDTOS) {
                mDatas = cellsDTOS;
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).setCheck(true);
                }
                adapter.setNewData(mDatas);
            }
        });

        viewModel.rightEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(checkRegionTv.getText().toString())) {
                    ToastUtils.showShort("请选择检查区域");
                    return;
                }

                if (TextUtils.isEmpty(checkPersonnelTv.getText().toString())) {
                    ToastUtils.showShort("请选择检查人员");
                    return;
                }

                //if (判断数据里还有没有状态为未处理的检查项目) 则新增一个弹窗，还有没检查完的项目，是否提交
//                showTipsPop();


                SafeCheckSubmitBean submitBean = new SafeCheckSubmitBean();
                submitBean.setScmid(safeBean.getScmid());
                submitBean.setScmidname(safeBean.getScmname());
                submitBean.setSctype(checkTypeTv.getText().toString());
                submitBean.setSclevel(checkLevelTv.getText().toString());
                submitBean.setScregion(checkRegionTv.getText().toString());
                submitBean.setDeptid(selectDeptId);
                submitBean.setScdate(checkDateTv.getText().toString());
                submitBean.setDeptname(checkDepartmentTv.getText().toString());
                submitBean.setUsername(checkPersonnelTv.getText().toString());
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < safeCheckTermDatas.size(); i++) {
                    if (i != safeCheckTermDatas.size() - 1) {
                        builder.append(safeCheckTermDatas.get(i) + ",");
                    } else {
                        builder.append(safeCheckTermDatas.get(i));
                    }
                }
                submitBean.setItemid(builder.toString());
                String modelJson = JSON.toJSONString(submitBean);
                viewModel.insertCheckreg(modelJson);
            }
        });
    }

    /**
     * 检查类型下拉弹窗
     */
    public void showCheckType() {
        if (null == checkTypePop) {
            checkTypePop = new CustomPopWindow.PopupWindowBuilder(SafeCheckDetailActivity.this)
                    .setView(checkTypePopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(checkTypeTv.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(checkTypeTv);
        } else {
            checkTypePop.showAsDropDown(checkTypeTv);
        }
    }


    /**
     * 检查区域下拉弹窗
     */
    public void showCheckRegionPop() {
        if (null == checkRegionPop) {
            checkRegionPop = new CustomPopWindow.PopupWindowBuilder(SafeCheckDetailActivity.this)
                    .setView(checkRegionPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(checkTypeTv.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(checkRegionTv);
        } else {
            checkRegionPop.showAsDropDown(checkRegionTv);
        }
    }

    /**
     * 检查级别下拉弹窗
     */
    public void showCheckLevelPop() {
        if (null == checkLevelPop) {
            checkLevelPop = new CustomPopWindow.PopupWindowBuilder(SafeCheckDetailActivity.this)
                    .setView(checkLevelPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(checkTypeTv.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(checkLevelTv);
        } else {
            checkLevelPop.showAsDropDown(checkLevelTv);
        }
    }


    /**
     * 参检人员
     */
    public void showCheckInspectionPersonnePop() {
        if (null == checkInspectionPersonnelPop) {
            checkInspectionPersonnelPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(checkInspectionPersonnelPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), DensityUtil.getScreenHeight(this) / 2)
                    .create()
                    .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            checkInspectionPersonnelPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    private void showParticipantsPop() {
        if (null == checkInspectionPersonnelPop) {
            checkInspectionPersonnelPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(checkInspectionPersonnelPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), DensityUtil.getScreenHeight(this) / 2)
                    .create()
                    .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            checkInspectionPersonnelPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 参检部门
     */
    public void showCheckInspectionDepartmentPop() {
        if (null == checkInspectionDepartmentPop) {
            checkInspectionDepartmentPop = new CustomPopWindow.PopupWindowBuilder(SafeCheckDetailActivity.this)
                    .setView(checkInspectionDepartmentPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(checkTypeTv.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(checkDepartmentTv);
        } else {
            checkInspectionDepartmentPop.showAsDropDown(checkDepartmentTv);
        }
    }

    /**
     * 检查项没有处理完的提示框
     */
    private void showTipsPop() {
        safeCheckTipsPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(safeCheckTipsPopView)
                .setOutsideTouchable(true)
                .setFocusable(false)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), WRAP_CONTENT)
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.check_type_tv) {
            showCheckType();
        } else if (id == R.id.check_region_tv) {
//            showCheckRegionPop();
            Intent intent = new Intent(this, MultistageActivity.class);
            intent.putExtra(Constants.MULTI_TITLE, "区域选择");
            startActivityForResult(intent, TO_MULTI_CODE);
        } else if (id == R.id.check_level_tv) {
            showCheckLevelPop();
        } else if (id == R.id.check_date_tv) {
            DatePickerManager.onYearMonthDay(SafeCheckDetailActivity.this, false, dateEntity, new OnDatePickedListener() {
                @Override
                public void onDatePicked(int year, int month, int day) {
                    checkDateTv.setText(year + "-" + month + "-" + day);
                }
            });
        } else if (id == R.id.check_personnel_tv) {
//            showCheckInspectionPersonnePop();
            Intent intent = new Intent(SafeCheckDetailActivity.this, PersonnelQueryActivity.class);
            startActivityForResult(intent, TO_PERSONNEL_CODE);
        } else if (id == R.id.check_department_tv) {
//            showCheckInspectionDepartmentPop();
        } else if (id == R.id.all_personnel_submit) {
            List<DeptUserBean.CellsDTO> list = new ArrayList<>();
            for (int i = 0; i < checkInspectionPersonnelDatas.size(); i++) {
                if (checkInspectionPersonnelDatas.get(i).isCheck()) {
                    list.add(checkInspectionPersonnelDatas.get(i));
                }
            }
            setChoiceText(list, checkInspectionPersonnelPop);
        } else if (id == R.id.checkToHiddenCancel) {
            checkToHiddenPop.dissmiss();
            adapter.notifyItemChanged(selectIndex);
//            safeCheckTermDatas.remove(selectItemId);
        } else if (id == R.id.toYbHidden) {
            checkToHiddenPop.dissmiss();
            Intent intent = new Intent(this, RiskRegisterActivity.class);
            intent.putExtra("tag", "0");
            intent.putExtra("checkUnqualified", "1");
            intent.putExtra("checkTypeTv", checkTypeTv.getText().toString()); //检查类型
            intent.putExtra("checkRegionTv", checkRegionTv.getText().toString()); //检查区域
            startActivityForResult(intent, Constants.YHTX_CODE);
        } else if (id == R.id.toZdHidden) {
            checkToHiddenPop.dissmiss();
            Intent intent = new Intent(this, RiskRegisterActivity.class);
            intent.putExtra("tag", "1");
            intent.putExtra("checkUnqualified", "1");
            intent.putExtra("checkTypeTv", checkTypeTv.getText().toString()); //检查类型
            intent.putExtra("checkRegionTv", checkRegionTv.getText().toString()); //检查区域
            startActivityForResult(intent, Constants.YHTX_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_MULTI_CODE) {
            String areaName = data.getStringExtra("areaName");
            checkRegionTv.setText(areaName);
        } else if (requestCode == TO_PERSONNEL_CODE && resultCode == RESULT_OK) {
            checkPersonnelTv.setText(data.getStringExtra("personnelTv"));
            checkDepartmentTv.setText(data.getStringExtra("deptTv"));
        } else if (null != data && requestCode == Constants.YHTX_CODE) {
            mDatas.get(selectIndex).setClickble(false);
            mDatas.get(selectIndex).setCheck(false);
            safeCheckTermDatas.add(mDatas.get(selectIndex).getItemid());
            adapter.setNewData(mDatas);
        }
    }


    private void setChoiceText(List<DeptUserBean.CellsDTO> list, CustomPopWindow popWindow) {
        StringBuilder builder = new StringBuilder();
        StringBuilder deptBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                builder.append(list.get(i).getUsername() + ",");
                deptBuilder.append(list.get(i).getDeptname() + ",");
            } else {
                builder.append(list.get(i).getUsername());
                deptBuilder.append(list.get(i).getDeptname());
            }
        }
        checkPersonnelTv.setText(builder);
        checkDepartmentTv.setText(deptBuilder);

        if (null != popWindow) {
            popWindow.dissmiss();
        }
    }

    private void showToRiskRegister() {
        checkToHiddenPop = new CustomPopWindow.PopupWindowBuilder(SafeCheckDetailActivity.this)
                .setView(checkToHiddenView)
                .setOutsideTouchable(false)
                .setFocusable(false)
                .enableBackgroundDark(true)
                .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), WRAP_CONTENT)
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


}