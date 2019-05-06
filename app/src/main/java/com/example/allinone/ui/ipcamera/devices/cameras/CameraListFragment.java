package com.example.allinone.ui.ipcamera.devices.cameras;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.LayoutParams;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.databinding.FragmentCamerasBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;
import com.example.allinone.ui.ipcamera.devices.DevicesListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class CameraListFragment extends BaseFragment<FragmentCamerasBinding, CameraListViewModel> {

    private List<AreaEntity> areas = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cameras;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ToastUtils.showShort("You Changed search text " + newText);
                return false;
            }
        });
    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void initData() {
        initList();
        LayoutParams listItemLP = new LayoutParams(LayoutParams.MATCH_PARENT, getItemHeight());
        View header = getLayoutInflater().inflate(R.layout.item_camera_area, binding.elvCamera, false);
        binding.elvCamera.setHeaderView(header, listItemLP);
        binding.elvCamera.setAdapter(new DevicesListAdapter(areas, listItemLP));
        //        binding.elvCamera.setOnChildClickListener(this);
    }


    private void initList() {
        //crate the group member info
        for (int i = 0; i < 20; i++) {
            final double d = Math.random();
            final int num_children = (int)(d*50);
            List<CameraEntity> cameras = new ArrayList<>();
            if (num_children > 0) {
                for (int j = 0; j < num_children; j++) {
                    boolean online = num_children % 2 == 0;
                    cameras.add(new CameraEntity(null, "Camera " + num_children, online));
                }
            }
            areas.add(new AreaEntity(null, "Area [" + num_children + "]", cameras));
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * get the List item LayoutParams
     * fix for mutiple android devices
     * note: can't move above
     */
    private int getItemHeight() {
        WindowManager wm = getActivity().getWindowManager();
        Point mPoint = new Point();
        wm.getDefaultDisplay()
                .getSize(mPoint);
        return mPoint.y > 900 ? mPoint.y / 14 : mPoint.y / 10;
    }
}