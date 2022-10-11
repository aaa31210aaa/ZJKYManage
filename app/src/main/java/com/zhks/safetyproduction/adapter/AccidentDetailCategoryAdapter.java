package com.zhks.safetyproduction.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.AccidentDetailBean;

import java.util.List;

public class AccidentDetailCategoryAdapter extends BaseQuickAdapter<AccidentDetailBean.DataDTO.SGLXDTO, BaseViewHolder> {
    public AccidentDetailCategoryAdapter(int layoutResId, @Nullable List<AccidentDetailBean.DataDTO.SGLXDTO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccidentDetailBean.DataDTO.SGLXDTO item) {
        helper.setText(R.id.safe_check_item_tv, item.getParamname());
    }
}
