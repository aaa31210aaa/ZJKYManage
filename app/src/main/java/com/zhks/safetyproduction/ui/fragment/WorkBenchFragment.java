package com.zhks.safetyproduction.ui.fragment;

import static com.zhks.safetyproduction.constants.Constants.TO_LOGING_CODE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.constants.PermissionConstants;
import com.zhks.safetyproduction.databinding.FragmentWorkBenchBinding;
import com.zhks.safetyproduction.entity.AppVersionUpdateBean;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.ui.activity.LoginActivity;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.GlideUtil;
import com.zhks.safetyproduction.utils.SystemUtils;
import com.zhks.safetyproduction.viewmodel.LicenceCheckViewModel;
import com.zhks.safetyproduction.viewmodel.WorkBenchViewModel;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.download.DownLoadStateBean;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.ResponseBody;

public class WorkBenchFragment extends BaseFragment<FragmentWorkBenchBinding, WorkBenchViewModel> {
    public ImageView userHeadImage;
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private CustomPopWindow popupWindow;
    private View popView;
    private CustomPopWindow signOutPop;
    private View signOutPopView;
    private TextView signOutCancel;
    private TextView signOutSubmit;
    private CustomPopWindow upDateWindow;
    private View updateView;
    private TextView updateTitle, updateContent;
    private LinearLayout llytUpdate;
    private TextView tvMustUpdate;
    private ProgressBar downloadingBar;
    private LinearLayout llytContent;
    private boolean canDownlaod = true;
    private AppVersionUpdateBean.DataDTO updateBean;
    private TextView tvDismissUpdate;
    private TextView tvUpdate;

    public WorkBenchFragment() {
        // Required empty public constructor
    }

    public static WorkBenchFragment newInstance() {
        WorkBenchFragment fragment = new WorkBenchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_work_bench;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        userHeadImage = getActivity().findViewById(R.id.user_head_image);
        GlideUtil.displayCircle(userHeadImage, R.drawable.login_default_headimg, true, getActivity());
        String userName = PersonInfoManager.getInstance().getUserName();
        String deptName = PersonInfoManager.getInstance().getDeptName();
        if (TextUtils.isEmpty(PersonInfoManager.getInstance().getUserId())) {
            viewModel.userName.set("点击头像登录");
            viewModel.deptName.set("---");
            viewModel.signOutVisible.set(View.GONE);
        } else {
            setSignSuc(userName, deptName);
        }
        signOutPopView = View.inflate(getActivity(), R.layout.signout_pop_layout, null);
        signOutCancel = signOutPopView.findViewById(R.id.signOutCancel);
        signOutSubmit = signOutPopView.findViewById(R.id.signOutSubmit);

        signOutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != popupWindow) {
                    popupWindow.dissmiss();
                }
            }
        });

        signOutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSignOut();
                PersonInfoManager.getInstance().clearUserInfo();
                if (null != popupWindow) {
                    popupWindow.dissmiss();
                }
                EventBus.getDefault().post(new UserBean());
            }
        });

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

    private void setSignOut() {
        viewModel.userName.set("点击头像登录");
        viewModel.deptName.set("---");
        viewModel.signOutVisible.set(View.GONE);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, TO_LOGING_CODE);
    }

    private void setSignSuc(String userName, String deptName) {
        viewModel.userName.set(userName);
        viewModel.deptName.set(deptName);
        viewModel.signOutVisible.set(View.VISIBLE);
    }

    @Override
    public WorkBenchViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getActivity().getApplication(), getActivity());
        return ViewModelProviders.of(this, factory).get(WorkBenchViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.toLoginEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, TO_LOGING_CODE);
            }
        });

        viewModel.signOutEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showSignOutPop();
            }
        });

        viewModel.appUpdateEvent.observe(this, new Observer<AppVersionUpdateBean.DataDTO>() {
            @Override
            public void onChanged(AppVersionUpdateBean.DataDTO dataDTO) {
                updateBean = dataDTO;
                showUpdateWindow(dataDTO);
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_LOGING_CODE) {
            //登录成功
            UserBean userBean = (UserBean) data.getSerializableExtra("userBean");
//            PersonInfoManager.getInstance().setUserId(userBean.getUserid());
//            PersonInfoManager.getInstance().setUserName(userBean.getUsername());
//            PersonInfoManager.getInstance().setUserType(userBean.getUsertype());
//            PersonInfoManager.getInstance().setDeptid(userBean.getDeptid());
//            PersonInfoManager.getInstance().setDeptName(userBean.getDeptname());
            PersonInfoManager.getInstance().setUserName(userBean.getUsername());
            PersonInfoManager.getInstance().setDeptName(userBean.getDeptname());
            viewModel.userName.set(userBean.getUsername());
            viewModel.deptName.set(userBean.getDeptname());
            viewModel.signOutVisible.set(View.VISIBLE);
        }
    }

    private void showSignOutPop() {
        popupWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(signOutPopView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(DensityUtil.dip2px(260), ViewGroup.LayoutParams.WRAP_CONTENT)
                .create()
                .showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
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
}