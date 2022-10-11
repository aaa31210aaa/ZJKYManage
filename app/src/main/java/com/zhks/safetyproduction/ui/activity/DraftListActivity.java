package com.zhks.safetyproduction.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.DraftAdapter;
import com.zhks.safetyproduction.entity.HiddenDBean;
import com.zhks.safetyproduction.utils.DaoUtilsStore;
import com.zhks.safetyproduction.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import greendao.HiddenDBeanDao;

public class DraftListActivity extends AppCompatActivity implements View.OnClickListener {
    private String type;
    private TextView draftEdit;
    private RecyclerView draftRv;
    private DraftAdapter adapter;
    private List<HiddenDBean> mDatas = new ArrayList<>();
    public boolean isEdit = false; //是否为编辑状态
    private LinearLayout editLin;
    private TextView draftEditAllselect;
    private TextView draftEditCancelselect;
    private TextView draftEditDelete;
    private ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_list);
        initData();
    }

    public void initData() {
        mDatas = DaoUtilsStore.getInstance().getHiddenDaoUtils().queryByQueryBuilder(HiddenDBeanDao.Properties.HiddenId.isNotNull());
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        draftEdit = findViewById(R.id.draft_edit);
        draftEdit.setOnClickListener(this);
        draftRv = findViewById(R.id.draft_rv);
        draftRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DraftAdapter(R.layout.draft_item_layout, mDatas);
        draftRv.setAdapter(adapter);
        editLin = findViewById(R.id.edit_lin);
        draftEditAllselect = findViewById(R.id.draft_edit_allselect);
        draftEditAllselect.setOnClickListener(this);
        draftEditCancelselect = findViewById(R.id.draft_edit_cancelselect);
        draftEditCancelselect.setOnClickListener(this);
        draftEditDelete = findViewById(R.id.draft_edit_delete);
        draftEditDelete.setOnClickListener(this);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(DraftListActivity.this, RiskRegisterActivity.class);
                intent.putExtra("hiddenBean", mDatas.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.draft_edit) {
            if (isEdit) {
                isEdit = false;
                draftEdit.setText("编辑");
                editLin.setVisibility(View.GONE);
            } else {
                isEdit = true;
                draftEdit.setText("取消");
                editLin.setVisibility(View.VISIBLE);
            }
            adapter.setIsEdit(isEdit);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.draft_edit_allselect) {
            //全选
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).setIsCheck(true);
            }
            adapter.setNewData(mDatas);
        } else if (id == R.id.draft_edit_cancelselect) {
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).setIsCheck(false);
            }
            adapter.setNewData(mDatas);
        } else if (id == R.id.draft_edit_delete) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).getIsCheck()) {
                    DaoUtilsStore.getInstance().getHiddenDaoUtils().delete(mDatas.get(i));
                    mDatas.remove(i);
                }
            }
            adapter.notifyDataSetChanged();
        } else if (id == R.id.iv_back) {
            finish();
        }
    }
}