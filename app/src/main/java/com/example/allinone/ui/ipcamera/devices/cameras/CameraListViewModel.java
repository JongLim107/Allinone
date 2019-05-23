package com.example.allinone.ui.ipcamera.devices.cameras;

import android.app.Application;
import android.content.Context;

import com.example.allinone.R;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.DeviceEntity;
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
public class CameraListViewModel extends BaseViewModel<BaseModel, DevicesNavigator> {
    public final ObservableList<AreaEntity> areas = new ObservableArrayList<>();
    public final ObservableField<String> playNow = new ObservableField<>("Play Now");
    public final ObservableList<DeviceEntity> selectedCameras = new ObservableArrayList<>();

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

    public CameraListViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application.getApplicationContext();
        this.selectedCameras
                .addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<DeviceEntity>>() {
                    @Override
                    public void onChanged(ObservableList<DeviceEntity> sender) {

                    }

                    @Override
                    public void onItemRangeChanged(ObservableList<DeviceEntity> sender, int positionStart,
                            int itemCount) {

                    }

                    @Override
                    public void onItemRangeInserted(ObservableList<DeviceEntity> sender, int positionStart,
                            int itemCount) {
                        onSelectedChanged();
                    }

                    @Override
                    public void onItemRangeMoved(ObservableList<DeviceEntity> sender, int fromPosition, int toPosition,
                            int itemCount) {

                    }

                    @Override
                    public void onItemRangeRemoved(ObservableList<DeviceEntity> sender, int positionStart,
                            int itemCount) {
                        onSelectedChanged();
                    }
                });
        initList();
    }

    // mock data
    public void initList() {
        for (int groupId = 0; groupId < 20; groupId++) {
            List<DeviceEntity> cameras = new ArrayList<>();
            for (int childId = 0; childId <= groupId; childId++) {
                boolean online = childId < 3;
                cameras.add(new DeviceEntity("" + childId, "Camera Title " + groupId + "-" + childId, online));
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
