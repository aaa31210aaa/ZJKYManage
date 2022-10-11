package com.zhks.safetyproduction.wight;


import static com.luck.picture.lib.thread.PictureThreadUtils.runOnUiThread;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.umeng.message.PushAgent;
import com.umeng.message.api.UPushRegisterCallback;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.manager.MyPreferencesManager;
import com.zhks.safetyproduction.manager.PushHelper;

public class PrivacyWindow {
    private Context context;
    private View contentView;
    private CustomPopWindow popupWindow;

    public PrivacyWindow(Context context) {
        this.context = context;
    }

    public void initPrivacyWindow() {
        contentView = View.inflate(context, R.layout.layout_privacy_window, null);
        contentView.findViewById(R.id.tvConfirm).setOnClickListener(onClickListener);
        contentView.findViewById(R.id.refuse).setOnClickListener(onClickListener);
        TextView tip = contentView.findViewById(R.id.tvContent);
        tip.append(context.getResources().getString(R.string.privacy_tip1));
        tip.append(context.getResources().getString(R.string.privacy_tip2));
        tip.append(context.getResources().getString(R.string.privacy_tip3));
        tip.append(context.getResources().getString(R.string.privacy_tip4));
        tip.append(context.getResources().getString(R.string.privacy_tip5));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvConfirm) {
                popupWindow.dissmiss();
                //用户点击隐私协议同意按钮后，初始化PushSDK
                MyPreferencesManager.getInstance().setAgreePrivacyAgreement(true);
                PushHelper.init(context.getApplicationContext());
                PushAgent.getInstance(context.getApplicationContext()).register(new UPushRegisterCallback() {
                    @Override
                    public void onSuccess(final String deviceToken) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }

                    @Override
                    public void onFailure(String code, String msg) {
                        Log.d("MainActivity", "code:" + code + " msg:" + msg);
                    }
                });
            } else if (v.getId() == R.id.refuse) {
                popupWindow.dissmiss();
            }
        }
    };

    public void showPrivacyPopWindow(View rootView) {
        //创建并显示popWindow
        popupWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setOutsideTouchable(false)
                .setFocusable(false)
                .setBgDarkAlpha(0.5f) // 控制亮度
                .size(context.getResources().getDimensionPixelSize(R.dimen.privacy_dialog_width), context.getResources().getDimensionPixelSize(R.dimen.privacy_dialog_height))
                .create()
                .showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }
}
