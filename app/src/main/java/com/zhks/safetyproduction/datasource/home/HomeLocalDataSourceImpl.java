package com.zhks.safetyproduction.datasource.home;

import com.zhks.safetyproduction.datasource.mine.MineLocalDataSourceImpl;

public class HomeLocalDataSourceImpl implements HomeLocalDataSource{
    private volatile static HomeLocalDataSourceImpl INSTANCE = null;

    public static HomeLocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (MineLocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HomeLocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HomeLocalDataSourceImpl() {
        //数据库Helper构建
    }
}
