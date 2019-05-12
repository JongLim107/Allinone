package com.example.allinone.ui.ipcamera.devices.snapshots;

import android.app.Application;
import android.content.Context;

import com.example.allinone.R;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;
import com.example.allinone.ui.ipcamera.devices.DevicesNavigator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class SnapshotViewModel extends BaseViewModel<BaseModel, DevicesNavigator> {
    public final ObservableList<AreaEntity> areas = new ObservableArrayList<>();
    public final ObservableField<String> playNow = new ObservableField<>("Play Now");
    public final ObservableList<CameraEntity> selectedCameras = new ObservableArrayList<>();

    private final Context mContext;

    public BindingCommand onStartPlay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (selectedCameras.size() < 1) {
                ToastUtils.showShort("Please choosing camera first.");
            } else {
                getNavigator().openCameraActivity(areas, selectedCameras);
            }
        }
    });

    public SnapshotViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application.getApplicationContext();

        initList();
    }

    // mock data
    public void initList() {
        for (int groupId = 0; groupId < 20; groupId++) {
            List<CameraEntity> cameras = new ArrayList<>();
            for (int childId = 0; childId <= groupId; childId++) {
                boolean online = childId < 2;
                cameras.add(new CameraEntity("" + childId, "Camera Title " + groupId + "-" + childId, online));
            }
            areas.add(new AreaEntity(groupId + "", "Area Title [" + groupId + "]", cameras));
        }
    }

    private void onSelectedChanged() {
        if (selectedCameras.size() > 0) {
            String title = String.format(mContext.getString(R.string.play_btn_format), selectedCameras.size());
            playNow.set(title);
        } else {
            String mButtonTitle = "Play Now";
            playNow.set(mButtonTitle);
        }
    }

}
