package com.zhks.safetyproduction.config;

import com.zhks.safetyproduction.datasource.home.HomeHttpDataSource;
import com.zhks.safetyproduction.datasource.home.HomeHttpDataSourceImpl;
import com.zhks.safetyproduction.datasource.home.HomeLocalDataSource;
import com.zhks.safetyproduction.datasource.home.HomeLocalDataSourceImpl;
import com.zhks.safetyproduction.http.RetrofitClient;
import com.zhks.safetyproduction.http.service.ApiService;
import com.zhks.safetyproduction.model.HomeModel;

public class HomeInjection {
    public static HomeModel provideRepository() {
        //网络API服务
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        //网络数据源
        HomeHttpDataSource httpDataSource = HomeHttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        HomeLocalDataSource localDataSource = HomeLocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return HomeModel.getInstance(httpDataSource, localDataSource);
    }
}
