package com.example.allinone.entity;

import com.example.allinone.R;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class DeviceEntity {
    private String id;
    private String name;
    private boolean online;
    private boolean checked;
    private DevTypes devType;
    private boolean lastChild;

    public DeviceEntity(String id, String name, boolean online) {
        this(id, name, online, DevTypes.CAMERA);
    }

    public DeviceEntity(String id, String name, boolean online, DevTypes devType) {
        this.id = id;
        this.name = name;
        this.online = online;
        this.checked = false;
        this.devType = devType;
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

    public int getIcon() {
        switch (devType) {
            case VIDEO:
                return online ? R.mipmap.ic_videos : R.mipmap.ic_videos_off;
            case PICTURE:
                return online ? R.mipmap.ic_photos : R.mipmap.ic_photos_off;
            default:
                return online ? R.mipmap.ic_camera : R.mipmap.ic_camera_off;
        }
    }

    public int getCheckIcon() {
        switch (devType) {
            case VIDEO:
            case PICTURE:
                return R.drawable.ic_check_tr;
            default:
                return checked ? R.drawable.ic_check : R.drawable.ic_check_na;
        }
    }

    public boolean isLastChild() {
        return lastChild;
    }

    public void setLastChild(boolean lastChild) {
        this.lastChild = lastChild;
    }

    public enum DevTypes {
        CAMERA, PICTURE, VIDEO
    }
}
