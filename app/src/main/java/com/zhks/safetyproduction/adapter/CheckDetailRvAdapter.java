package com.zhks.safetyproduction.adapter;

import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.SafeCheckFormBean;
import com.zhks.safetyproduction.entity.SafeCheckTermBean;

import java.util.List;

public class CheckDetailRvAdapter extends BaseQuickAdapter<SafeCheckTermBean.CellsDTO, BaseViewHolder> {
    public CheckDetailRvAdapter(int layoutResId, @Nullable List<SafeCheckTermBean.CellsDTO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SafeCheckTermBean.CellsDTO item) {
        TextView checkDetailItemSerialNumber = helper.getView(R.id.check_detail_item_serial_number);
        TextView checkDetailItemObservation = helper.getView(R.id.check_detail_item_observation);
        Switch safeCheckDetailItemSwitch = helper.getView(R.id.safe_check_detail_item_switch);
        checkDetailItemSerialNumber.setText(String.valueOf(helper.getLayoutPosition()));
        checkDetailItemObservation.setText(item.getItemsdescribe());
        if (item.isCheck()) {
            safeCheckDetailItemSwitch.setChecked(true);
        }

        if (item.isClickble()) {
            safeCheckDetailItemSwitch.setEnabled(true);
        } else {
            safeCheckDetailItemSwitch.setEnabled(false);
        }

        safeCheckDetailItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                safeCheckSwitchCall.SafeCheckSwitchListener(item, b, helper.getLayoutPosition());
            }
        });
    }

    public interface SafeCheckSwitchCall {
        void SafeCheckSwitchListener(SafeCheckTermBean.CellsDTO cellsDTO, boolean isCheck, int position);
    }

    public SafeCheckSwitchCall safeCheckSwitchCall;

    public void setSafeCheckSwitchCall(SafeCheckSwitchCall checkSwitchCall) {
        this.safeCheckSwitchCall = checkSwitchCall;
    }
}
