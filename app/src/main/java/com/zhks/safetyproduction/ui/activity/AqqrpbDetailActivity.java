package com.zhks.safetyproduction.ui.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static com.zhks.safetyproduction.constants.Constants.MAXSELECTNUM;
import static com.zhks.safetyproduction.constants.Constants.TO_MULTI_CODE;
import static com.zhks.safetyproduction.constants.Constants.WRITE_READ_PERMISSION;
import static com.zhks.safetyproduction.constants.PermissionConstants.permissionsPhotoGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.zhks.safetyproduction.adapter.AqqrpbDetailAdapter;
import com.zhks.safetyproduction.adapter.GridImageAdapter;
import com.zhks.safetyproduction.adapter.RiskCheckDetailAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.databinding.ActivityAqqrpbDetailBinding;
import com.zhks.safetyproduction.entity.AqqrpbBean;
import com.zhks.safetyproduction.entity.EnclosureBean;
import com.zhks.safetyproduction.entity.HiddenDBean;
import com.zhks.safetyproduction.entity.RiskCheckItemBean;
import com.zhks.safetyproduction.manager.PictureSelectorManager;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.utils.StringUtils;
import com.zhks.safetyproduction.viewmodel.AqqrpbDetailViewModel;
import com.zhks.safetyproduction.viewmodel.SafeCheckListViewModel;
import com.zhks.safetyproduction.wight.FullyGridLayoutManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.DonwloadSaveImg;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class AqqrpbDetailActivity extends BaseActivity<ActivityAqqrpbDetailBinding, AqqrpbDetailViewModel> implements View.OnClickListener {
    private AqqrpbBean.RowsDTO bean;
    private AqqrpbDetailAdapter adapter;
    private List<AqqrpbBean.RowsDTO> mDatas = new ArrayList<>();
    private int selectIndex;
    private CustomPopWindow checkToHiddenPop;
    private View checkToHiddenView;
    private TextView checkToHiddenCancel;
    private TextView toYbHidden;
    private TextView toZdHidden;
    private List<String> safeCheckTermDatas = new ArrayList<>();
    private static GridImageAdapter mAdapter;
    private ActivityResultLauncher<Intent> launcherResult;
    private CustomPopWindow uploadImageWindow;
    private View uploadImageView;
    private int viewId;
    private static List<File> fileList = new ArrayList<>();
    private List<LocalMedia> exFiles = new ArrayList<>();
    private List<File> fileListReady = new ArrayList<>();
    private List<LocalMedia> ylList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_aqqrpb_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
        Intent intent = getIntent();
        bean = (AqqrpbBean.RowsDTO) intent.getSerializableExtra("bean");
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("?????????????????????");
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("??????");
    }

    @Override
    public AqqrpbDetailViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(AqqrpbDetailViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        binding.pbDate.setText(bean.getDates());
        binding.pbbc.setText(bean.getShift());
        binding.pdzydd.setText(bean.getWorkname());
        binding.pbsszd.setText(bean.getMidname());
        binding.pbzymlx.setText(bean.getWorktypename());
        binding.pbzyry.setText(bean.getEowstaff());
        viewModel.workingFaceDetail(bean.getCsid());
        adapter = new AqqrpbDetailAdapter(R.layout.aqqrpb_detail_item_layout, mDatas);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        initPhoto();

        checkToHiddenView = View.inflate(this, R.layout.check_to_hidden_pop, null);
        checkToHiddenCancel = checkToHiddenView.findViewById(R.id.checkToHiddenCancel);
        checkToHiddenCancel.setOnClickListener(this);
        toYbHidden = checkToHiddenView.findViewById(R.id.toYbHidden);
        toZdHidden = checkToHiddenView.findViewById(R.id.toZdHidden);
        toYbHidden.setOnClickListener(this);
        toZdHidden.setOnClickListener(this);


        adapter.setAqqrpbDetailSwitchCall(new AqqrpbDetailAdapter.AqqrpbDetailSwitchCall() {
            @Override
            public void AqqrpbDetailSwitchListener(AqqrpbBean.RowsDTO dataDTO, boolean isCheck, int position) {
                if (!isCheck && !mDatas.get(position).isCheck()) {
                    //????????????  ???????????????????????????
                    selectIndex = position - 1;
                    showToRiskRegister();
                }
            }
        });
    }

    private void initPhoto() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.aqqrpbPhotoRv.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        binding.aqqrpbPhotoRv.setAdapter(mAdapter);
        // ??????????????????onCreate???Fragment onAttach??????????????????java.lang.IllegalStateException??????
        launcherResult = createActivityResultLauncher();

        uploadImageView = View.inflate(this, R.layout.upload_image_pop_layout, null);
        uploadImageView.findViewById(R.id.tvTakePhoto).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvChooseImage).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvDismiss).setOnClickListener(this);
        viewModel.getEnclosure(bean.getCsid());
    }

    /**
     * ??????????????????
     */
    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            new RxPermissions(AqqrpbDetailActivity.this).request(WRITE_READ_PERMISSION).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        showUploadImagePopWindow();
                    } else {
                        ToastUtils.showShort("??????????????????????????????????????????????????????");
                    }
                }
            });
        }
    };

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
                            ylList = selectList;
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
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(AqqrpbDetailActivity.this, media.getPath());
                                        media.setWidth(videoExtraInfo.getWidth());
                                        media.setHeight(videoExtraInfo.getHeight());
                                    }
                                }

                                fileList.add(new File(media.getRealPath()));
                                // TODO ????????????PictureSelectorExternalUtils.getExifInterface();??????????????????????????????????????????????????????????????????????????????
                            }
                            mAdapter.setList(selectList);
                            mAdapter.setFileList(fileList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.mDatas.observe(this, new Observer<List<AqqrpbBean.RowsDTO>>() {
            @Override
            public void onChanged(List<AqqrpbBean.RowsDTO> rowsDTOS) {
                mDatas = rowsDTOS;
                adapter.setNewData(mDatas);
            }
        });

        /**
         * ??????
         */
        viewModel.submitEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (null != bean) {
                    viewModel.workingFaceUpdate(bean.getCsid(), StringUtils.setTextStrComma(safeCheckTermDatas), fileList);
                }
            }
        });

        viewModel.enclosureEvent.observe(this, new Observer<List<EnclosureBean.DataDTO>>() {
            @Override
            public void onChanged(List<EnclosureBean.DataDTO> dataDTOS) {
                if (dataDTOS.isEmpty()) {
                    return;
                }
                for (int i = 0; i < dataDTOS.size(); i++) {
                    LocalMedia localMedia = new LocalMedia();
                    String filePath = Constants.IP_URL + dataDTOS.get(i).getAttachmenturl();
                    localMedia.setRealPath(filePath);
                    long time = System.currentTimeMillis();
                    localMedia.setId(time);
                    localMedia.setPath(Constants.LOCALADDRESS + time);
                    imgList.add(filePath);
//                    File file = new File(filePath);
                    exFiles.add(localMedia);
//                    gdList.add(localMedia);
                    fileListReady.add(new File(filePath));
                }
                ylList = exFiles;
                getPermission(imgList);
//                mAdapter.setFileList(fileListReady);
//                mAdapter.setFileListReady(fileListReady);
                mAdapter.setList(exFiles);
//                mAdapter.setExFiles(exFiles);
                mAdapter.notifyDataSetChanged();
            }
        });

        viewModel.workingFaceUpdateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    /**
     * ????????????????????????
     */
    public void getPermission(List<String> filePaths) {
        new RxPermissions(this).request(WRITE_READ_PERMISSION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    DonwloadSaveImg.donwloadImg(AqqrpbDetailActivity.this, filePaths);
                } else {
                    ToastUtils.showShort("??????????????????????????????????????????????????????");
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TO_MULTI_CODE) {
                String areaName = data.getStringExtra("areaName");
            } else if (requestCode == Constants.YHTX_CODE) {
                mDatas.get(selectIndex).setConfirmresult(Constants.QRJG002);
                mDatas.get(selectIndex).setClickble(false);
                safeCheckTermDatas.add(mDatas.get(selectIndex).getDetailid());
                adapter.setNewData(mDatas);
            }
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void showToRiskRegister() {
        checkToHiddenPop = new CustomPopWindow.PopupWindowBuilder(AqqrpbDetailActivity.this)
                .setView(checkToHiddenView)
                .setOutsideTouchable(false)
                .setFocusable(false)
                .enableBackgroundDark(true)
                .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), WRAP_CONTENT)
                .create()
                .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

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
                        PictureSelectorManager.openCamera(AqqrpbDetailActivity.this, MAXSELECTNUM, mAdapter.getData(), mAdapter, new MyResultCallback(mAdapter));
                    } else if (viewId == R.id.tvChooseImage) {
//                        ylList.removeAll(gdList);
                        PictureSelectorManager.openAlbum(AqqrpbDetailActivity.this,
                                PictureMimeType.ofImage(), MAXSELECTNUM, mAdapter.getData(), launcherResult);
                    }
                } else {
                    ToastUtils.showShort("??????????????????????????????????????????????????????");
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    private class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            fileList.clear();
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
                mAdapter.setList(result);
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
        }
    }

    @Override
    public void onClick(View view) {
        viewId = view.getId();
        if (viewId == R.id.checkToHiddenCancel) {
            checkToHiddenPop.dissmiss();
            adapter.notifyItemChanged(selectIndex);
        } else if (viewId == R.id.toYbHidden) {
            checkToHiddenPop.dissmiss();
            Intent intent = new Intent(this, RiskRegisterActivity.class);
            intent.putExtra("tag", "0");
            intent.putExtra("checkUnqualified", "1");
            intent.putExtra("checkTypeTv", "??????");
            startActivityForResult(intent, Constants.YHTX_CODE);
        } else if (viewId == R.id.toZdHidden) {
            checkToHiddenPop.dissmiss();
            Intent intent = new Intent(this, RiskRegisterActivity.class);
            intent.putExtra("tag", "1");
            intent.putExtra("checkUnqualified", "1");
            intent.putExtra("checkTypeTv", "??????");
            startActivityForResult(intent, Constants.YHTX_CODE);
        } else if (viewId == R.id.tvTakePhoto) {
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