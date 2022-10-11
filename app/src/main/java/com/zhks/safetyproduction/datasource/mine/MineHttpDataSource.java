package com.zhks.safetyproduction.datasource.mine;

import com.zhks.safetyproduction.entity.UserBean;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

public interface MineHttpDataSource {
    Observable<UserBean> login(String loginname, String pwd);
}
