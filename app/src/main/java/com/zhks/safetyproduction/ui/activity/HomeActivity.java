package com.zhks.safetyproduction.ui.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.adapter.ViewPagerBindingAdapter;
import com.zhks.safetyproduction.callback.CustomAutoSize;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityHomeBinding;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.manager.MyPreferencesManager;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.ui.fragment.HomeFragment;
import com.zhks.safetyproduction.ui.fragment.NewsFragment;
import com.zhks.safetyproduction.ui.fragment.WorkBenchFragment;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.HomeActivityViewModel;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.TabEntity;
import com.zhks.safetyproduction.viewmodel.LicenceCheckDetailViewModel;
import com.zhks.safetyproduction.wight.PrivacyWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeActivityViewModel> implements CustomAutoSize {
    private ViewPagerBindingAdapter adapter;
    private String[] mTitlesArrays = {"首页", "消息", "工作台"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_news_unselect,
            R.mipmap.tab_mine_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_news_select,
            R.mipmap.tab_mine_select};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private long mExitTime;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 1080;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public HomeActivityViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(HomeActivityViewModel.class);
    }

    @SuppressLint("ResourceType")
    @Override
    public void initData() {
        initViewPager();
        initViewPagerData();
        if (!MyPreferencesManager.getInstance().hasAgreePrivacyAgreement()) {
            PrivacyWindow privacyWindow = new PrivacyWindow(this);
            privacyWindow.initPrivacyWindow();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    privacyWindow.showPrivacyPopWindow(getWindow().getDecorView());
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void initViewPager() {
        if (null == adapter) {
            adapter = new ViewPagerBindingAdapter(getSupportFragmentManager(), fragments, mTitlesArrays);
        }
    }

    private void initViewPagerData() {
        fragments.add(new HomeFragment());
        fragments.add(new NewsFragment());
        fragments.add(new WorkBenchFragment());
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(adapter);
        for (int i = 0; i < mTitlesArrays.length; i++) {
            mTabEntities.add(new TabEntity(mTitlesArrays[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        binding.tablayout.setTabData(mTabEntities);
        //监听tab
        binding.tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                binding.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        //监听viewpager
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.tablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.viewPager.setCurrentItem(0);
    }


    @Override
    public void initViewObservable() {
//        viewModel.itemClickEvent.observe(this, s -> ToastUtils.showShort("position：" + s));
        viewModel.loginStateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                ToastUtils.showShort("登录已过期，请重新登录");
                PersonInfoManager.getInstance().clearUserInfo();
                viewModel.startActivity(LoginActivity.class);
                EventBus.getDefault().post(new UserBean());
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                ToastUtils.showShort("再按一次退出应用");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                finish();
//                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}