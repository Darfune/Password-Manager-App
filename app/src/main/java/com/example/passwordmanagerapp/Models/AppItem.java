package com.example.passwordmanagerapp.Models;

public class AppItem {

    private String id;
    private String appName;
    private String account;

    public AppItem() {
    }

    public AppItem(String id, String appName, String account) {
        this.id = id;
        this.appName = appName;
        this.account = account;
    }

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
}
