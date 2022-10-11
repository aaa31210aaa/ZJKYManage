package com.zhks.safetyproduction.manager;


import static com.zhks.safetyproduction.utils.SystemUtil.getANDROID_ID;


import com.zhks.safetyproduction.constants.Constants;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.utils.SPUtils;


public class PersonInfoManager {
    private static PersonInfoManager instance;
    private Map headMap;

    public static PersonInfoManager getInstance() {
        if (instance == null) {
            synchronized (PersonInfoManager.class) {
                if (instance == null) {
                    instance = new PersonInfoManager();
                }
            }
        }
        return instance;
    }

    public Map getHeadMap() {
        headMap = new HashMap();
        headMap.put("deviceId", getANDROID_ID());
        return headMap;
    }

    /**
     * 保存用户id
     */
    public void setToken(String token) {
        SPUtils.getInstance().put(Constants.USER_TOKEN, token);
    }

    /**
     * 获取用户id
     */
    public String getToken() {
        return SPUtils.getInstance().getString(Constants.USER_TOKEN, "");
    }


    /**
     * 保存用户id
     */
    public void setUserId(String userId) {
        SPUtils.getInstance().put(Constants.USER_ID, userId);
    }

    /**
     * 获取用户id
     */
    public String getUserId() {
        return SPUtils.getInstance().getString(Constants.USER_ID, "");
    }

    /**
     * 保存用户名称
     */
    public void setUserName(String userName) {
        SPUtils.getInstance().put(Constants.USER_NAME, userName);
    }

    /**
     * 获取用户名称
     */
    public String getUserName() {
        return SPUtils.getInstance().getString(Constants.USER_NAME, "");
    }

    /**
     * 保存组织机构id
     */
    public void setDeptid(String deptid) {
        SPUtils.getInstance().put(Constants.DEPT_ID, deptid);
    }

    /**
     * 获取组织机构id
     */
    public String getDeptid() {
        return SPUtils.getInstance().getString(Constants.DEPT_ID, "");
    }

    /**
     * 保存组织机构名称
     */
    public void setDeptName(String deptName) {
        SPUtils.getInstance().put(Constants.DEPT_NAME, deptName);
    }

    /**
     * 获取组织机构名称
     */
    public String getDeptName() {
        return SPUtils.getInstance().getString(Constants.DEPT_NAME, "");
    }

    /**
     * 保存用户类型
     */
    public void setUserType(String userType) {
        SPUtils.getInstance().put(Constants.USER_TYPE, userType);
    }

    /**
     * 获取用户类型
     */
    public String getUserType() {
        return SPUtils.getInstance().getString(Constants.USER_TYPE, "");
    }

    /**
     * 获取用户是否为管理人员
     */
    public String isAdmin() {
        return SPUtils.getInstance().getString(Constants.ISADMIN, "");
    }

    public void setAdmin(String isAdmin) {
        SPUtils.getInstance().put(Constants.ISADMIN, isAdmin);
    }


    /**
     * 清空本地用户信息
     */
    public void clearUserInfo() {
        PersonInfoManager.getInstance().setUserId("");
        PersonInfoManager.getInstance().setUserName("");
        PersonInfoManager.getInstance().setDeptid("");
        PersonInfoManager.getInstance().setDeptName("");
        PersonInfoManager.getInstance().setUserType("");
        PersonInfoManager.getInstance().setAdmin("");
    }

}
