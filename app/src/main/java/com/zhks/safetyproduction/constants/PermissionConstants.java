package com.zhks.safetyproduction.constants;

import android.Manifest;

public class PermissionConstants {
    public static final String[] permissionsPhotoGroup= new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] locationPerMissionGroup = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    public static final String[] writePerMissionGrop = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}
