package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.zhks.safetyproduction.constants.Constants.MAXSELECTNUM;
import static com.zhks.safetyproduction.constants.Constants.REQUEST_CODE_CHOOSE;
import static com.zhks.safetyproduction.constants.Constants.TO_MULTI_CODE;
import static com.zhks.safetyproduction.constants.Constants.TO_PERSONNEL_CODE;
import static com.zhks.safetyproduction.constants.PermissionConstants.permissionsPhotoGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.MediaUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.adapter.GridImageAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.databinding.ActivityHiddenReportDetailBinding;
import com.zhks.safetyproduction.entity.AccidentDetailBean;
import com.zhks.safetyproduction.entity.HiddenReportBean;
import com.zhks.safetyproduction.manager.PictureSelectorManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.HiddenReportDetailViewModel;
import com.zhks.safetyproduction.wight.FullyGridLayoutManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HiddenReportDetailActivity extends BaseActivity<ActivityHiddenReportDetailBinding, HiddenReportDetailViewModel>
        implements View.OnClickListener {
    private View reportTypePopView;
    private List<AccidentDetailBean.DataDTO.YHLXDTO> reportTypeDatas = new ArrayList<>();
    private Map<String, String> reportTypeMap = new HashMap<>();

    private CustomPopAdapter reportTypeAdapter;
    private RecyclerView reportTypeRv;
    private CustomPopWindow reportTypePop;
    private static GridImageAdapter mAdapter;
    private ActivityResultLauncher<Intent> launcherResult;
    private int viewId;
    private CustomPopWindow uploadImageWindow;
    private View uploadImageView;
    public int personalPageIndex = 1;
    private static List<File> fileList = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_hidden_report_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("提交");
        viewModel.setTitleText("隐患举报");
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.yhflEvent.observe(this, new Observer<List<AccidentDetailBean.DataDTO.YHLXDTO>>() {
            @Override
            public void onChanged(List<AccidentDetailBean.DataDTO.YHLXDTO> yhlxdtos) {
                reportTypeDatas = yhlxdtos;
                for (int i = 0; i < reportTypeDatas.size(); i++) {
                    reportTypeMap.put(reportTypeDatas.get(i).getParamname(), reportTypeDatas.get(i).getCode());
                }
                binding.reportType.setText(reportTypeDatas.get(0).getParamname());
                reportTypeAdapter.setNewData(reportTypeDatas);
            }
        });

        viewModel.submitEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                HiddenReportBean bean = new HiddenReportBean();
                bean.setReporttype(reportTypeMap.get(binding.reportType.getText().toString()));
                bean.setReporttypename(binding.reportType.getText().toString());
                bean.setTroubleplace(binding.hiddenPlace.getText().toString());
                bean.setTroubledescribe(binding.hiddenDescribe.getText().toString());
                bean.setTroubleaccunit("");
                bean.setTroubleaccunitname("");
                bean.setCreateid(PersonInfoManager.getInstance().getUserId());
                bean.setCreatedate(DateUtils.getCurrentDate());
                bean.setAcceptanceperson(binding.receiver.getText().toString());
                String modelJson = JSON.toJSONString(bean);
                viewModel.addTroublereport(modelJson, fileList);
            }
        });


        viewModel.receiverEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
//                showReceiverPop();
                Intent intent = new Intent(HiddenReportDetailActivity.this, PersonnelQueryActivity.class);
                startActivityForResult(intent, TO_PERSONNEL_CODE);
            }
        });

        viewModel.reportTypeEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showReportTypePopWindow();
            }
        });

        viewModel.choosePlaceEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent intent = new Intent(HiddenReportDetailActivity.this, MultistageActivity.class);
                intent.putExtra(Constants.MULTI_TITLE, "隐患地点");
                startActivityForResult(intent, TO_MULTI_CODE);
            }
        });
    }


    @Override
    public void initData() {
        super.initData();
        reportTypePopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        reportTypeAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, reportTypeDatas);
        reportTypeRv = reportTypePopView.findViewById(R.id.spinner_pop_rv);
        reportTypeRv.setLayoutManager(new LinearLayoutManager(this));
        reportTypeRv.setAdapter(reportTypeAdapter);
        reportTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.reportType.setText(reportTypeDatas.get(position).getParamname());
                if (null != reportTypePop) {
                    reportTypePop.dissmiss();
                }
            }
        });


        uploadImageView = View.inflate(this, R.layout.upload_image_pop_layout, null);
        uploadImageView.findViewById(R.id.tvTakePhoto).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvChooseImage).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvDismiss).setOnClickListener(this);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.hiddenReportRv.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        binding.hiddenReportRv.setAdapter(mAdapter);
        // 注册需要写在onCreate或Fragment onAttach里，否则会报java.lang.IllegalStateException异常
        launcherResult = createActivityResultLauncher();
    }


    @Override
    public HiddenReportDetailViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(HiddenReportDetailViewModel.class);
    }

    /**
     * 增加图片按钮
     */
    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            showUploadImagePopWindow();
        }
    };

    /**
     * 创建一个ActivityResultLauncher
     *
     * @return
     */
    private ActivityResultLauncher<Intent> createActivityResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == RESULT_OK) {
                            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(result.getData());
                            fileList.clear();
                            for (LocalMedia media : selectList) {
                                if (media.getWidth() == 0 || media.getHeight() == 0) {
                                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                                        media.setWidth(imageExtraInfo.getWidth());
                                        media.setHeight(imageExtraInfo.getHeight());
                                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(HiddenReportDetailActivity.this, media.getPath());
                                        media.setWidth(videoExtraInfo.getWidth());
                                        media.setHeight(videoExtraInfo.getHeight());
                                    }
                                }
                                fileList.add(new File(media.getRealPath()));
                                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
                            }
                            mAdapter.setFileList(fileList);
                            mAdapter.setList(selectList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 返回结果回调
     */
    public static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                if (media.getWidth() == 0 || media.getHeight() == 0) {
                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                        media.setWidth(imageExtraInfo.getWidth());
                        media.setHeight(imageExtraInfo.getHeight());
                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(PictureAppMaster.getInstance().getAppContext(), media.getPath());
                        media.setWidth(videoExtraInfo.getWidth());
                        media.setHeight(videoExtraInfo.getHeight());
                    }
                }
                fileList.add(new File(media.getRealPath()));
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapter.setFileList(fileList);
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
        }
    }

    private void showReportTypePopWindow() {
        if (null == reportTypePop) {
            reportTypePop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(reportTypePopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.reportType.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.reportType);
        } else {
            reportTypePop.showAsDropDown(binding.reportType);
        }
    }

    private void showUploadImagePopWindow() {
        //创建并显示popWindow
        uploadImageWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(uploadImageView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(150))
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @SuppressLint("CheckResult")
    private void chooseImage() {
        new RxPermissions(this).request(permissionsPhotoGroup).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    if (viewId == R.id.tvTakePhoto) {
                        PictureSelectorManager.openCamera(HiddenReportDetailActivity.this, MAXSELECTNUM, mAdapter.getData(), mAdapter, new MyResultCallback(mAdapter));
                    } else if (viewId == R.id.tvChooseImage) {
                        PictureSelectorManager.openAlbum(HiddenReportDetailActivity.this,
                                PictureMimeType.ofImage(), MAXSELECTNUM, mAdapter.getData(), launcherResult);
                    }
                } else {
                    ToastUtils.showShort("获取权限失败，请在设置中打开相关权限");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            for (LocalMedia media : selectList) {
                if (media.getWidth() == 0 || media.getHeight() == 0) {
                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                        media.setWidth(imageExtraInfo.getWidth());
                        media.setHeight(imageExtraInfo.getHeight());
                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(this, media.getPath());
                        media.setWidth(videoExtraInfo.getWidth());
                        media.setHeight(videoExtraInfo.getHeight());
                    }
                }
            }
            mAdapter.setList(selectList);
            mAdapter.notifyDataSetChanged();
        } else if (requestCode == TO_MULTI_CODE && resultCode == RESULT_OK) {
            String areaName = data.getStringExtra("areaName");
            binding.hiddenPlace.setText(areaName);
        } else if (requestCode == TO_PERSONNEL_CODE && resultCode == RESULT_OK) {
            binding.receiver.setText(data.getStringExtra("personnelTv"));
        }
    }

    @Override
    public void onClick(View view) {
        viewId = view.getId();
        if (viewId == R.id.tvTakePhoto) {
            if (null != uploadImageWindow) {
                chooseImage();
                uploadImageWindow.dissmiss();
            }
        } else if (viewId == R.id.tvChooseImage) {
            chooseImage();
            if (null != uploadImageWindow) {
                uploadImageWindow.dissmiss();
            }
        } else if (viewId == R.id.tvDismiss) {
            if (null != uploadImageWindow) {
                uploadImageWindow.dissmiss();
            }
        }
    }
}