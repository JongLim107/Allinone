package com.example.allinone.entity;

import com.example.allinone.R;

import java.util.List;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class AreaEntity {
    private String id;
    private String name;
    private boolean expanded;
    private Checked checked;
    private int checkIcon;
    private List<CameraEntity> cameras;

    public AreaEntity(String id, String name, List<CameraEntity> cameras) {
        this.id = id;
        this.name = name;
        this.cameras = cameras;
        this.expanded = false;
        this.checked = Checked.none;
        this.checkIcon = R.drawable.ic_check_na;
    }

    public int childSize() {
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

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getCheckIcon() {
        switch (checked) {
            case some:
                checkIcon = R.drawable.ic_check_bg;
                break;
            case all:
                checkIcon = R.drawable.ic_check_all;
                break;
            default:
                checkIcon = R.drawable.ic_check_na;
        }
        return checkIcon;
    }

    public void setChecked(Checked checked) {
        this.checked = checked;
    }

    public enum Checked {
        none, some, all
    }

}
