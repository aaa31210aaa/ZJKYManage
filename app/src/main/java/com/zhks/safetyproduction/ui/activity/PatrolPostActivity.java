package com.zhks.safetyproduction.ui.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityPatrolPostBinding;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.PatrolPostViewModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;

public class PatrolPostActivity extends BaseActivity<ActivityPatrolPostBinding, PatrolPostViewModel> {
    private List<DepartmentBean.CellsDTO.DateDTO> deptDatas = new ArrayList<>();
    private CustomPopAdapter deptAdapter;
    private RecyclerView deptRv;
    private CustomPopWindow deptPopWindow;
    private View deptPopView;

    private List<String> gwTypeDatas = new ArrayList<>();
    private CustomPopAdapter gwTypeAdapter;
    private RecyclerView gwTypeRv;
    private CustomPopWindow gwTypePopWindow;
    private View gwTypePopView;
    private String deptSelectId;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_patrol_post;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("巡查岗位选择");
    }

    @Override
    public PatrolPostViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(PatrolPostViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        deptPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        deptAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, deptDatas);
        deptRv = deptPopView.findViewById(R.id.spinner_pop_rv);
        deptRv.setLayoutManager(new LinearLayoutManager(this));
        deptRv.setAdapter(deptAdapter);
        gwTypeDatas.add("作业岗");
        gwTypeDatas.add("管理岗");
        gwTypePopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        gwTypeAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, gwTypeDatas);
        gwTypeRv = gwTypePopView.findViewById(R.id.spinner_pop_rv);
        gwTypeRv.setLayoutManager(new LinearLayoutManager(this));
        gwTypeRv.setAdapter(gwTypeAdapter);
        binding.jobType.setText(gwTypeDatas.get(0));
        deptAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String evatype = binding.jobType.getText().toString().equals("作业岗") ? Constants.ZYGW_CODE : Constants.GLGW_CODE;
                deptSelectId = deptDatas.get(position).getDtid();
                binding.deptTv.setText(deptDatas.get(position).getDtname());
                if (null != deptPopWindow) {
                    deptPopWindow.dissmiss();
                }
                viewModel.getJobtaskByDept(evatype, deptSelectId);
            }
        });

        gwTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.jobType.setText(gwTypeDatas.get(position));
                String evatype = binding.jobType.getText().toString().equals("作业岗") ? Constants.ZYGW_CODE : Constants.GLGW_CODE;
                if (null != gwTypePopWindow) {
                    gwTypePopWindow.dissmiss();
                }
                viewModel.getJobtaskByDept(evatype, deptSelectId);
            }
        });
        viewModel.getDept();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.deptEvent.observe(this, new Observer<List<DepartmentBean.CellsDTO.DateDTO>>() {
            @Override
            public void onChanged(List<DepartmentBean.CellsDTO.DateDTO> dataDTOS) {
                deptDatas = dataDTOS;
                binding.deptTv.setText(deptDatas.get(0).getDtname());
                viewModel.getJobtaskByDept(binding.jobType.getText().toString(), deptDatas.get(0).getDtid());
                deptSelectId = deptDatas.get(0).getDtid();
                deptAdapter.setNewData(deptDatas);
            }
        });

        viewModel.deptCommandEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showDeptPopWindow();
            }
        });

        viewModel.jobCommandEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showJobTypePopWindow();
            }
        });
    }

    private void showDeptPopWindow() {
        //创建并显示popWindow
        deptPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(deptPopView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(binding.deptTv.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        if (!deptPopWindow.getPopupWindow().isShowing()) {
            deptPopWindow.getPopupWindow().showAsDropDown(binding.deptTv);
        }
    }

    private void showJobTypePopWindow() {
        //创建并显示popWindow
        gwTypePopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(gwTypePopView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(binding.jobType.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        if (!gwTypePopWindow.getPopupWindow().isShowing()) {
            gwTypePopWindow.getPopupWindow().showAsDropDown(binding.jobType);
        }
    }
}