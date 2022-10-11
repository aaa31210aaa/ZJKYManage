package com.zhks.safetyproduction.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.ListQuestionsBean;
import com.zhks.safetyproduction.entity.QuestionBean;

import java.util.List;

public class QuestionPopAdapter extends BaseQuickAdapter<ListQuestionsBean.DataDTO, BaseViewHolder> {
    public QuestionPopAdapter(int layoutResId, @Nullable List<ListQuestionsBean.DataDTO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListQuestionsBean.DataDTO item) {
        TextView questionItem = helper.getView(R.id.question_item_tv);
        LinearLayout questionPopLin = helper.getView(R.id.question_pop_lin);
        if (item.isCheck()) {
            questionPopLin.setBackgroundColor(mContext.getResources().getColor(R.color.main_color));
            questionItem.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            questionPopLin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            questionItem.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        questionItem.setText(item.getQuestionNo());
        questionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionItemClick.setQuestionItemClick(helper.getLayoutPosition());
            }
        });
    }

    public QuestionItemClick questionItemClick;

    public interface QuestionItemClick {
        void setQuestionItemClick(int position);
    }

    public void setQuestionItemClick(QuestionItemClick questionItemClick) {
        this.questionItemClick = questionItemClick;
    }
}
