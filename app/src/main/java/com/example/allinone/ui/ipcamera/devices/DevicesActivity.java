package com.example.allinone.ui.ipcamera.devices;

import android.os.Bundle;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.base.BaseFragmentPagerAdapter;
import com.example.allinone.base.ToolbarViewModel;
import com.example.allinone.databinding.ActivityDevicesBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.ui.ipcamera.devices.alarms.AlarmListFragment;
import com.example.allinone.ui.ipcamera.devices.cameras.CameraListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.IToolbarNavigator;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class DevicesActivity extends BaseActivity<ActivityDevicesBinding, ToolbarViewModel> implements
        IToolbarNavigator, DevicesNavigator {

    protected List<Fragment> pagerFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new CameraListFragment());
        list.add(new Fragment());
        list.add(new Fragment());
        list.add(new AlarmListFragment());
        list.add(new Fragment());
        return list;
    }

    protected List<String> pagerTitleString() {
        List<String> list = new ArrayList<>();
        String[] TITLES = {"Switch In", "Switch Out"};
        list.add("Camera");
        list.add("Snapshot Record");
        list.add("Video Record");
        list.add("Remote Alarm");
        list.add("Switch In");
        return list;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_devices;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //设置Adapter
        BaseFragmentPagerAdapter pagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),
                pagerFragment(), pagerTitleString());
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);

        viewModel.setNavigator(this);
        viewModel.setTitleText("Devices List");
    }

    @Override
    public void onLeftClick() {
        onBackPressed();
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
    public void openSnapshotActivity() {

    }

    @Override
    public void openVideoPlayActivity() {

    }

    @Override
    public void openFileListActivity() {

    }

    @Override
    public void openCameraActivity(List<AreaEntity> areas, List selectedCameras) {
        ToastUtils.showLong("Sorry, haven't implement this part for now.");
    }

}
