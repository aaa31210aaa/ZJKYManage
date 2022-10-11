package com.zhks.safetyproduction.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.IconTreeItemHolder;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityMultistageBinding;
import com.zhks.safetyproduction.entity.MultistageBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.LicenceCheckViewModel;
import com.zhks.safetyproduction.viewmodel.MultistageViewModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MultistageActivity extends BaseActivity<ActivityMultistageBinding, MultistageViewModel> {
    private RelativeLayout container;
    private AndroidTreeView tView;
    private int counter = 0;
    private String title;
    private TreeNode currentTreeNode;
    List<MultistageBean.DataDTO.CheckAreasDTO> rootList = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_multistage;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText(getIntent().getStringExtra(Constants.MULTI_TITLE));
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public MultistageViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(MultistageViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.getAreaTree("0");
        container = findViewById(R.id.container);
        //根节点
        currentTreeNode = TreeNode.root();
    }

    private void fillDownloadsFolder(TreeNode node) {
//        TreeNode downloads = new TreeNode(new IconTreeItemHolder.IconTreeItem("Downloads" + (counter++)));
//        node.addChild(downloads);
//        if (counter < 5) {
//            fillDownloadsFolder(downloads);
//        }
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            viewModel.getAreaTree(item.multistageBean.getId());
            if (TextUtils.equals("false", item.multistageBean.getIsParent())) {
                Intent intent = new Intent();
                intent.putExtra("areaName", item.multistageBean.getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    };

    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            Intent intent = new Intent();
            intent.putExtra("areaName", item.multistageBean.getName());
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
    };

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.resultDatas.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.size(); i++) {
                    String jsonStr = JSON.toJSONString(jsonArray.getJSONObject(i));
                    MultistageBean.DataDTO.CheckAreasDTO bean = JSON.parseObject(jsonStr, MultistageBean.DataDTO.CheckAreasDTO.class);
                    rootList.add(bean);
                    TreeNode treeNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(bean));
                    if (TextUtils.equals("true", bean.getIsParent())) {
                        parse(jsonArray.getJSONObject(i), treeNode);
                    }
                    currentTreeNode.addChildren(treeNode);
                }

                if (null == tView) {
                    tView = new AndroidTreeView(MultistageActivity.this, currentTreeNode);
                    tView.setDefaultAnimation(true);
                    tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
                    tView.setDefaultViewHolder(IconTreeItemHolder.class);
                    tView.setDefaultNodeClickListener(nodeClickListener);
                    tView.setDefaultNodeLongClickListener(nodeLongClickListener);
                }
                container.addView(tView.getView());
            }
        });
    }

    public List<MultistageBean.DataDTO.CheckAreasDTO> parse(JSONObject body, TreeNode treeNode) {
        List<MultistageBean.DataDTO.CheckAreasDTO> list = new ArrayList<>();
        JSONArray jsonArray = body.getJSONArray("checkAreas");
        for (int i = 0; i < jsonArray.size(); i++) {
            MultistageBean.DataDTO.CheckAreasDTO bean = new MultistageBean.DataDTO.CheckAreasDTO();
            JSONObject dataJsonObject = jsonArray.getJSONObject(i);
            bean.setId(dataJsonObject.getString("id"));
            bean.setPid(dataJsonObject.getString("pid"));
            bean.setName(dataJsonObject.getString("name"));
            bean.setIsParent(dataJsonObject.getString("isParent"));
            bean.setClose(dataJsonObject.getString("close"));
            TreeNode itemTreeNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(bean));
            treeNode.addChildren(itemTreeNode);
            if (TextUtils.equals("true", bean.getIsParent())) {
                bean.setCheckAreas(parse(dataJsonObject, itemTreeNode));
            }
            list.add(bean);
        }
        return list;
    }
}