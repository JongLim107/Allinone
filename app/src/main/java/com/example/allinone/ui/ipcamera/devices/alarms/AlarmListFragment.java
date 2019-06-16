package com.example.allinone.ui.ipcamera.devices.alarms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.databinding.FragmentAlarmsBinding;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class AlarmListFragment extends BaseFragment<FragmentAlarmsBinding, AlarmListViewModel> {

    private Timer timer;
    private boolean isShowing = false;

    @Override
    public int initContentView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_alarms;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initParam() {
        super.initParam();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isShowing && viewModel != null && !viewModel.initRowData()) {
                    viewModel.updateRow();
                }
            }
        }, 1000, 2000);
    }

    @Override
    public void onDestroy() {
        isShowing = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

}
