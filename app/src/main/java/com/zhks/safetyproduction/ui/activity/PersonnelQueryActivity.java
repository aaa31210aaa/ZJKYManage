package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityPersonnelQueryBinding;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.PersonnelQueryViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseActivity;

public class PersonnelQueryActivity extends BaseActivity<ActivityPersonnelQueryBinding, PersonnelQueryViewModel> {
    private int personalPageIndex = 1;
    private CustomPopWindow deptPop;
    private View deptPopView;
    private List<DepartmentBean.CellsDTO.DateDTO> departmentBeanList = new ArrayList<>();
    private Map<String, String> deptMap = new HashMap<>();
    private RecyclerView deptRv;
    private CustomPopAdapter deptAdapter;
    private String deptName = "";

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_personnel_query;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        deptName = getIntent().getStringExtra("deptName");
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("人员选择");
    }

    @Override
    public PersonnelQueryViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(PersonnelQueryViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setContext(this);
        deptPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        deptRv = deptPopView.findViewById(R.id.spinner_pop_rv);
        deptRv.setLayoutManager(new LinearLayoutManager(this));
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        binding.refreshLayout.setEnableScrollContentWhenLoaded(true);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                personalPageIndex = 1;
                viewModel.getPersonnelData(deptMap.get(binding.deptTv.getText().toString()),
                        String.valueOf(personalPageIndex), binding.refreshLayout, false, binding.deptTv.getText().toString());
            }
        });

        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout mRefreshLayout) {
                personalPageIndex++;
                viewModel.getPersonnelData(deptMap.get(binding.deptTv.getText().toString()), String.valueOf(personalPageIndex),
                        binding.refreshLayout, true, binding.deptTv.getText().toString());
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.deptEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showDeptPop();
            }
        });

        viewModel.departDataEvent.observe(this, new Observer<List<DepartmentBean.CellsDTO.DateDTO>>() {
            @Override
            public void onChanged(List<DepartmentBean.CellsDTO.DateDTO> cellsDTOS) {
                departmentBeanList = cellsDTOS;
                DepartmentBean.CellsDTO.DateDTO everyBody = new DepartmentBean.CellsDTO.DateDTO();
                everyBody.setDtid("");
                everyBody.setDtname("所有人");
                departmentBeanList.add(0, everyBody);
                deptAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, departmentBeanList);
                deptRv.setAdapter(deptAdapter);
                deptAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        personalPageIndex = 1;
                        binding.deptTv.setText(departmentBeanList.get(position).getDtname());
                        if (null != deptPop) {
                            deptPop.dissmiss();
                        }
                        viewModel.getPersonnelData(deptMap.get(binding.deptTv.getText().toString()), "1",
                                binding.refreshLayout, false, binding.deptTv.getText().toString());
                    }
                });
                for (int i = 0; i < cellsDTOS.size(); i++) {
                    deptMap.put(cellsDTOS.get(i).getDtname(), cellsDTOS.get(i).getDtid());
                }
                if (TextUtils.isEmpty(deptName)) {
                    binding.deptTv.setText(cellsDTOS.get(0).getDtname());
                } else {
                    for (int j = 0; j < cellsDTOS.size(); j++) {
                        if (TextUtils.equals(deptName, cellsDTOS.get(j).getDtname())) {
                            binding.deptTv.setText(cellsDTOS.get(j).getDtname());
                        }
                    }
                }

                viewModel.getPersonnelData(deptMap.get(binding.deptTv.getText().toString()), "1",
                        binding.refreshLayout, false, binding.deptTv.getText().toString());
            }
        });
    }

    private void showDeptPop() {
        deptPop = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(deptPopView)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setFocusable(false)
                .size(binding.deptCe.getWidth(), WRAP_CONTENT)
                .create()
                .showAsDropDown(binding.deptCe);
    }


}