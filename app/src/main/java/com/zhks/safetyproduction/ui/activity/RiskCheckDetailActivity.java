package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.zhks.safetyproduction.constants.Constants.TO_MULTI_CODE;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.adapter.RiskCheckDetailAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityRiskCheckDetailBinding;
import com.zhks.safetyproduction.entity.DjRecordBean;
import com.zhks.safetyproduction.entity.MiningAreaBean;
import com.zhks.safetyproduction.entity.PositionInfoBean;
import com.zhks.safetyproduction.entity.RiskCheckItemBean;
import com.zhks.safetyproduction.entity.RiskCheckRecordsBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.utils.StringUtils;
import com.zhks.safetyproduction.viewmodel.RiskCheckDetailViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 管理岗位
 */
public class RiskCheckDetailActivity extends BaseActivity<ActivityRiskCheckDetailBinding, RiskCheckDetailViewModel> implements View.OnClickListener {
    private DateEntity dateEntity;
    private RiskCheckDetailAdapter adapter;
    private List<RiskCheckItemBean.DataDTO> mDatas;
    private RecyclerView checkDetailRv;
    private List<String> safeCheckTermDatas = new ArrayList<>();
    private CustomPopWindow checkToHiddenPop;
    private View checkToHiddenView;
    private TextView checkToHiddenCancel;
    private TextView toYbHidden;
    private TextView toZdHidden;
    private Bundle bundle;
    private String id;
    private String rlid;
    private String checkman;
    private int selectIndex;
    private RiskCheckRecordsBean.DataDTO dataDTO;
    private String type; //岗位类型

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_risk_check_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        bundle = getIntent().getExtras();
        if (null != bundle) {
            dataDTO = (RiskCheckRecordsBean.DataDTO) bundle.getSerializable("itemBean");
        }
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("岗位风险巡检");
    }

    @Override
    public RiskCheckDetailViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(RiskCheckDetailViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        dateEntity = DateEntity.today();
        binding.djDate.setText(DateUtils.getCurrentDate());
        mDatas = new ArrayList<>();
        checkDetailRv = findViewById(R.id.recyclerView);
        checkDetailRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RiskCheckDetailAdapter(R.layout.risk_check_detail_item_layout, mDatas);
        adapter.bindToRecyclerView(checkDetailRv);
        checkDetailRv.setAdapter(adapter);
        checkToHiddenView = View.inflate(this, R.layout.check_to_hidden_pop, null);
        checkToHiddenCancel = checkToHiddenView.findViewById(R.id.checkToHiddenCancel);
        checkToHiddenCancel.setOnClickListener(this);
        toYbHidden = checkToHiddenView.findViewById(R.id.toYbHidden);
        toZdHidden = checkToHiddenView.findViewById(R.id.toZdHidden);
        toYbHidden.setOnClickListener(this);
        toZdHidden.setOnClickListener(this);

        if (null != bundle) {
            if (TextUtils.equals("0", bundle.getString("index"))) {
                binding.username.setText(bundle.getString("userName"));
                binding.deptname.setText(bundle.getString("deptname"));
                binding.evaname.setText(bundle.getString("evaname"));
                binding.fxdname.setText(bundle.getString("fxdname"));
                rlid = bundle.getString("evaid");
                type = bundle.getString("type");
            } else {
                if (null != dataDTO) {
                    id = dataDTO.getId();
                    checkman = dataDTO.getCheckman();
                    binding.username.setText(dataDTO.getCheckmanname());
                    binding.deptname.setText(dataDTO.getDeptname());
                    binding.evaname.setText(dataDTO.getEvaname());
                    binding.fxdname.setText(dataDTO.getFxdname());
                    binding.djAddress.setText(dataDTO.getLocationname());
                    binding.djDate.setText(dataDTO.getCheckdate());
                    type = dataDTO.getType();
                }
            }
            viewModel.getItemList(bundle.getString("evaid"));
        } else {
            viewModel.getJobtaskByUserId();
        }

        adapter.setRiskDetailSwitchCall(new RiskCheckDetailAdapter.RiskDetailSwitchCall() {
            @Override
            public void RiskDetailSwitchListener(RiskCheckItemBean.DataDTO dataDTO, boolean isCheck, int position) {
                if (!isCheck && !mDatas.get(position).isCheck()) {
                    //弹出提示  是否跳转到隐患填写
                    selectIndex = position - 1;
                    showToRiskRegister();
                }
            }
        });

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.djAddressEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(RiskCheckDetailActivity.this, MultistageActivity.class);
                intent.putExtra(Constants.MULTI_TITLE, "点检地点");
                startActivityForResult(intent, TO_MULTI_CODE);
            }
        });

        viewModel.djDateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(RiskCheckDetailActivity.this, true, dateEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.djDate.setText(year + "-" + month + "-" + day);
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

        viewModel.jobtaskEvent.observe(this, new Observer<List<PositionInfoBean.DataDTO>>() {
            @Override
            public void onChanged(List<PositionInfoBean.DataDTO> dataDTOS) {
                binding.username.setText(dataDTOS.get(0).getUserName());
                binding.evaname.setText(dataDTOS.get(0).getEvaname());
                binding.deptname.setText(dataDTOS.get(0).getDeptname());
                binding.fxdname.setText(dataDTOS.get(0).getFxdname());
                rlid = dataDTOS.get(0).getEvaid();
                type = dataDTOS.get(0).getType();
                viewModel.getItemList(dataDTOS.get(0).getEvaid());
            }
        });

        viewModel.itemListEvent.observe(this, new Observer<List<RiskCheckItemBean.DataDTO>>() {
            @Override
            public void onChanged(List<RiskCheckItemBean.DataDTO> dataDTOS) {
                mDatas = dataDTOS;
                adapter.setNewData(mDatas);
            }
        });

        /**
         * 保存
         */
        viewModel.saveEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(binding.djAddress.getText().toString())) {
                    ToastUtils.showShort("请选择点检地点");
                    return;
                }
                DjRecordBean bean = new DjRecordBean();
                bean.setId(id);
                bean.setRlid(rlid);
                bean.setCheckdate(binding.djDate.getText().toString());
                bean.setCheckman(PersonInfoManager.getInstance().getUserId());
                bean.setCheckmanname(PersonInfoManager.getInstance().getUserName());
                bean.setLocationname(binding.djAddress.getText().toString());
                bean.setState("0");
                bean.setItemid(StringUtils.setTextStrComma(safeCheckTermDatas));
                String modelJson = JSON.toJSONString(bean);
                if (TextUtils.equals("1", type)) {
                    viewModel.saveManage(modelJson);
                } else {
                    viewModel.saveWork(modelJson);
                }

            }
        });

        /**
         * 提交
         */
        viewModel.submitEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(binding.djAddress.getText().toString())) {
                    ToastUtils.showShort("请选择点检地点");
                    return;
                }
                DjRecordBean bean = new DjRecordBean();
                bean.setId(id);
                bean.setRlid(rlid);
                bean.setCheckdate(binding.djDate.getText().toString());
                bean.setCheckman(PersonInfoManager.getInstance().getUserId());
                bean.setCheckmanname(PersonInfoManager.getInstance().getUserName());
                bean.setLocationname(binding.djAddress.getText().toString());
                bean.setState("1");
                bean.setItemid(StringUtils.setTextStrComma(safeCheckTermDatas));
                String modelJson = JSON.toJSONString(bean);
                if (TextUtils.equals("1", type)) {
                    viewModel.saveManage(modelJson);
                } else {
                    viewModel.saveWork(modelJson);
                }
            }
        });

        viewModel.saveManageEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                setResult(RESULT_OK);
                finish();
            }
        });

        viewModel.saveWorksEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TO_MULTI_CODE) {
                String areaName = data.getStringExtra("areaName");
                binding.djAddress.setText(areaName);
            } else if (requestCode == Constants.YHTX_CODE) {
                mDatas.get(selectIndex).setCheck(true);
                mDatas.get(selectIndex).setClickble(false);
                safeCheckTermDatas.add(mDatas.get(selectIndex).getRlid());
                adapter.setNewData(mDatas);
            }
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (null != adapter) {
//            adapter.setNewData(mDatas);
//        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.checkToHiddenCancel) {
            checkToHiddenPop.dissmiss();
            adapter.notifyItemChanged(selectIndex);
        } else if (id == R.id.toYbHidden) {
            checkToHiddenPop.dissmiss();
             //检查区域
            Intent intent = new Intent(this, RiskRegisterActivity.class);
            intent.putExtra("tag", "0");
            intent.putExtra("checkUnqualified", "1");
            intent.putExtra("checkTypeTv", "自查"); //检查类型
            intent.putExtra("checkRegionTv", binding.djAddress.getText().toString());
            startActivityForResult(intent, Constants.YHTX_CODE);
        } else if (id == R.id.toZdHidden) {
            checkToHiddenPop.dissmiss();
            Intent intent = new Intent(this, RiskRegisterActivity.class);
            intent.putExtra("tag", "1");
            intent.putExtra("checkUnqualified", "1");
            intent.putExtra("checkTypeTv", "自查"); //检查类型
            intent.putExtra("checkRegionTv", binding.djAddress.getText().toString());
            startActivityForResult(intent, Constants.YHTX_CODE);
        }
    }


    private void showToRiskRegister() {
        checkToHiddenPop = new CustomPopWindow.PopupWindowBuilder(RiskCheckDetailActivity.this)
                .setView(checkToHiddenView)
                .setOutsideTouchable(false)
                .setFocusable(false)
                .enableBackgroundDark(true)
                .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), WRAP_CONTENT)
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

}