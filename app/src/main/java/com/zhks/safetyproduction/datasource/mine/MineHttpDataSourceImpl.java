package com.zhks.safetyproduction.datasource.mine;


import com.zhks.safetyproduction.manager.PersonInfoManager;
import com.zhks.safetyproduction.entity.UserBean;
import com.zhks.safetyproduction.http.service.ApiService;

import io.reactivex.Observable;

public class MineHttpDataSourceImpl implements MineHttpDataSource {
    private ApiService apiService;
    private volatile static MineHttpDataSourceImpl INSTANCE = null;

    public static MineHttpDataSourceImpl getInstance(ApiService apiService) {
        if (INSTANCE == null) {
            synchronized (MineHttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MineHttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private MineHttpDataSourceImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<UserBean> login(String account, String pwd) {
        return apiService.login(PersonInfoManager.getInstance().getToken(), account, pwd);
    }
}
