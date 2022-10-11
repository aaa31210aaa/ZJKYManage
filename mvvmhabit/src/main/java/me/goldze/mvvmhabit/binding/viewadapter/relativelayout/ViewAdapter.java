package me.goldze.mvvmhabit.binding.viewadapter.relativelayout;

import android.widget.RelativeLayout;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {
    @BindingAdapter(value = {"backgroudImage"}, requireAll = false)
    public static void setBackgroudImage(RelativeLayout relativeLayout, int drawableRes) {
        relativeLayout.setBackgroundResource(drawableRes);
    }
}
