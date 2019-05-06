package com.example.allinone.entity;

import java.util.List;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class AreaEntity {
    private String id;
    private String name;
    private boolean expanded;
    private Checked checkedAll;
    private List<CameraEntity> cameras;

    public AreaEntity(String id, String name, List<CameraEntity> cameras) {
        this.id = id;
        this.name = name;
        this.cameras = cameras;
        this.checkedAll = Checked.none;
        this.expanded = false;
    }

    public int getCameraLen() {
        return this.cameras.size();
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

    public List<CameraEntity> getCameras() {
        return cameras;
    }

    public void setCameras(List<CameraEntity> cameras) {
        this.cameras = cameras;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Checked getCheckedAll() {
        return checkedAll;
    }

    public void setCheckedAll(Checked checkedAll) {
        this.checkedAll = checkedAll;
    }

    public enum Checked {
        none, some, all
    }

}
