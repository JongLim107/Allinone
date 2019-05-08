package com.example.allinone.ui.ipcamera.devices.cameras;

import android.app.Application;
import android.content.Context;

import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;
import com.example.allinone.ui.ipcamera.devices.DevicesNavigator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class CameraListViewModel extends BaseViewModel<BaseModel, DevicesNavigator> {

    public final ObservableList<AreaEntity> areas = new ObservableArrayList<>();

    private final int MAX_OPEN_COUNT = 16;
    private final Context mContext;
    private int[] selectedCameras = new int[0];

    public BindingCommand onStartPlay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (selectedCameras.length < 1) {
                ToastUtils.showShort("Please choosing camera first.");
            } else {
                getNavigator().openCameraActivity(areas, selectedCameras);
            }
        }
    });

    public CameraListViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application.getApplicationContext();
        initList();
    }

    // mock data
    public void initList() {
        //crate the group member info
        for (int groupId = 0; groupId < 20; groupId++) {
            List<CameraEntity> cameras = new ArrayList<>();
            for (int childId = 0; childId <= groupId; childId++) {
                boolean online = 0 == childId % 2;
                cameras.add(new CameraEntity("" + childId, "Camera " + groupId + "-" + childId, online));
            }
            areas.add(new AreaEntity(groupId + "", "Area [" + groupId + "]", cameras));
        }
        areas.get(2)
                .setExpanded(true);
        areas.get(2)
                .setChecked(AreaEntity.Checked.some);
        areas.get(3)
                .setChecked(AreaEntity.Checked.all);
    }
}
