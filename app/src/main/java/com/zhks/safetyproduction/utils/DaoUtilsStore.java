package com.zhks.safetyproduction.utils;

import com.zhks.safetyproduction.entity.HiddenDBean;
import com.zhks.safetyproduction.entity.QuestionBean;
import com.zhks.safetyproduction.manager.DaoManager;

import greendao.HiddenDBeanDao;
import greendao.QuestionBeanDao;

public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance = new DaoUtilsStore();
    private CommonDaoUtils<QuestionBean> mQuestionDaoUtils;
    private CommonDaoUtils<HiddenDBean> mHiddenDaoUtils;

    public static DaoUtilsStore getInstance() {
        return instance;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();
        QuestionBeanDao _QuestionDao = mManager.getDaoSession().getQuestionBeanDao();
        HiddenDBeanDao _HiddenDao = mManager.getDaoSession().getHiddenDBeanDao();
        mQuestionDaoUtils = new CommonDaoUtils<>(QuestionBean.class, _QuestionDao);
        mHiddenDaoUtils = new CommonDaoUtils<>(HiddenDBean.class, _HiddenDao);
    }

    public CommonDaoUtils<QuestionBean> getQuestionDaoUtils() {
        return mQuestionDaoUtils;
    }

    public CommonDaoUtils<HiddenDBean> getHiddenDaoUtils() {
        return mHiddenDaoUtils;
    }
}
