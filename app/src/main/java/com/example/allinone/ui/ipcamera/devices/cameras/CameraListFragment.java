package com.example.allinone.ui.ipcamera.devices.cameras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.allinone.R;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class CameraListFragment extends BaseFragment {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cameras;
    }

    @Override
    public int initVariableId() {
        return 0;
    }
}
