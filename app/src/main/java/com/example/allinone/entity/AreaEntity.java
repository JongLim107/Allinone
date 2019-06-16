package com.example.allinone.entity;

import com.example.allinone.R;
import com.example.allinone.base.StickyHeaderGroupItem;

import java.util.List;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class AreaEntity extends StickyHeaderGroupItem<DeviceEntity> {
    private String id;
    private String name;
    private int checkIcon;
    private boolean expanded;
    private List<DeviceEntity> cameras;

    public AreaEntity(String id, String name, List<DeviceEntity> cameras) {
        super(name, false, cameras);
        this.id = id;
        this.name = name;
        this.cameras = cameras;
        this.expanded = false;
        this.checkIcon = R.drawable.ic_check_na;
    }

    private Checked getCheckedStatus() {
        int checkedChildren = 0;
        for (DeviceEntity c : cameras) {
            checkedChildren += c.isChecked() ? 1 : 0;
        }
        if (checkedChildren == cameras.size()) {
            return AreaEntity.Checked.all;
        } else if (checkedChildren > 0) {
            return AreaEntity.Checked.some;
        } else {
            return AreaEntity.Checked.none;
        }
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

    public List<DeviceEntity> getCameras() {
        return cameras;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getCheckIcon() {
        switch (getCheckedStatus()) {
            case some:
                checkIcon = R.drawable.ic_check_bg;
                break;
            case all:
                checkIcon = R.drawable.ic_check_all;
                break;
            default:
                checkIcon = R.drawable.ic_check_tr;
        }
        return checkIcon;
    }

    public enum Checked {
        none, some, all
    }

}
