package com.zhks.safetyproduction.datasource.mine;


public class MineLocalDataSourceImpl implements MineLocalDataSource {
    private volatile static MineLocalDataSourceImpl INSTANCE = null;

    public static MineLocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (MineLocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MineLocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private MineLocalDataSourceImpl() {
        //数据库Helper构建
    }


}
