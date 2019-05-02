package com.example.allinone.ui.ipcamera.devices.alarms;

import android.app.Application;

import com.example.allinone.entity.AlarmEntity;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Created by JongLim on 2/5/2019.
 */
public class AlarmListViewModel extends BaseViewModel {

    public AlarmEntity entity;

    public AlarmListViewModel(@NonNull Application application) {
        super(application, null);
    }

    public void setEntity(AlarmEntity entity) {
        this.entity = entity;
    }

}
