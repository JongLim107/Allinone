package com.example.allinone.entity;

import com.example.allinone.R;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class CameraEntity {
    private String id;
    private String name;
    private boolean online; // off/on line
    private boolean checked;

    public CameraEntity(String id, String name, boolean online) {
        this.id = id;
        this.name = name;
        this.online = online;
        this.checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getIcon(){
        return online ? R.mipmap.ic_camera : R.mipmap.ic_camera_off;
    }
}
