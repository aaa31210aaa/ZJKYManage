package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static com.zhks.safetyproduction.constants.Constants.TO_PERSONNEL_CODE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityAcceptConfirmBinding;
import com.zhks.safetyproduction.entity.AcceptConfirmBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.AcceptConfirmViewModel;
import com.zhks.safetyproduction.viewmodel.HomeItemCommonViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class AcceptConfirmActivity extends BaseActivity<ActivityAcceptConfirmBinding, AcceptConfirmViewModel> {
    private CustomPopWindow popWindow;
    private View popWindowView;
    private RecyclerView recyclerView;
    private List<String> mDatas = new ArrayList<>();
    private CustomPopAdapter adapter;
    private Map<String, String> map = new HashMap<>();
    private String trid;
    private DateEntity dateEntity;
    private String xhqrrId;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_accept_confirm;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        trid = getIntent().getStringExtra("trid");
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("隐患验收");
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("提交");
    }

    @Override
    public AcceptConfirmViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(AcceptConfirmViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        mDatas.add("合格");
        mDatas.add("不合格");
        map.put(mDatas.get(0), "SFHG001");
        map.put(mDatas.get(1), "SFHG002");
        binding.sfhg.setText(mDatas.get(0));
        dateEntity = DateEntity.today();
        binding.acceptDate.setText(DateUtils.getCurrentDate());
        popWindowView = View.inflate(this, R.layout.spinner_pop_layout, null);
        recyclerView = popWindowView.findViewById(R.id.spinner_pop_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, mDatas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.sfhgEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showPopWindow();
            }
        });

        viewModel.acceptDateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(AcceptConfirmActivity.this, true, dateEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.acceptDate.setText(year + "-" + month + "-" + day);
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

        viewModel.xhqrrEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(AcceptConfirmActivity.this, PersonnelQueryActivity.class);
                startActivityForResult(intent, TO_PERSONNEL_CODE);
            }
        });

        viewModel.rightEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(binding.acceptParticipants.getText().toString())) {
                    ToastUtils.showShort("请填写验收参与人");
                    return;
                }

                if (TextUtils.isEmpty(binding.xhqrr.getText().toString())) {
                    ToastUtils.showShort("请填写销号确认人");
                    return;
                }
                AcceptConfirmBean bean = new AcceptConfirmBean();
                bean.setUserid(PersonInfoManager.getInstance().getUserId());
                bean.setTrid(trid);
                bean.setIsqualified(map.get(binding.sfhg.getText().toString()));
                bean.setTaattendman(binding.acceptParticipants.getText().toString());
                bean.setTaview(binding.acceptOpinion.getText().toString());
                bean.setTadate(binding.acceptDate.getText().toString());
                bean.setTaconfirmman(xhqrrId);
                String modelJson = JSON.toJSONString(bean);
                viewModel.insertTroubleaccept(modelJson);
            }
        });

        viewModel.troubleAcceptEvenet.observe(this, new Observer() {
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
        if (requestCode == TO_PERSONNEL_CODE && resultCode == RESULT_OK) {
            binding.xhqrr.setText(data.getStringExtra("personnelTv"));
            xhqrrId = data.getStringExtra("userId");
        }
    }

    private void showPopWindow() {
        if (null == popWindow) {
            popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(popWindowView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.sfhg.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.sfhg);
        } else {
            popWindow.showAsDropDown(binding.sfhg);
        }
    }
}