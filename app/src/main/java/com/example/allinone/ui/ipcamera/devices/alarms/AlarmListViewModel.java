package com.example.allinone.ui.ipcamera.devices.alarms;

import android.app.Application;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.entity.AlarmEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Created by JongLim on 2/5/2019.
 */
public class AlarmListViewModel extends BaseViewModel {

    private final int MAX_ALARM_DEV = 3;
    private final int MAX_DEV_ROW = 6;
    private final ObservableArrayList<AlarmEntity> alarms = new ObservableArrayList<>();
    private final SimpleDateFormat mSDDate = new SimpleDateFormat("yyyy/MM/dd");
    private final SimpleDateFormat mSDTime = new SimpleDateFormat("HH:mm:ss");

    private final ObservableInt layout = new ObservableInt(0);
    private int update_index = 0;

    public AlarmListViewModel(@NonNull Application application) {
        super(application);
        this.layout.set(R.layout.item_alarm);
        AlarmEntity label = new AlarmEntity("序号", "时间", "设备ID", "监控值");
        alarms.add(label);
    }

    public boolean initRowData() {
        if (alarms.size() < MAX_ALARM_DEV * MAX_DEV_ROW) {
            Date date = new Date(System.currentTimeMillis());
            for (int i = 0; i < MAX_DEV_ROW; i++) {
                String time = (i % 2 == 0) ? mSDTime.format(date) : mSDDate.format(date);
                String devId = "100100" + (alarms.size() - 1) / MAX_DEV_ROW;
                String str = getAlarmItem(i);
                AlarmEntity alarm = new AlarmEntity("" + alarms.size(), time, devId, str);
                alarms.add(alarm);
            }
            return true;
        }
        return false;
    }

    public void updateRow() {
        if (update_index >= MAX_ALARM_DEV) {
            update_index = 0;
        }
        Date date = new Date(System.currentTimeMillis());
        for (int i = 0; i < MAX_DEV_ROW; i++) {
            AlarmEntity alarm = alarms.get(update_index * MAX_DEV_ROW + i + 1);
            String time = (i % 2 == 0) ? mSDTime.format(date) : mSDDate.format(date);
            String str = getAlarmItem(i);
            alarm.setTime(time, BR.time);
            alarm.setValue(str, BR.value);
        }
        update_index++;
    }

    private String getAlarmItem(int index) {
        Random random = new Random();
        float value;
        switch (index) {
            case 0:
                value = 27f + random.nextInt(20) / 10f;
                return "空气温度: " + value + " ℃";
            case 1:
                value = 57.5f + random.nextInt(10) / 10f;
                return "空气湿度: " + value + " %";
            case 2:
                value = 25f + random.nextInt(20) / 10f;
                return "土壤温度: " + value + " ℃";
            case 3:
                value = 60.5f + random.nextInt(10) / 10f;
                return "土壤湿度: " + value + " %";
            case 4:
                value = 9527 + random.nextInt(100);
                return "光照强度: " + value + " lux";
            default:
                value = 1798 + random.nextInt(500);
                return "CO2浓度: " + value + " ppm";
        }
    }

    public ObservableInt getLayout() {
        return layout;
    }

    public ObservableArrayList<AlarmEntity> getAlarms() {
        return alarms;
    }

}
