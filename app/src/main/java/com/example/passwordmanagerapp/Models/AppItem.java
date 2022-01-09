package com.example.passwordmanagerapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class AppItem implements Parcelable {

    private String id;
    private String appName;
    private String account;
    private String password;

    public AppItem() {

    }

    public AppItem(String id, String appName, String account, String password) {
        this.id = id;
        this.appName = appName;
        this.account = account;
        this.password = password;
    }

    protected AppItem(Parcel in) {
        id = in.readString();
        appName = in.readString();
        account = in.readString();
        password = in.readString();
    }

    public static final Creator<AppItem> CREATOR = new Creator<AppItem>() {
        @Override
        public AppItem createFromParcel(Parcel in) {
            return new AppItem(in);
        }

        @Override
        public AppItem[] newArray(int size) {
            return new AppItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(appName);
        parcel.writeString(account);
        parcel.writeString(password);
    }
}
