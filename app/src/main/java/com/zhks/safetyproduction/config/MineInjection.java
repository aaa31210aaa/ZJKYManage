package com.zhks.safetyproduction.config;


import com.zhks.safetyproduction.datasource.mine.MineHttpDataSource;
import com.zhks.safetyproduction.datasource.mine.MineHttpDataSourceImpl;
import com.zhks.safetyproduction.datasource.mine.MineLocalDataSource;
import com.zhks.safetyproduction.datasource.mine.MineLocalDataSourceImpl;
import com.zhks.safetyproduction.http.RetrofitClient;
import com.zhks.safetyproduction.http.service.ApiService;
import com.zhks.safetyproduction.model.MineModel;

/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。
 */
public class MineInjection {
    public static MineModel provideRepository() {
        //网络API服务
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        //网络数据源
        MineHttpDataSource httpDataSource = MineHttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        MineLocalDataSource localDataSource = MineLocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return MineModel.getInstance(httpDataSource, localDataSource);
    }
}
