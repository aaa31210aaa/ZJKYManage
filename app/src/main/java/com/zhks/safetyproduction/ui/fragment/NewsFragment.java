package com.zhks.safetyproduction.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.databinding.FragmentNewsBinding;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.NewsViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.goldze.mvvmhabit.base.BaseFragment;

public class NewsFragment extends BaseFragment<FragmentNewsBinding, NewsViewModel> {

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_news;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(getActivity(), R.color.main_color);
        EventBus.getDefault().register(this);
    }

    @Override
    public NewsViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getActivity().getApplication(), getActivity());
        return ViewModelProviders.of(this, factory).get(NewsViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        initRefresh();
        if (!TextUtils.isEmpty(PersonInfoManager.getInstance().getUserId())) {
            viewModel.initData(binding.refreshLayout);
        }
    }

    private void initRefresh() {
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.initData(binding.refreshLayout);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(UserBean userBean) {
        if (!TextUtils.isEmpty(userBean.getUserid())) {
            PersonInfoManager.getInstance().setUserId(userBean.getUserid());
            PersonInfoManager.getInstance().setUserName(userBean.getUsername());
            PersonInfoManager.getInstance().setUserType(userBean.getUsertype());
            PersonInfoManager.getInstance().setDeptid(userBean.getDeptid());
            PersonInfoManager.getInstance().setDeptName(userBean.getDeptname());
            PersonInfoManager.getInstance().setAdmin(userBean.isAdmin());
            viewModel.initData(binding.refreshLayout);
        } else {
            viewModel.itemViewModels.clear();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(String getPush) {
        if (TextUtils.equals("getPush", getPush)) {
            viewModel.initData(binding.refreshLayout);
        }
    }
}