package com.zhks.safetyproduction.app;


import static com.zhks.safetyproduction.constants.Constants.DB_NAME;

import android.database.sqlite.SQLiteDatabase;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.zhks.safetyproduction.manager.DaoManager;
import com.zhks.safetyproduction.manager.PushHelper;

import greendao.DaoMaster;
import greendao.DaoSession;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;
import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication extends BaseApplication {
    private static DaoSession mDaoSession;
    private static greendao.DaoMaster daoMaster;

    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启日志打印
        KLog.init(true);
        //配置全局异常崩溃操作
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(false) //是否启动全局异常捕获
                .showErrorDetails(false) //是否显示错误详细信息
                .showRestartButton(false) //是否显示重启按钮
                .trackActivities(false) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
        AutoSizeConfig.getInstance()
                .setDesignWidthInDp(360)
                .setDesignHeightInDp(640);
        initGreenDao();
        initUmengSDK();
    }

    /**
     * 初始化友盟SDK
     */
    private void initUmengSDK() {
        //日志开关
        UMConfigure.setLogEnabled(true);
        //预初始化
        PushHelper.preInit(this);
        //是否同意隐私政策 //上到市场 就需要这一句
//        boolean agreed = Pers;
//        if (!agreed) {
//            return;
//        }
        boolean isMainProcess = UMUtils.isMainProgress(this);
        if (isMainProcess) {
            //启动优化：建议在子线程中执行初始化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PushHelper.init(getApplicationContext());
                }
            }).start();
        } else {
            //若不是主进程（":channel"结尾的进程），直接初始化sdk，不可在子线程中执行
            PushHelper.init(getApplicationContext());
        }
    }

    private void initGreenDao() {
        greendao.DaoMaster.DevOpenHelper helper = new greendao.DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new greendao.DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        DaoManager.getInstance().init(this, daoMaster, mDaoSession, helper);
    }

    public static DaoSession getDaoSessions() {
        return mDaoSession;
    }

    public static DaoMaster getDaoMasters() {
        return daoMaster;
    }

}
