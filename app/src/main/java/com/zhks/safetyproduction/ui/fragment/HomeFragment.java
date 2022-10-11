package com.zhks.safetyproduction.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.zhouwei.library.CustomPopWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.constants.PermissionConstants;
import com.zhks.safetyproduction.entity.AppVersionUpdateBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.databinding.FragmentHomeBinding;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.utils.SystemUtils;
import com.zhks.safetyproduction.viewmodel.HomeViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.download.DownLoadStateBean;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.ResponseBody;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    private CustomPopWindow upDateWindow;
    private View updateView;
    private TextView updateTitle, updateContent;
    private LinearLayout llytUpdate;
    private TextView tvMustUpdate;
    private ProgressBar downloadingBar;
    private LinearLayout llytContent;
    private boolean canDownlaod = true;
    private TextView tvDismissUpdate;
    private TextView tvUpdate;
    private AppVersionUpdateBean.DataDTO updateBean;
    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public HomeViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getActivity().getApplication(), getActivity());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        updateView = View.inflate(getActivity(), R.layout.layout_update_window, null);
        tvUpdate = updateView.findViewById(R.id.tvUpdate);
        tvUpdate.setOnClickListener(onClickListener);
        tvDismissUpdate = updateView.findViewById(R.id.tvDismissUpdate);
        tvDismissUpdate.setOnClickListener(onClickListener);
        updateView.findViewById(R.id.tvMustUpdate).setOnClickListener(onClickListener);
        updateTitle = updateView.findViewById(R.id.tvUpdateVersion);
        updateContent = updateView.findViewById(R.id.tvUpdateContent);
        llytUpdate = updateView.findViewById(R.id.llytUpdate);
        tvMustUpdate = updateView.findViewById(R.id.tvMustUpdate);
        llytContent = updateView.findViewById(R.id.llytContent);
        downloadingBar = updateView.findViewById(R.id.downloading);
        viewModel.getUpdateVersion(getActivity());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvUpdate) {
                if (canDownlaod) {
                    update(updateBean.getUrl());
                    llytContent.setVisibility(View.GONE);
                    downloadingBar.setVisibility(View.VISIBLE);
                    tvDismissUpdate.setVisibility(View.GONE);
                    tvUpdate.setText("正在更新中...");
                }
            }
            if (v.getId() == R.id.tvDismissUpdate) {
                upDateWindow.dissmiss();
            }
            if (v.getId() == R.id.tvMustUpdate) {
                llytContent.setVisibility(View.GONE);
                downloadingBar.setVisibility(View.VISIBLE);
                if (canDownlaod) {
                    update(updateBean.getUrl());
                }
            }
        }
    };

    private void update(final String url) {
        new RxPermissions(this).requestEach(PermissionConstants.writePerMissionGrop).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    downloadApk(url);
                } else if (permission.shouldShowRequestPermissionRationale) {
                } else {
                    ToastUtils.showShort("请在设置中手动开启写入SD卡权限");
                }
            }
        });
    }

    private void downloadApk(String url) {
        final DownLoadStateBean downLoadStateBean = new DownLoadStateBean();
        DownLoadManager.getInstance().load(url, downLoadStateBean, new ProgressCallBack<ResponseBody>(Constants.APK_SAVE_PATH, Constants.APK_NAME) {
            @Override
            public void onStart() {
                super.onStart();
                canDownlaod = false;
            }

            @Override
            public void onCompleted() {
                Log.e("HomeActivity", "onCompleted");
                tvDismissUpdate.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                canDownlaod = true;
                SystemUtils.INSTANCE.installApk(Constants.APK_SAVE_PATH, Constants.APK_NAME);
            }

            @Override
            public void progress(final long progress, final long total) {
                downloadingBar.setMax((int) total);
                downloadingBar.setProgress((int) progress);
                downLoadStateBean.setTotal(total);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                canDownlaod = true;
                ToastUtils.showShort("下载更新包失败，请重新下载！");
                tvMustUpdate.setText("重新下载");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.appUpdateEvent.observe(this, new Observer<AppVersionUpdateBean.DataDTO>() {
            @Override
            public void onChanged(AppVersionUpdateBean.DataDTO dataDTO) {
                updateBean = dataDTO;
                showUpdateWindow(dataDTO);
            }
        });
    }

    private void showUpdateWindow(AppVersionUpdateBean.DataDTO bean) {
        updateTitle.setText("发现新版本" + bean.getVerCode());
        updateContent.setText(bean.getAppmemo());
        upDateWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(updateView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setOutsideTouchable(false)
                .setFocusable(false)
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .create()
                .showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
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
            viewModel.initHomeData(userBean.getUserid());
        } else {
            viewModel.itemViewModels.clear();
        }
    }


}