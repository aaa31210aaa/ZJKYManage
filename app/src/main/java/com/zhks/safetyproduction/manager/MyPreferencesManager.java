package com.zhks.safetyproduction.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhks.safetyproduction.constants.Constants;

import me.goldze.mvvmhabit.utils.SPUtils;

public class MyPreferencesManager {
    private static volatile MyPreferencesManager instance;

    private MyPreferencesManager() {
    }

    public static MyPreferencesManager getInstance() {
        if (instance == null) {
            synchronized (MyPreferencesManager.class) {
                if (instance == null) {
                    instance = new MyPreferencesManager();
                }
            }
        }
        return instance;
    }

    /**
     * 设置隐私协议是否同意
     *
     * @param value 是否同意
     */
    public void setAgreePrivacyAgreement(boolean value) {
        SPUtils.getInstance().put(Constants.AGREEMENT_ACCEPTED, value);
    }

    /**
     * 是否同意了隐私协议
     *
     * @return true 已经同意；false 还没有同意
     */
    public boolean hasAgreePrivacyAgreement() {
        return SPUtils.getInstance().getBoolean(Constants.AGREEMENT_ACCEPTED);
    }
}
