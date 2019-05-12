package com.example.allinone.entity;

import com.example.allinone.R;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by JongLim on 2/5/2019.
 */
public class AlarmEntity extends BaseObservable {
    private String num;
    private String time;
    private String devId;
    private String value;
    private int color;

    public AlarmEntity(String num, String time, String devId, String value) {
        this.num = num;
        this.time = time;
        this.devId = devId;
        this.value = value;
        this.color = R.color.white;
    }

    public int getColor() {
        try {
            int n = Integer.parseInt(num);
            return (n - 1) % 6 == 0 ? R.color.viewLineColor : R.color.white;
        } catch (NumberFormatException e) {
            return color;
        }

    }

    @Bindable
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time, int fieldId) {
        this.time = time;
        notifyPropertyChanged(fieldId);
    }

    @Bindable
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value, int fieldId) {
        this.value = value;
        notifyPropertyChanged(fieldId);
    }
}
