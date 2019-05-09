package com.example.allinone.ui.ipcamera.devices.cameras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.databinding.FragmentCamerasBinding;
import com.example.allinone.databinding.ItemCameraAreaBinding;
import com.example.allinone.databinding.ItemCameraDevBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;
import com.example.allinone.ui.ipcamera.devices.DevicesNavigator;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class CameraListFragment extends BaseFragment<FragmentCamerasBinding, CameraListViewModel> {

    private final int MAX_OPEN_COUNT = 16;
    private CamerasListAdapter adapter;

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
    public void initData() {
        viewModel.setNavigator((DevicesNavigator) getActivity());
        adapter = new CamerasListAdapter(getContext(), viewModel.areas);
        binding.areasList.setAdapter(adapter);

        ItemCameraAreaBinding ibinding = ItemCameraAreaBinding.inflate(getLayoutInflater(), binding.areasList, false);
        View headerView = ibinding.getRoot();
        headerView.setTag(ibinding);
        binding.areasList.setHeaderView(headerView);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<AreaEntity> list = viewModel.onFilterCamera(newText);
                adapter.replaceData(list);
                return false;
            }
        });

        binding.areasList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                    long id) {
                CameraEntity camera = adapter.getChild(groupPosition, childPosition);
                if (camera.isOnline()) {
                    if (!camera.isChecked() && viewModel.selectedCameras.size() == MAX_OPEN_COUNT) {
                        ToastUtils.showShort("Only can play maximum 16 camera in the one time.");
                        return false;
                    }

                    if (!camera.isChecked() && viewModel.selectedCameras.size() < MAX_OPEN_COUNT) {
                        camera.setChecked(true);
                        viewModel.selectedCameras.add(camera);
                    } else {
                        viewModel.selectedCameras.remove(camera);
                        camera.setChecked(false);
                    }
                    ItemCameraDevBinding binding = (ItemCameraDevBinding) v.getTag();
                    binding.setModel(camera);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort("Camera is offline, please retry later.");
                }
                return true;
            }
        });
    }

}