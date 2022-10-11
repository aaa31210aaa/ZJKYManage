package com.zhks.safetyproduction.adapter;

import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.RiskCheckItemBean;

import java.util.List;

public class RiskCheckDetailAdapter extends BaseQuickAdapter<RiskCheckItemBean.DataDTO, BaseViewHolder> {
    public RiskCheckDetailAdapter(int layoutResId, @Nullable List<RiskCheckItemBean.DataDTO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RiskCheckItemBean.DataDTO item) {
        helper.setText(R.id.controlMeasures, item.getManameasure());
        Switch riskPointSwitch = helper.getView(R.id.riskPointSwitch);

        if (!item.isCheck()) {
            riskPointSwitch.setChecked(true);
        } else {
            riskPointSwitch.setChecked(false);
        }

        if (item.isClickble()) {
            riskPointSwitch.setEnabled(true);
        } else {
            riskPointSwitch.setEnabled(false);
        }


        riskPointSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                safeCheckSwitchCall.RiskDetailSwitchListener(item, b, helper.getLayoutPosition());
            }
        });
    }

    public interface RiskDetailSwitchCall {
        void RiskDetailSwitchListener(RiskCheckItemBean.DataDTO dataDTO, boolean isCheck, int position);
    }

    public RiskDetailSwitchCall safeCheckSwitchCall;

    public void setRiskDetailSwitchCall(RiskCheckDetailAdapter.RiskDetailSwitchCall checkSwitchCall) {
        this.safeCheckSwitchCall = checkSwitchCall;
    }
}
