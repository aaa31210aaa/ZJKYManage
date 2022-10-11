package com.zhks.safetyproduction.model;

import androidx.annotation.NonNull;


import com.zhks.safetyproduction.datasource.mine.MineHttpDataSource;
import com.zhks.safetyproduction.datasource.mine.MineLocalDataSource;
import com.zhks.safetyproduction.entity.UserBean;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据
 * Created by tanggongwen on 2019/9/17.
 */
public class MineModel extends BaseModel implements MineHttpDataSource, MineLocalDataSource {
    private volatile static MineModel INSTANCE = null;
    private final MineHttpDataSource mHttpDataSource;

    private final MineLocalDataSource mLocalDataSource;

    private MineModel(@NonNull MineHttpDataSource httpDataSource,
                      @NonNull MineLocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static MineModel getInstance(MineHttpDataSource httpDataSource,
                                        MineLocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (MineModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MineModel(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public Observable<UserBean> login(String loginname,String loginpwd) {
        return mHttpDataSource.login(loginname,loginpwd);
    }
}
