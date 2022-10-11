package com.zhks.safetyproduction.ui.activity;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.TodoDetailAdapter;
import com.zhks.safetyproduction.entity.TodoDetailBean;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.KeyboardUtils;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;

public class TodoDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView todoDetailRv;
    private TodoDetailAdapter adapter;
    private List<TodoDetailBean> mDatas;
    private View headerView;
    private TextView todoDetailTitle;
    private TextView currentState;
    private TextView todoDetailApplyContent;
    private TextView todoDetailDisagree;
    private TextView todoDetailAgree;
    private TextView todoDetailComment;
    private LinearLayout todoCommentLin;
    private EditText todoCommentEdittext;
    private TextView todoCommentSend;
    //附着在软键盘上的输入弹出窗
    public CustomPopWindow inputAndSendPop;
    private View sendPopContentView;
    private View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        initData();
    }

    public void initData() {
        ScreenUtils.fullScreen(this, false);
        ScreenUtils.StatusBarLightMode(this, true);
        rootView = findViewById(R.id.rootView);
        todoDetailRv = findViewById(R.id.todo_detail_rv);
        todoDetailDisagree = findViewById(R.id.todo_detail_disagree);
        todoDetailDisagree.setOnClickListener(this);
        todoDetailAgree = findViewById(R.id.todo_detail_agree);
        todoDetailAgree.setOnClickListener(this);
        todoDetailComment = findViewById(R.id.todo_detail_comment);
        todoDetailRv.setHasFixedSize(true);
        mDatas = new ArrayList<>();
        sendPopContentView = View.inflate(this, R.layout.send_pop_content_view, null);
        todoCommentLin = sendPopContentView.findViewById(R.id.todo_comment_lin);
        todoCommentEdittext = sendPopContentView.findViewById(R.id.todo_comment_edittext);
        todoCommentSend = sendPopContentView.findViewById(R.id.todo_comment_send);
        todoDetailComment.setOnClickListener(this);
        todoCommentSend.setOnClickListener(this);
        headerView = View.inflate(this, R.layout.todo_detail_header, null);
        todoDetailTitle = headerView.findViewById(R.id.todo_detail_title);
        currentState = headerView.findViewById(R.id.current_state);
        todoDetailApplyContent = headerView.findViewById(R.id.todo_detail_apply_content_title);
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        todoDetailRv.setLayoutManager(linearLayoutManager);
        adapter = new TodoDetailAdapter(R.layout.todo_detail_item_layout, mDatas, this);
        adapter.addHeaderView(headerView);
        todoDetailRv.setAdapter(adapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 6; i++) {
            TodoDetailBean detailBean = new TodoDetailBean();
            detailBean.setPosition(i);
            detailBean.setApproverNikeName("审批人");
            detailBean.setApprover("张三" + i);
            detailBean.setApprovalTime("2021-11-23");
            if (i == 3 || i == 4 || i == 5) {
                detailBean.setApprovalStatus("待审批");
            } else {
                detailBean.setApprovalStatus("已同意");
            }

            if (i == 3) {
                detailBean.setApprovalComments("测试测试测试测试测试");
            }
            mDatas.add(detailBean);
        }
        adapter.setNewData(mDatas);
        todoDetailTitle.setText("张三提交的高空作业审批");
        currentState.setText("审批通过");
        todoDetailApplyContent.setText("周一需要进行高空作业，请领导知悉并审批");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.todo_detail_disagree) { //不同意

        } else if (id == R.id.todo_detail_agree) { //同意

        } else if (id == R.id.todo_detail_comment) { //评论
            ToastUtils.showShort("测试");
            KeyboardUtils.toggleSoftInput(getWindow().getDecorView());
            showInputEdittextAndSend();
        } else if (id == R.id.todo_comment_send) { //发送评论
            todoCommentLin.setVisibility(View.GONE);
        }
    }

    /**
     * 弹出发送评论弹出窗
     */
    private void showInputEdittextAndSend() {
        //创建并显示popWindow
        if (null == inputAndSendPop) {
            inputAndSendPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(sendPopContentView)
                    .setOutsideTouchable(false)
                    .setFocusable(true)
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .size(Utils.getContext().getResources().getDisplayMetrics().widthPixels, DensityUtil.dip2px(50))
                    .create()
                    .showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        } else {
            inputAndSendPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }
        todoCommentEdittext.setFocusable(true);
        todoCommentEdittext.setFocusableInTouchMode(true);
        todoCommentEdittext.requestFocus();
    }
}