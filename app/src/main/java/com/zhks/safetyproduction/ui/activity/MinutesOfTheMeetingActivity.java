package com.zhks.safetyproduction.ui.activity;


import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static com.zhks.safetyproduction.constants.Constants.MAXSELECTNUM;
import static com.zhks.safetyproduction.constants.Constants.TO_CYLDMUTIL_PERSONNEL_CODE;
import static com.zhks.safetyproduction.constants.Constants.TO_YHRYMUTIL_PERSONNEL_CODE;
import static com.zhks.safetyproduction.constants.Constants.TO_ZCRMUTIL_PERSONNEL_CODE;
import static com.zhks.safetyproduction.constants.PermissionConstants.permissionsPhotoGroup;
import static com.zhks.safetyproduction.utils.DateUtils.formatter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.MediaUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.adapter.CustomPopAdapter;
import com.zhks.safetyproduction.adapter.GridImageAdapter;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.constants.Constants;
import com.zhks.safetyproduction.entity.DeptUserBean;
import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.databinding.ActivityMinutesOfTheMeetingBinding;
import com.zhks.safetyproduction.entity.CurrentUserBean;
import com.zhks.safetyproduction.entity.CustomPopBean;
import com.zhks.safetyproduction.entity.MeetingRecordBean;
import com.zhks.safetyproduction.manager.DatePickerManager;
import com.zhks.safetyproduction.manager.PictureSelectorManager;
import com.zhks.safetyproduction.utils.DateUtils;
import com.zhks.safetyproduction.utils.DensityUtil;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.MinutesOfTheMeetingModel;
import com.zhks.safetyproduction.viewmodel.PersonnelQueryItemViewModel;
import com.zhks.safetyproduction.viewmodel.PersonnelQueryViewModel;
import com.zhks.safetyproduction.wight.FullyGridLayoutManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MinutesOfTheMeetingActivity extends BaseActivity<ActivityMinutesOfTheMeetingBinding, MinutesOfTheMeetingModel>
        implements View.OnClickListener {
    private View shiftPopView;
    private View hostPopView;
    private View participantsPopView;
    private View leadersPopView;
    private CustomPopWindow shiftPop;
    private CustomPopWindow hostPop;
    private CustomPopWindow participantsPop;
    private CustomPopWindow leadersPop;
    private RecyclerView shiftRv;
    private RecyclerView hostRv;
    private RecyclerView participantsRv;
    private RecyclerView leadersRv;
    private CustomPopAdapter hostAdapter;
    private CustomPopAdapter participantsAdapter;
    private CustomPopAdapter leadersAdapter;
    private CustomPopAdapter shiftAdapter;
    private List<CustomPopBean> shiftDatas = new ArrayList<>();
    private CustomPopWindow uploadImageWindow;
    private View uploadImageView;
    private static GridImageAdapter mAdapter;
    private ActivityResultLauncher<Intent> launcherResult;
    private int viewId;
    private List<CurrentUserBean.DataDTO> hostDatas = new ArrayList<>();
    private List<CurrentUserBean.DataDTO> hostSearchDatas = new ArrayList<>();
    private List<CurrentUserBean.DataDTO> participantsDatas = new ArrayList<>();
    private List<CurrentUserBean.DataDTO> participantsSearchDatas = new ArrayList<>();
    private List<CurrentUserBean.DataDTO> leadersDatas = new ArrayList<>();
    private List<CurrentUserBean.DataDTO> leadersSearchDatas = new ArrayList<>();
    private TextView multiSubmit;
    private TextView participantsSubmit;
    private TextView leadersSubmit;
    private DatimeEntity startDatimeEntiy;
    private DatimeEntity endDatimeEntiy;
    private static List<File> fileList = new ArrayList<>();
    private Handler handler = new Handler();
    private EditText hostPersonnel_search_etv;
    private SmartRefreshLayout hostRefreshlayout;
    private SmartRefreshLayout participantsRefreshlayout;
    private SmartRefreshLayout leadersRefreshlayout;
    private int hostRvPage = 1;
    private int participantsRvPage = 1;
    private int leadersRvPage = 1;
    private String selectHostMans; //选中

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_minutes_of_the_meeting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MinutesOfTheMeetingModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(MinutesOfTheMeetingModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
        ScreenUtils.setStatusBarColor(this, R.color.main_color);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setRightTextVisible(View.VISIBLE);
        viewModel.setRightText("提交");
        viewModel.setTitleText("班前会议记录");
    }

    @Override
    public void initData() {
        super.initData();
        startDatimeEntiy = DatimeEntity.now();
        endDatimeEntiy = DatimeEntity.now();
        shiftPopView = View.inflate(this, R.layout.spinner_pop_layout, null);
        hostPopView = View.inflate(this, R.layout.multiple_choice_layout, null);
        participantsPopView = View.inflate(this, R.layout.participants_choice_layout, null);
        participantsRefreshlayout = participantsPopView.findViewById(R.id.participantsRefreshlayout);
        leadersPopView = View.inflate(this, R.layout.leaders_choice_layout, null);
        leadersRefreshlayout = leadersPopView.findViewById(R.id.leadersRefreshlayout);
        multiSubmit = hostPopView.findViewById(R.id.multi_submit);
        multiSubmit.setOnClickListener(this);
        participantsSubmit = participantsPopView.findViewById(R.id.participants_submit);
        leadersSubmit = leadersPopView.findViewById(R.id.leaders_submit);
        participantsSubmit.setOnClickListener(this);
        leadersSubmit.setOnClickListener(this);
        hostPersonnel_search_etv = hostPopView.findViewById(R.id.hostPersonnel_search_etv);
        hostRefreshlayout = hostPopView.findViewById(R.id.hostRefreshlayout);
        hostRv = hostPopView.findViewById(R.id.multi_choice_rv);
        participantsRv = participantsPopView.findViewById(R.id.participants_choice_rv);
        leadersRv = leadersPopView.findViewById(R.id.leaders_choice_rv);
        uploadImageView = View.inflate(this, R.layout.upload_image_pop_layout, null);
        uploadImageView.findViewById(R.id.tvTakePhoto).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvChooseImage).setOnClickListener(this);
        uploadImageView.findViewById(R.id.tvDismiss).setOnClickListener(this);
        shiftRv = shiftPopView.findViewById(R.id.spinner_pop_rv);
        shiftRv.setLayoutManager(new LinearLayoutManager(this));
        hostRv.setLayoutManager(new LinearLayoutManager(this));
        participantsRv.setLayoutManager(new LinearLayoutManager(this));
        leadersRv.setLayoutManager(new LinearLayoutManager(this));
        shiftAdapter = new CustomPopAdapter(R.layout.safe_check_type_item_layout, shiftDatas);
        hostAdapter = new CustomPopAdapter(R.layout.multi_choice_item_layout, hostDatas);
        participantsAdapter = new CustomPopAdapter(R.layout.multi_choice_item_layout, participantsDatas);
        leadersAdapter = new CustomPopAdapter(R.layout.multi_choice_item_layout, leadersDatas);
        shiftRv.setAdapter(shiftAdapter);
        hostRv.setAdapter(hostAdapter);
        participantsRv.setAdapter(participantsAdapter);
        leadersRv.setAdapter(leadersAdapter);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.meetingImgRv.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        binding.meetingImgRv.setAdapter(mAdapter);
        // 注册需要写在onCreate或Fragment onAttach里，否则会报java.lang.IllegalStateException异常
        launcherResult = createActivityResultLauncher();

        shiftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                binding.shift.setText(shiftDatas.get(position).getMessage());
                if (null != shiftPop) {
                    shiftPop.dissmiss();
                }
            }
        });

//        hostAdapter.setCheckBoxClick(new CustomPopAdapter.CheckBoxClick() {
//            @Override
//            public void setCheckBoxClick(CurrentUserBean.DataDTO dataDTO, int position) {
//                if (dataDTO.isCheck()) {
//                    dataDTO.setCheck(false);
//                } else {
//                    dataDTO.setCheck(true);
//                }
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        hostAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        });

//        //主持人搜索框监听
//        hostPersonnel_search_etv.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                search(charSequence.toString().trim(), hostSearchDatas, hostDatas, hostAdapter);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

//        //主持人列表刷新监听
//        hostRefreshlayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                hostDatas.clear();
//                hostRvPage = 1;
//                viewModel.getHostListUser(String.valueOf(hostRvPage));
//            }
//        });
//        //主持人列表加载监听
//        hostRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                hostRvPage++;
//                viewModel.getHostListUser(String.valueOf(hostRvPage));
//            }
//        });
//
//        //与会人员列表刷新监听
//        participantsRefreshlayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                participantsDatas.clear();
//                participantsRvPage = 1;
//                viewModel.getParticipantsListUser(String.valueOf(participantsRvPage));
//            }
//        });
//        //与会人员列表加载监听
//        participantsRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                participantsRvPage++;
//                viewModel.getParticipantsListUser(String.valueOf(participantsRvPage));
//            }
//        });
//
//        //参与领导列表刷新监听
//        leadersRefreshlayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                leadersDatas.clear();
//                leadersRvPage = 1;
//                viewModel.getLeadersListUser(String.valueOf(leadersRvPage));
//            }
//        });
//        //参与领导列表加载监听
//        leadersRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                leadersRvPage++;
//                viewModel.getLeadersListUser(String.valueOf(leadersRvPage));
//            }
//        });
//
//
//        viewModel.getHostListUser(String.valueOf(hostRvPage));
//        viewModel.getParticipantsListUser(String.valueOf(participantsRvPage));
//        viewModel.getLeadersListUser(String.valueOf(leadersRvPage));
        getData();
    }

    private void search(String str, List<CurrentUserBean.DataDTO> searchDatas, List<CurrentUserBean.DataDTO> userDatas, CustomPopAdapter adapter) {
        if (TextUtils.isEmpty(str)) {
            hostRefreshlayout.setEnableRefresh(true);
            hostRefreshlayout.setEnableLoadMore(true);
            adapter.setNewData(userDatas);
        } else {
            hostRefreshlayout.setEnableRefresh(false);
            hostRefreshlayout.setEnableLoadMore(false);
            if (!searchDatas.isEmpty()) {
                searchDatas.clear();
            }
            if (!userDatas.isEmpty()) {
                for (CurrentUserBean.DataDTO entity : userDatas) {
                    try {
                        if (entity.getUsername().contains(str)) {
                            searchDatas.add(entity);
                            adapter.setNewData(searchDatas);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    private void getData() {
        String str[] = {"早班", "中班", "晚班"};
        for (int i = 0; i < str.length; i++) {
            CustomPopBean bean = new CustomPopBean();
            bean.setMessage(str[i]);
            shiftDatas.add(bean);
        }

        binding.shift.setText(shiftDatas.get(0).getMessage());
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        //提交
        viewModel.rightTextEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(binding.host.getText())) {
                    ToastUtils.showShort("请选择主持人");
                    return;
                }

                if (TextUtils.isEmpty(binding.participants.getText())) {
                    ToastUtils.showShort("请选择与会人员");
                    return;
                }
                MeetingRecordBean bean = new MeetingRecordBean();
                bean.setTeamname(binding.shift.getText().toString());
                bean.setStartdate(binding.startDate.getText().toString());
                bean.setEnddate(binding.endDate.getText().toString());
                bean.setCreateunit(PersonInfoManager.getInstance().getDeptid());
                bean.setZcr(binding.host.getText().toString());
                bean.setYhry(binding.participants.getText().toString());
                bean.setCyld(binding.leaders.getText().toString());
                bean.setDqgzjl(binding.workRecord.getText().toString());
                bean.setZysx(binding.safetyProtectiveMeasures.getText().toString());
                bean.setQtnr(binding.otherContent.getText().toString());
                String modelJson = JSON.toJSONString(bean);
                viewModel.uploadMeetingRecord(modelJson, fileList);
            }
        });

        viewModel.startDateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
//                DatePickerManager.onYearMonthDay(MinutesOfTheMeetingActivity.this, true, startDateEntity, new OnDatePickedListener() {
//                    @Override
//                    public void onDatePicked(int year, int month, int day) {
//                        long selectStartDate = Long.parseLong(DateUtils.date2TimeStamp(year + "-" + month + "-" + day, "yyyy-MM-dd"));
//                        long selectEndDate = Long.parseLong(DateUtils.date2TimeStamp(binding.endDate.getText().toString(), "yyyy-MM-dd"));
//                        if (selectEndDate < selectStartDate) {
//                            ToastUtils.showShort("请选择正确的日期");
//                            return;
//                        }
//                        binding.startDate.setText(year + "-" + month + "-" + day);
//                        try {
//                            Date date = DateUtils.formatterYmd.parse(year + "-" + month + "-" + day);
//                            startDateEntity = DateEntity.target(date);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
                DatePickerManager.onYearMonthDayTime(MinutesOfTheMeetingActivity.this, false, startDatimeEntiy, new OnDatimePickedListener() {
                    @Override
                    public void onDatimePicked(int year, int month, int day, int hour, int minute, int second) {
                        String time = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                        long selectStartDate = Long.parseLong(DateUtils.date2TimeStamp(time, "yyyy-MM-dd HH:mm"));
                        long selectEndDate = Long.parseLong(DateUtils.date2TimeStamp(binding.endDate.getText().toString(), "yyyy-MM-dd HH:mm"));
                        if (selectEndDate < selectStartDate) {
                            ToastUtils.showShort("请选择正确的日期");
                            return;
                        }
                        String sjcTime = DateUtils.date2TimeStamp(time, formatter);
                        binding.startDate.setText(DateUtils.timeStamp2Date(sjcTime, formatter));
                        startDatimeEntiy = DatePickerManager.DatimeEntityTarget(year, month, day, hour, minute);
                    }
                });
            }
        });

        viewModel.endDateEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                DatePickerManager.onYearMonthDayTime(MinutesOfTheMeetingActivity.this, false, endDatimeEntiy, new OnDatimePickedListener() {
                    @Override
                    public void onDatimePicked(int year, int month, int day, int hour, int minute, int second) {
                        String time = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                        long selectStartDate = Long.parseLong(DateUtils.date2TimeStamp(binding.startDate.getText().toString(), "yyyy-MM-dd HH:mm"));
                        long selectEndDate = Long.parseLong(DateUtils.date2TimeStamp(time, "yyyy-MM-dd HH:mm"));
                        if (selectEndDate < selectStartDate) {
                            ToastUtils.showShort("请选择正确的日期");
                            return;
                        }
                        String sjcTime = DateUtils.date2TimeStamp(time, formatter);
                        binding.endDate.setText(DateUtils.timeStamp2Date(sjcTime, formatter));
                        endDatimeEntiy = DatePickerManager.DatimeEntityTarget(year, month, day, hour, minute);
                    }
                });
            }
        });


        viewModel.shiftEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                showShiftPop();
            }
        });

        viewModel.hostEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
//                showHostPop();
                Intent intent = new Intent(MinutesOfTheMeetingActivity.this, PersonnelMutilQueryActivity.class);
                startActivityForResult(intent, TO_ZCRMUTIL_PERSONNEL_CODE);
            }
        });

        viewModel.participantsEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
//                showParticipantsPop();
                Intent intent = new Intent(MinutesOfTheMeetingActivity.this, PersonnelMutilQueryActivity.class);
                startActivityForResult(intent, TO_YHRYMUTIL_PERSONNEL_CODE);
            }
        });

        viewModel.leadersEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
//                showLeadersPop();
                Intent intent = new Intent(MinutesOfTheMeetingActivity.this, PersonnelMutilQueryActivity.class);
                startActivityForResult(intent, TO_CYLDMUTIL_PERSONNEL_CODE);
            }
        });

        viewModel.hostUserEvent.observe(this, new Observer<List<CurrentUserBean.DataDTO>>() {
            @Override
            public void onChanged(List<CurrentUserBean.DataDTO> dataDTOS) {
                hostDatas.addAll(dataDTOS);
                hostAdapter.setNewData(hostDatas);
                leadersDatas = dataDTOS;
                leadersAdapter.setNewData(leadersDatas);
            }
        });

        viewModel.ParticipantsUserEvent.observe(this, new Observer<List<CurrentUserBean.DataDTO>>() {
            @Override
            public void onChanged(List<CurrentUserBean.DataDTO> dataDTOS) {
                participantsDatas.addAll(dataDTOS);

            }
        });

        viewModel.LeadersUserEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {

            }
        });
    }

    private void showHostPop() {
        if (null == hostPop) {
            hostPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(hostPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(50), DensityUtil.getScreenHeight(this) / 2)
                    .create()
                    .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            hostPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }


    private void showParticipantsPop() {
        if (null == participantsPop) {
            participantsPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(participantsPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), DensityUtil.getScreenHeight(this) / 2)
                    .create()
                    .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            participantsPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    private void showLeadersPop() {
        if (null == leadersPop) {
            leadersPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(leadersPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(DensityUtil.getScreenWidth(this) - DensityUtil.dip2px(100), DensityUtil.getScreenHeight(this) / 2)
                    .create()
                    .showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } else {
            leadersPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    private void showShiftPop() {
        if (null == shiftPop) {
            shiftPop = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(shiftPopView)
                    .setOutsideTouchable(true)
                    .setFocusable(false)
                    .size(binding.shift.getWidth(), WRAP_CONTENT)
                    .create()
                    .showAsDropDown(binding.shift);
        } else {
            shiftPop.showAsDropDown(binding.shift);
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
                                        MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(MinutesOfTheMeetingActivity.this, media.getPath());
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

    @SuppressLint("CheckResult")
    private void chooseImage() {
        new RxPermissions(this).request(permissionsPhotoGroup).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    if (viewId == R.id.tvTakePhoto) {
                        PictureSelectorManager.openCamera(MinutesOfTheMeetingActivity.this, MAXSELECTNUM, mAdapter.getData(), mAdapter, new MyResultCallback(mAdapter));
                    } else if (viewId == R.id.tvChooseImage) {
                        PictureSelectorManager.openAlbum(MinutesOfTheMeetingActivity.this,
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
        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == TO_ZCRMUTIL_PERSONNEL_CODE) {
                binding.host.setText(data.getStringExtra("mans"));
            } else if (requestCode == TO_YHRYMUTIL_PERSONNEL_CODE) {
                binding.participants.setText(data.getStringExtra("mans"));
            } else if (requestCode == TO_CYLDMUTIL_PERSONNEL_CODE) {
                binding.leaders.setText(data.getStringExtra("mans"));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (launcherResult != null) {
            launcherResult.unregister();
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
        } else if (viewId == R.id.multi_submit) {
            List<CurrentUserBean.DataDTO> list = new ArrayList<>();
            for (int i = 0; i < hostDatas.size(); i++) {
                if (hostDatas.get(i).isCheck()) {
                    list.add(hostDatas.get(i));
                }
            }
            setChoiceText(list, binding.host, hostPop);
        } else if (viewId == R.id.participants_submit) {
            List<CurrentUserBean.DataDTO> list = new ArrayList<>();
            for (int i = 0; i < participantsDatas.size(); i++) {
                if (participantsDatas.get(i).isCheck()) {
                    list.add(participantsDatas.get(i));
                }
            }
            setChoiceText(list, binding.participants, participantsPop);
        } else if (viewId == R.id.leaders_submit) {
            List<CurrentUserBean.DataDTO> list = new ArrayList<>();
            for (int i = 0; i < leadersDatas.size(); i++) {
                if (leadersDatas.get(i).isCheck()) {
                    list.add(leadersDatas.get(i));
                }
            }
            setChoiceText(list, binding.leaders, leadersPop);
        }
    }

    private void setChoiceText(List<CurrentUserBean.DataDTO> list, TextView textView, CustomPopWindow popWindow) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                builder.append(list.get(i).getUsername() + ",");
            } else {
                builder.append(list.get(i).getUsername());
            }
        }
        textView.setText(builder);

        if (null != popWindow) {
            popWindow.dissmiss();
        }
    }
}