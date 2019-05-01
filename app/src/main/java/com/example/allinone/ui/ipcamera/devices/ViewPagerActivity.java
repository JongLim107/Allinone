package com.example.allinone.ui.ipcamera.devices;

import android.os.Bundle;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.databinding.ActivityDevicesBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.IToolbarNavigator;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class ViewPagerActivity extends BaseActivity<ActivityDevicesBinding, ViewPagerViewModel>
        implements IToolbarNavigator, ViewPagerNavigator {

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
        viewModel.setNavigator(this);
        viewModel.setTitleText("Platform Device");

        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        setSupportActionBar(binding.include.toolbar);
    }

    @Override
    public void initViewObservable() {
        viewModel.itemClickEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String text) {
                ToastUtils.showShort("position：" + text);
            }
        });
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
