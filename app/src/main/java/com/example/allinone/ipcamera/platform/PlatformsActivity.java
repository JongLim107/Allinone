package com.example.allinone.ipcamera.platform;

import android.content.Context;
import android.os.Bundle;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.databinding.ActivityPlatformsBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class PlatformsActivity extends BaseActivity<ActivityPlatformsBinding, PlatformsViewModel> {

    private Context mContext;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_platforms;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        //给ViewPager设置adapter
        viewModel.setPagerAdapter(new PlatformsAdapter(getSupportFragmentManager()));
    }

    @Override
    public void initViewObservable() {
//        viewModel.itemClickEvent.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String text) {
//                ToastUtils.showShort("position：" + text);
//            }
//        });
    }

    public static class PlatformsAdapter extends FragmentPagerAdapter {

        private final String[] TABS_TITLE = new String[]{"Camera", "Snapshot Record", "Video Record", "Remote Alarm", "Switch In", "Switch Out"};

        public PlatformsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CameraListFragment();
                case 1:
                    return new SnapshotFragment();
                case 2:
                    return new VideoListFragment();
                case 3:
                    return new AlarmListFragment();
                case 4:
                    return new VideoListFragment();
                case 5:
                    return new VideoListFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return TABS_TITLE.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS_TITLE[position];
        }
    }
}
