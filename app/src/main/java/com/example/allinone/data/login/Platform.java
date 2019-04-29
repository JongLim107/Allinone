package com.example.allinone.data.login;

/**
 * Created by Jong Lim on 21/4/19.
 */
public class Platform {
    private String mAccount;
    private String mPassword;
    private String mAddress;
    private String mTitle;

    public Platform() {
        new Platform("", "", "", "");
    }

    public Platform(String account, String pwd, String title, String address) {
        this.mAccount = account;
        this.mPassword = pwd;
        this.mTitle = title;
        this.mAddress = address;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
