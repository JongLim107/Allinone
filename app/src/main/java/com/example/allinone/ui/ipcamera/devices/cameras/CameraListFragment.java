package com.example.allinone.ui.ipcamera.devices.cameras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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
        binding.elvCamera.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int gPos, int cPos, long id) {
                ToastUtils.showShort("You click on child index " + cPos);
                return false;
            }
        });
    }

    @Override
    public void initData() {
        initList();
        View header = getLayoutInflater().inflate(R.layout.item_camera_area, binding.elvCamera, false);
        binding.elvCamera.setHeaderView(header);
        binding.elvCamera.setAdapter(new DevicesListAdapter(areas));
    }

    private void initList() {
        //crate the group member info
        for (int i = 0; i < 20; i++) {
            final double d = Math.random();
            final int num_children = (int) (d * 50);
            List<CameraEntity> cameras = new ArrayList<>();
            if (num_children > 0) {
                for (int j = 0; j < num_children; j++) {
                    boolean online = num_children % 2 == 0;
                    cameras.add(new CameraEntity(null, "Camera " + j, online));
                }
            }
            areas.add(new AreaEntity(null, "Area [" + num_children + "]", cameras));
        }
    }

}