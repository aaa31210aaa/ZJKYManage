package com.zhks.safetyproduction.constants;

import android.Manifest;
import android.os.Environment;

public class Constants {
    public static final String IP_URL = "http://221.214.182.102:18082/dcs_zj";
    public static final int REQUEST_CODE_CHOOSE = 10001;
    public static final String DB_NAME = "zjky.db";
    public static final String QUESTION_JUDGE = "judge";
    public static final String QUESTION_SINGLE_CHOICE = "single_choice";
    public static final String QUESTION_MULTIPLE_CHOICE = "multiple_choice";
    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_CODE2 = "0000";
    public static final String SUCCESS_STR = "true";
    public static final String TYPE_TOKEO = "token";
    public static final String KEY_USER = "keyuser";
    public static final String PUSH_TOKEN = "pushtoken";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String DEPT_ID = "deptId";
    public static final String DEPT_NAME = "deptName";
    public static final String USER_TYPE = "userType";
    public static final String USER_TOKEN = "userToken";
    public static final String MESSAGE_TYPE1 = "XXLX001";
    public static final String MESSAGE_TYPE2 = "XXLX002";
    public static final String APPFLAGN00 = "1000"; //安全检查表
    public static final String APPFLAGNO1 = "1001";
    public static final String APPFLAGNO2 = "1002";
    public static final String APPFLAGNO3 = "1003";
    public static final String APPFLAGNO4 = "1004"; //风险点检查
    public static final String APPFLAGNO5 = "1005";
    public static final String APPFLAGNO6 = "1006";
    public static final String APPFLAGNO7 = "1007";
    public static final String APPFLAGNO8 = "1008";
    public static final String APPFLAGNO9 = "1009";
    public static final String APPFLAGNO10 = "1010"; //隐患整改
    public static final String APPFLAGNO11 = "1011"; //隐患验收
    public static final String APPFLAGNO12 = "1012"; //隐患销案
    public static final String APPFLAGNO3000 = "3000"; //试题考试
    public static final String APPFLAGNO3001 = "3001"; //安全确认
    public static final String MULTI_TITLE = "multiTitle";
    public static final String ISADMIN = "isAmin";
    public static final String ZYGW_CODE = "PGDYLX003";
    public static final String GLGW_CODE = "PGDYLX005";
    public static final int YHTX_CODE = 30001;
    public static final int GWJL_LIST_CODE = 30002;
    public static final String LOCALADDRESS = "content://media/external/images/media/";
    public static final int YHYSCODE = 30003;
    public static final String YHJB001 = "YHJB001"; //一般隐患
    public static final String YHJB002 = "YHJB002"; //重大隐患
    public static final String QRJG001 = "QRJG001";  //QRJG001（合格），
    public static final String QRJG002 = "QRJG002"; //QRJG002（不合格）
    public static final String[] WRITE_READ_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String TX001 = "TX001";
    public static final String TX002 = "TX002";
    public static final String TX003 = "TX003";

    /**
     * 跳转code
     */
    public static final int TO_LOGING_CODE = 20001;
    public static final int TO_MULTI_CODE = 20002;
    public static final int TO_PERSONNEL_CODE = 20003; //隐患整改人
    public static final int TO_PERSONNEL_CODE_YSR = 20004; //隐患验收人
    public static final int TO_PERSONNEL_CODE_PCR = 20005; //隐患排查人
    public static final int TO_YHXA_CODE = 20006; //隐患核销
    public static final int QUESTION_TM = 20007;
    public static final int TO_AQQRDETAIL_CODE = 20008;
    public static final int TO_ZCRMUTIL_PERSONNEL_CODE = 20009;
    public static final int TO_YHRYMUTIL_PERSONNEL_CODE = 20010;
    public static final int TO_CYLDMUTIL_PERSONNEL_CODE = 20011;
    public static final int TO_DWZZR_PERSONNEL_CODE = 20012;
    public static final int TO_REGISTER_RISK = 20013;
    public static final String AGREEMENT_ACCEPTED = "agreement_accepted";
    public static final String DRAFT_TYPE_YH = "yh";
    public static final String DRAFT_TYPE_SGKB = "sgkb";
    public static final int MAXSELECTNUM = 9;
    public static final int CONFIRMMAXSELECTNUM = 5;

    public static final String APK_SAVE_PATH = Environment.getExternalStorageDirectory().getPath();

    public static final String APK_NAME= "zjky.apk";

}
