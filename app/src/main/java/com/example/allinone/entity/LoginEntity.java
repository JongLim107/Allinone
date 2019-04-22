package com.example.allinone.entity;

/**
 * Created by Jong Lim on 19/4/19.
 */
public class LoginEntity {
    private String name;
    private String pwd;
    private Boolean isLogin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Boolean isLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }
}
