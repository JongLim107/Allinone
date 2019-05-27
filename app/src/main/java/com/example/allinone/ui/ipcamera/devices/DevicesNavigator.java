package com.example.allinone.ui.ipcamera.devices;

import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.DeviceEntity;

import java.util.List;

/**
 * Created by JongLim on 1/5/2019.
 */
public interface DevicesNavigator {

    void openSnapshotActivity();

    void openVideoPlayActivity();

    void openFileListActivity(List<AreaEntity> areas, DeviceEntity camera);

    void openCameraActivity(List<AreaEntity> areas, List selectedCameras);

}
