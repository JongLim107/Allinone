package com.example.allinone.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Jong Lim on 21/4/19.
 */
@Entity
public class PlatformEntity {

    @Id
    public long id;
    private String title;
    private String userName;
    private String password;
    private String address;
    private String port;
    private boolean saveInner;

    public PlatformEntity() {
        this("", "", "", "", "9100");
    }

    public PlatformEntity(String title, String userName, String password, String address) {
        this(title, userName, password, address, "9100");
    }

    public PlatformEntity(String title, String userName, String password, String address, String port) {
        this.title = title;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.port = port;
        this.saveInner = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isSaveInner() {
        return saveInner;
    }

    public void setSaveInner(boolean saveInner) {
        this.saveInner = saveInner;
    }
}
