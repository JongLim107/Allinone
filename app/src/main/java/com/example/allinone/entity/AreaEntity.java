package com.example.allinone.entity;

import java.util.List;

import androidx.databinding.ObservableBoolean;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class AreaEntity {
    private String id;
    private String name;
    private Checked checkedAll;
    private List<CameraEntity> cameras;
    private ObservableBoolean expanded = new ObservableBoolean(false);

    public AreaEntity(String id, String name, List<CameraEntity> cameras) {
        this.id = id;
        this.name = name;
        this.cameras = cameras;
        this.checkedAll = Checked.none;
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

    public Checked getCheckedAll() {
        return checkedAll;
    }

    public void setCheckedAll(Checked checkedAll) {
        this.checkedAll = checkedAll;
    }

    public boolean isExpanded() {
        return this.expanded.get();
    }

    public void setExpanded(boolean expanded) {
        this.expanded.set(expanded);
    }

    public boolean toggle() {
        setExpanded(!isExpanded());
        return false;
    }

    public enum Checked {
        none,
        some,
        all
    }

}
