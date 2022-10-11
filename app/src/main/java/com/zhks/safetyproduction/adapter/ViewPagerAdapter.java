package com.zhks.safetyproduction.adapter;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.zhks.safetyproduction.databinding.SingleChoiceItemBinding;
import com.zhks.safetyproduction.viewmodel.SingleChoiceViewModel;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;

public class ViewPagerAdapter extends BindingViewPagerAdapter<SingleChoiceViewModel> {
    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, SingleChoiceViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        SingleChoiceItemBinding _binding = (SingleChoiceItemBinding) binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
