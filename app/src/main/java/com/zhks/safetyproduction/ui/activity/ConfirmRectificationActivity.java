package com.zhks.safetyproduction.ui.activity;

import static com.zhks.safetyproduction.constants.Constants.CONFIRMMAXSELECTNUM;
import static com.zhks.safetyproduction.constants.PermissionConstants.permissionsPhotoGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
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
import com.zhks.safetyproduction.adapter.GridImageAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityConfirmRectificationBinding;
import com.zhks.safetyproduction.entity.ConfirmRectificationBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.manager.PictureSelectorManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.ConfirmRectificationViewModel;
import com.zhks.safetyproduction.viewmodel.RiskRectificationViewModel;
import com.zhks.safetyproduction.wight.FullyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ConfirmRectificationActivity extends BaseActivity<ActivityConfirmRectificationBinding, ConfirmRectificationViewModel>
        implements View.OnClickListener {
    private static GridImageAdapter mAdapter;
    private CustomPopWindow uploadImageWindow;
    private View uploadImageView;
    private ActivityResultLauncher<Intent> launcherResult;
    private static List<File> fileList = new ArrayList<>();
    private int viewId;
    private DateEntity dateEntity;
    private String trid;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_confirm_rectification;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            trid = bundle.getString("trid");
        }
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("????????????");
    }

    @Override
    public ConfirmRectificationViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(ConfirmRectificationViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        dateEntity = DateEntity.today();
        binding.completeDateTv.setText(DateUtils.getCurrentDate());
        uploadImageView = View.inflate(this, R.layout.upload_image_pop_layout, null);
        uploadImageView.findViewById(R.id.tvTakePhoto).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvChooseImage).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvDismiss).setOnClickListener(this);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.riskRegisterRv.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        binding.riskRegisterRv.setAdapter(mAdapter);
        // ??????????????????onCreate???Fragment onAttach??????????????????java.lang.IllegalStateException??????
        launcherResult = createActivityResultLauncher();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.completeSubmitEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(binding.recview.getText().toString())) {
                    ToastUtils.showShort("?????????????????????");
                    return;
                }

                if (mAdapter.getFileList().isEmpty()) {
                    ToastUtils.showShort("??????????????????????????????");
                    return;
                }

                ConfirmRectificationBean bean = new ConfirmRectificationBean();
                bean.setTrid(trid);
                bean.setRecfinishdate(binding.completeDateTv.getText().toString());
                bean.setRecview(binding.recview.getText().toString());
                String modelJson = JSON.toJSONString(bean);
                viewModel.submit(modelJson, mAdapter.getFileList());
            }
        });

        viewModel.completeDateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDay(ConfirmRectificationActivity.this, true, dateEntity, new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        binding.completeDateTv.setText(year + "-" + month + "-" + day);
                        try {
                            Date date = DateUtils.formatterYmd.parse(year + "-" + month + "-" + day);
                            dateEntity = DateEntity.target(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        viewModel.submitSuccessEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                EventBus.getDefault().post("????????????");
                finish();
            }
        });
    }

    /**
     * ??????????????????
     */
    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            showUploadImagePopWindow();
        }
    };

    private void showUploadImagePopWindow() {
        //???????????????popWindow
        uploadImageWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(uploadImageView)
                .enableBackgroundDark(true) //??????popWindow????????????????????????
                .setBgDarkAlpha(0.7f) // ????????????
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
                        PictureSelectorManager.openCamera(ConfirmRectificationActivity.this, CONFIRMMAXSELECTNUM, mAdapter.getData(), mAdapter, new ConfirmRectificationActivity.MyResultCallback(mAdapter));
                    } else if (viewId == R.id.tvChooseImage) {
                        PictureSelectorManager.openAlbum(ConfirmRectificationActivity.this,
                                PictureMimeType.ofImage(), CONFIRMMAXSELECTNUM, mAdapter.getData(), launcherResult);
                    }
                } else {
                    ToastUtils.showShort("??????????????????????????????????????????????????????");
                }
            }
        });
    }

    /**
     * ????????????ActivityResultLauncher
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
                            // ?????? LocalMedia ??????????????????path
                            // 1.media.getPath(); ??????path
                            // 2.media.getCutPath();?????????path????????????media.isCut();??????????????????
                            // 3.media.getCompressPath();?????????path????????????media.isCompressed();??????????????????
                            // 4.media.getOriginalPath()); media.isOriginal());???true?????????????????????
                            // 5.media.getAndroidQToPath();Android Q?????????????????????????????????????????????????????????????????????????????????????????????????????????.isAndroidQTransform ???false ?????????????????????
                            // ???????????????????????????????????????????????????????????????????????????????????????
                            fileList.clear();
                            for (LocalMedia media : selectList) {
                                if (media.getWidth() == 0 || media.getHeight() == 0) {
                                    if (PictureMimeType.isHasImage(media.getMimeType())) {
                                        MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
                                        media.setWidth(imageExtraInfo.getWidth());
                                        media.setHeight(imageExtraInfo.getHeight());
                                    } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(ConfirmRectificationActivity.this, media.getPath());
                                        media.setWidth(videoExtraInfo.getWidth());
                                        media.setHeight(videoExtraInfo.getHeight());
                                    }
                                }
                                fileList.add(new File(media.getRealPath()));
                                // TODO ????????????PictureSelectorExternalUtils.getExifInterface();??????????????????????????????????????????????????????????????????????????????
                            }
                            mAdapter.setFileList(fileList);
                            mAdapter.setList(selectList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
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


    /**
     * ??????????????????
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (launcherResult != null) {
            launcherResult.unregister();
        }
    }
}