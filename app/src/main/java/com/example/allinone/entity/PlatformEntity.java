package com.example.allinone.entity;

/**
 * Created by Jong Lim on 21/4/19.
 */
public class PlatformEntity {
    private String mAccount;
    private String mPassword;
    private String mAddress;
    private int mPort;
    private String mTitle;

    public PlatformEntity(String account, String pwd, String title, String address ) {
        new PlatformEntity(account, pwd, title, address, 9100);
    }

    public PlatformEntity(String account, String pwd, String title, String address, int mPort) {
        this.mAccount = account;
        this.mPassword = pwd;
        this.mTitle = title;
        this.mAddress = address;
        this.mPort = mPort;
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
