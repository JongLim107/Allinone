package com.example.allinone.entity;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class CameraEntity {
    private String id;
    private String name;
    private boolean status; // 0 == off-line,  1 == on-line

    public CameraEntity(String id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
