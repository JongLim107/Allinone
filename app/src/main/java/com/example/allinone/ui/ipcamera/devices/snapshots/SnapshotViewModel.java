package com.example.allinone.ui.ipcamera.devices.snapshots;

import android.app.Application;
import android.content.Context;

import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.DeviceEntity;
import com.example.allinone.ui.ipcamera.devices.DevicesNavigator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class SnapshotViewModel extends BaseViewModel<BaseModel, DevicesNavigator> {
    public final ObservableList<AreaEntity> areas = new ObservableArrayList<>();

    public SnapshotViewModel(@NonNull Application application) {
        super(application);
        Context mContext = application.getApplicationContext();

        initList();
    }

    // mock data
    public void initList() {
        for (int groupId = 0; groupId < 20; groupId++) {
            List<DeviceEntity> cameras = new ArrayList<>();
            for (int childId = 0; childId <= groupId; childId++) {
                boolean online = childId < 2;
                cameras.add(new DeviceEntity("" + childId, "Camera Title " + groupId + "-" + childId, online,
                        DeviceEntity.DevTypes.PICTURE));
            }
            areas.add(new AreaEntity(groupId + "", "Area Title [" + groupId + "]", cameras));
        }
    }


    public List<AreaEntity> onFilterCamera(String newText) {
        if (newText == null || newText.length() < 1) {
            return areas;
        }

        List<AreaEntity> nas = new ArrayList<>();
        for (AreaEntity a : areas) {
            List<DeviceEntity> ncs = new ArrayList<>();
            for (DeviceEntity c : a.getCameras()) {
                if (c.getName().contains(newText)) {
                    ncs.add(c);
                }
            }
            if (ncs.size() > 0) {
                nas.add(new AreaEntity(a.getId(), a.getName(), ncs));
            }
        }
        return nas;
    }


}
