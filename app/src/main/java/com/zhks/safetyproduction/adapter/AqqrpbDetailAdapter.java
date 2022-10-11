package com.zhks.safetyproduction.adapter;

import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.entity.RiskCheckItemBean;

import java.util.List;

public class AqqrpbDetailAdapter extends BaseQuickAdapter<AqqrpbBean.RowsDTO, BaseViewHolder> {
    public AqqrpbDetailAdapter(int layoutResId, @Nullable List<AqqrpbBean.RowsDTO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AqqrpbBean.RowsDTO item) {
        TextView qrbzTv = helper.getView(R.id.qrbzTv);
        qrbzTv.setText(item.getConfirmstandard());

        Switch qrjgSwitch = helper.getView(R.id.qrjgSwitch);
        if (TextUtils.isEmpty(item.getConfirmresult()) || TextUtils.equals(item.getConfirmresult(), Constants.QRJG001)) {
            qrjgSwitch.setChecked(true);
        } else {
            qrjgSwitch.setChecked(false);
        }

        if (item.isClickble()) {
            qrjgSwitch.setEnabled(true);
        } else {
            qrjgSwitch.setEnabled(false);
        }

        qrjgSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                aqqrpbCheckSwitchCall.AqqrpbDetailSwitchListener(item, b, helper.getLayoutPosition());
            }
        });

    }

    public interface AqqrpbDetailSwitchCall {
        void AqqrpbDetailSwitchListener(AqqrpbBean.RowsDTO dataDTO, boolean isCheck, int position);
    }

    public AqqrpbDetailAdapter.AqqrpbDetailSwitchCall aqqrpbCheckSwitchCall;

    public void setAqqrpbDetailSwitchCall(AqqrpbDetailAdapter.AqqrpbDetailSwitchCall checkSwitchCall) {
        this.aqqrpbCheckSwitchCall = checkSwitchCall;
    }
}
