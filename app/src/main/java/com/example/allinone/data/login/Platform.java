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

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
