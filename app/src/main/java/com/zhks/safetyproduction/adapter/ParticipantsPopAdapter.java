package com.zhks.safetyproduction.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.MultChoiceBean;

import java.util.List;

public class ParticipantsPopAdapter extends BaseQuickAdapter<MultChoiceBean, BaseViewHolder> {
    public ParticipantsPopAdapter(int layoutResId, @Nullable List<MultChoiceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultChoiceBean item) {
        TextView multiName = helper.getView(R.id.multi_item_name);
        CheckBox multiCk = helper.getView(R.id.multi_item_ck);
        multiName.setText(item.getName());
        if (item.isCheck()) {
            multiCk.setChecked(true);
        } else {
            multiCk.setChecked(false);
        }

        multiCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isCheck()) {
                    item.setCheck(false);
                } else {
                    item.setCheck(true);
                }
            }
        });
    }
}
