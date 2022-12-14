package com.zhks.safetyproduction.manager;

import static com.zhks.safetyproduction.constants.Constants.DB_NAME;
import com.zhks.safetyproduction.BuildConfig;
import com.zhks.safetyproduction.app.MyApplication;

import org.greenrobot.greendao.query.QueryBuilder;

import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * 数据库管理
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();

    private MyApplication mApplication;

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManager manager = new DaoManager();
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    /**
     * 单例模式获得操作数据库对象
     */
    public static DaoManager getInstance() {
        return manager;
    }

    private DaoManager() {
        setDebug();
    }

    public void init(MyApplication application, DaoMaster daoMaster, DaoSession daoSession, DaoMaster.DevOpenHelper helper) {
        this.mApplication = application;
        this.mDaoMaster = daoMaster;
        this.mDaoSession = daoSession;
        this.mHelper = helper;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     */
    public DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mApplication, DB_NAME, null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     */
    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug() {
        if (BuildConfig.DEBUG) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection() {
        closeDaoSession();
    }

    public void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    public void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }
}
