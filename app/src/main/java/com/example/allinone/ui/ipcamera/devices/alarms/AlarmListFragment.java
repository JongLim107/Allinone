package com.example.allinone.ui.ipcamera.devices.alarms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.databinding.FragmentAlarmsBinding;
import com.example.allinone.entity.AlarmEntity;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class AlarmListFragment extends BaseFragment<FragmentAlarmsBinding, AlarmListViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_alarms;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setEntity(new AlarmEntity("01", "19:23:00", "1001001", "30℃"));
    }
}
