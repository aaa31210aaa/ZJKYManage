package com.zhks.safetyproduction.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

public class SafeCheckFormBean extends BaseObservable implements Parcelable {
    private String safeCheckType;
    private String safeCheckRegion;
    private String safeCheckObject;
    private String SerialNumber;
    private String Observation;
    private boolean isRegister;

    public String getSafeCheckType() {
        return safeCheckType;
    }

    public void setSafeCheckType(String safeCheckType) {
        this.safeCheckType = safeCheckType;
    }

    public String getSafeCheckRegion() {
        return safeCheckRegion;
    }

    public void setSafeCheckRegion(String safeCheckRegion) {
        this.safeCheckRegion = safeCheckRegion;
    }

    public String getSafeCheckObject() {
        return safeCheckObject;
    }

    public void setSafeCheckObject(String safeCheckObject) {
        this.safeCheckObject = safeCheckObject;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.safeCheckType);
    }

    public SafeCheckFormBean() {
    }

    protected SafeCheckFormBean(Parcel in) {
        this.safeCheckType = in.readString();
    }

    public static final Creator<SafeCheckFormBean> CREATOR = new Creator<SafeCheckFormBean>() {
        @Override
        public SafeCheckFormBean createFromParcel(Parcel source) {
            return new SafeCheckFormBean(source);
        }

        @Override
        public SafeCheckFormBean[] newArray(int size) {
            return new SafeCheckFormBean[size];
        }
    };
}
