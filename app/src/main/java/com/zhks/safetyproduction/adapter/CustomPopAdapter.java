package com.zhks.safetyproduction.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.CurrentUserBean;
import com.zhks.safetyproduction.entity.CustomPopBean;
import com.zhks.safetyproduction.entity.DepartmentBean;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.entity.MiningAreaBean;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CustomPopAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private Handler handler;

    public CustomPopAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (item instanceof DepartmentBean.CellsDTO.DateDTO) {
            helper.setText(R.id.safe_check_item_tv, ((DepartmentBean.CellsDTO.DateDTO) item).getDtname());
        } else if (item instanceof DeptUserBean.CellsDTO) {
            TextView safeCheckItemTv = helper.getView(R.id.safe_check_item_tv);
            if (null != safeCheckItemTv) {
                helper.setText(R.id.safe_check_item_tv, ((DeptUserBean.CellsDTO) item).getUsername());
            } else {
                helper.setText(R.id.multi_item_name, ((DeptUserBean.CellsDTO) item).getUsername());
                CheckBox checkBox = helper.getView(R.id.multi_item_ck);
                RelativeLayout mulItem = helper.getView(R.id.multi_item_rl);
                if (((DeptUserBean.CellsDTO) item).isCheck()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setEnabled(false);

                mulItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((DeptUserBean.CellsDTO) item).isCheck()) {
                            ((DeptUserBean.CellsDTO) item).setCheck(false);
                        } else {
                            ((DeptUserBean.CellsDTO) item).setCheck(true);
                        }
                        notifyDataSetChanged();
                    }
                });
            }

        } else if (item instanceof AccidentDetailBean.DataDTO.JCLXDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.JCLXDTO) item).getParamname());
        } else if (item instanceof AccidentDetailBean.DataDTO.JCJBDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.JCJBDTO) item).getParamname());
        } else if (item instanceof AccidentDetailBean.DataDTO.YHLXDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.YHLXDTO) item).getParamname());
        } else if (item instanceof CurrentUserBean.DataDTO) {
            helper.setText(R.id.multi_item_name, ((CurrentUserBean.DataDTO) item).getUsername());
            ImageView checkImage = helper.getView(R.id.multi_item_ck);
            RelativeLayout mulItem = helper.getView(R.id.multi_item_rl);


            if (((CurrentUserBean.DataDTO) item).isCheck()) {
                checkImage.setBackgroundResource(R.drawable.draft_item_select);
            } else {
                checkImage.setBackgroundResource(R.drawable.draft_item_unselect);
            }

            checkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBoxClick.setCheckBoxClick((CurrentUserBean.DataDTO) item, helper.getLayoutPosition());
                }
            });


//            mulItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (((CurrentUserBean.DataDTO) item).isCheck()) {
//                        ((CurrentUserBean.DataDTO) item).setCheck(false);
//                    } else {
//                        ((CurrentUserBean.DataDTO) item).setCheck(true);
//                    }
//                    notifyDataSetChanged();
//                }
//            });

        } else if (item instanceof CustomPopBean) {
            helper.setText(R.id.safe_check_item_tv, ((CustomPopBean) item).getMessage());
        } else if (item instanceof AccidentDetailBean.DataDTO.SLPTDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.SLPTDTO) item).getParamname());
        } else if (item instanceof AccidentDetailBean.DataDTO.YHLYDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.YHLYDTO) item).getParamname());
        } else if (item instanceof AccidentDetailBean.DataDTO.YHJBDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.YHJBDTO) item).getParamname());
        } else if (item instanceof AccidentDetailBean.DataDTO.YHLBDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.YHLBDTO) item).getParamname());
        } else if (item instanceof AccidentDetailBean.DataDTO.ZGLXDTO) {
            helper.setText(R.id.safe_check_item_tv, ((AccidentDetailBean.DataDTO.ZGLXDTO) item).getParamname());
        } else if (item instanceof MiningAreaBean.DataDTO) {
            helper.setText(R.id.safe_check_item_tv, ((MiningAreaBean.DataDTO) item).getDeptName());
        } else {
            helper.setText(R.id.safe_check_item_tv, item.toString());
        }
    }

    public CheckBoxClick checkBoxClick;


    public interface CheckBoxClick {
        void setCheckBoxClick(CurrentUserBean.DataDTO dataDTO, int position);
    }

    public void setCheckBoxClick(CheckBoxClick checkBoxClick) {
        this.checkBoxClick = checkBoxClick;
    }
}
