package com.zhks.safetyproduction.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.TodoDetailBean;

import java.util.List;

public class TodoDetailAdapter extends BaseQuickAdapter<TodoDetailBean, BaseViewHolder> {
    private Context mContext;
    private List<TodoDetailBean> mDatas;

    public TodoDetailAdapter(int layoutResId, @Nullable List<TodoDetailBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
        this.mDatas = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoDetailBean item) {
        ImageView todoTopLine = helper.getView(R.id.todo_top_line);
        ImageView todoApproveNode = helper.getView(R.id.todo_approve_node);
        TextView todoApproverNickname = helper.getView(R.id.todo_approver_nickname);
        TextView todoApproverName = helper.getView(R.id.todo_approver_name);
        TextView todoApproveState = helper.getView(R.id.todo_approve_state);
        TextView todoApproveTime = helper.getView(R.id.todo_approve_time);
        TextView todoApproveComment = helper.getView(R.id.todo_approve_comment);
        ImageView todoApproveBottomLine = helper.getView(R.id.todo_approve_bottom_line);

        if (helper.getAdapterPosition() == 1) {
            todoTopLine.setVisibility(View.GONE);
        } else {
            todoTopLine.setVisibility(View.VISIBLE);
        }

        if (item.getPosition() == mDatas.size() - 1) {
            todoApproveBottomLine.setVisibility(View.GONE);
        } else {
            todoApproveBottomLine.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(item.getApprovalComments())) {
            todoApproveComment.setVisibility(View.GONE);
        } else {
            todoApproveComment.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals(item.getApprovalStatus(), "已同意")) {
            todoApproveNode.setImageDrawable(mContext.getResources().getDrawable(R.drawable.todo_agreed));
            todoTopLine.setBackgroundColor(mContext.getResources().getColor(R.color.todo_agree_line_color));
            todoApproveBottomLine.setBackgroundColor(mContext.getResources().getColor(R.color.todo_agree_line_color));
        } else {
            todoApproveNode.setImageDrawable(mContext.getResources().getDrawable(R.drawable.reviewed));
            todoTopLine.setBackgroundColor(mContext.getResources().getColor(R.color.gray_ccc));
            todoApproveBottomLine.setBackgroundColor(mContext.getResources().getColor(R.color.gray_ccc));
        }

        todoApproverNickname.setText(item.getApproverNikeName());
        todoApproverName.setText(item.getApprover());
        todoApproveState.setText(item.getApprovalStatus());
        todoApproveTime.setText(item.getApprovalTime());
        todoApproveComment.setText(item.getApprovalComments());
    }
}
