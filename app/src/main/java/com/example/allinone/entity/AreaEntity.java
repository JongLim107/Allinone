package com.example.allinone.entity;

import java.util.List;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class AreaEntity {
    private String id;
    private String name;
    private List<CameraEntity> cameras;

    public AreaEntity(String id, String name, List<CameraEntity> cameras) {
        this.id = id;
        this.name = name;
        this.cameras = cameras;
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

}
