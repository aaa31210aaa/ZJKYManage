package com.zhks.safetyproduction.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.AccidentDetailBean;

import java.util.List;

public class AccidentDetailTypeAdapter extends BaseQuickAdapter<AccidentDetailBean.DataDTO.SGFLDTO, BaseViewHolder> {
    public AccidentDetailTypeAdapter(int layoutResId, @Nullable List<AccidentDetailBean.DataDTO.SGFLDTO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccidentDetailBean.DataDTO.SGFLDTO item) {
        helper.setText(R.id.safe_check_item_tv, item.getParamname());
    }
}
