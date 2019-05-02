package com.example.allinone.ui.ipcamera.devices;

import com.example.allinone.base.BasePagerFragment;
import com.example.allinone.ui.ipcamera.devices.alarms.AlarmListFragment;
import com.example.allinone.ui.ipcamera.devices.cameras.CameraListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import me.goldze.mvvmhabit.base.IToolbarNavigator;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class DevicesFragment extends BasePagerFragment implements IToolbarNavigator, DevicesNavigator {

    @Override
    protected List<Fragment> pagerFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new AlarmListFragment());
        list.add(new CameraListFragment());
        list.add(new Fragment());
        list.add(new Fragment());
        list.add(new Fragment());
        return list;
    }

    @Override
    protected List<String> pagerTitleString() {
        List<String> list = new ArrayList<>();
        String[] TITLES = {"Switch In", "Switch Out"};
        list.add("Camera");
        list.add("Snapshot Record");
        list.add("Video Record");
        list.add("Remote Alarm");
        list.add("Camera");
        return list;
    }

    @Override
    public void initData() {
        super.initData();
//        viewModel.setNavigator(this);
//        viewModel.setTitleText("Platform Device");
    }

    @Override
    public void onLeftClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onRightIconClick() {

    }

    @Override
    public void onRightTextClick() {

    }

    @Override
    public void onTitleClick() {

    }

    @Override
    public void openCameraActivity() {

    }

    @Override
    public void openSnapshotActivity() {

    }

    @Override
    public void openVideoPlayActivity() {

    }

    @Override
    public void openFileListActivity() {

    }

}
