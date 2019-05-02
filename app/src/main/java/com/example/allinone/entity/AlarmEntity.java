package com.example.allinone.entity;

/**
 * Created by JongLim on 2/5/2019.
 */
public class AlarmEntity {
    private String num;
    private String time;
    private String devId;
    private String value;

    public AlarmEntity(String num, String time, String devId, String value) {
        this.num = num;
        this.time = time;
        this.devId = devId;
        this.value = value;
    }

    public String getNum() {
        return num;
    }

    public String getTime() {
        return time;
    }

    public String getDevId() {
        return devId;
    }

    public String getValue() {
        return value;
    }

}
