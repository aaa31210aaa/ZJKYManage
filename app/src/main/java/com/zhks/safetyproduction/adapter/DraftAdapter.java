package com.zhks.safetyproduction.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.HiddenDBean;

import java.util.List;

public class DraftAdapter extends BaseQuickAdapter<HiddenDBean, BaseViewHolder> {
    private boolean isEdit;

    public DraftAdapter(int layoutResId, @Nullable List<HiddenDBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HiddenDBean item) {
        helper.setText(R.id.draft_item_describe, item.getTrdescribe());
        helper.setText(R.id.draft_item_date, item.getInputDate());
        ImageView draftItemCheck = helper.getView(R.id.draft_item_check);

        if (isEdit) {
            draftItemCheck.setVisibility(View.VISIBLE);
        } else {
            draftItemCheck.setVisibility(View.INVISIBLE);
        }

        if (item.getIsCheck()) {
            item.setIsCheck(true);
        } else {
            item.setIsCheck(false);
        }

        draftItemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getIsCheck()) {
                    item.setIsCheck(false);
                    draftItemCheck.setImageResource(R.drawable.draft_item_unselect);
                } else {
                    item.setIsCheck(true);
                    draftItemCheck.setImageResource(R.drawable.draft_item_select);
                }
            }
        });
    }

    public void setIsEdit(boolean mIsEdit) {
        this.isEdit = mIsEdit;
    }
}
