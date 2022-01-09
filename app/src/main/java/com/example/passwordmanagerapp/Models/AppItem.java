package com.example.passwordmanagerapp.Models;

public class AppItem {

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
}
