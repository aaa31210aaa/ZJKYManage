package com.zhks.safetyproduction.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.entity.MultistageBean;

public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private ImageView icon;
    private MultistageBean.DataDTO.CheckAreasDTO item;

    public IconTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.multistageBean.getName());
        icon = view.findViewById(R.id.arrow_icon);
        item = value.multistageBean;
        if (TextUtils.equals("true", value.multistageBean.getIsParent())) {
            icon.setImageResource(R.drawable.arrow_right);
        }
        return view;
    }

    @Override
    public void toggle(boolean active) {
        if (TextUtils.equals("true",item.getIsParent())) {
            icon.setImageResource(active ? R.drawable.arrow_down : R.drawable.arrow_right);
        }
    }

    public static class IconTreeItem {
        public MultistageBean.DataDTO.CheckAreasDTO multistageBean;

        public IconTreeItem(MultistageBean.DataDTO.CheckAreasDTO multistageBean) {
            this.multistageBean = multistageBean;
        }
    }
}
