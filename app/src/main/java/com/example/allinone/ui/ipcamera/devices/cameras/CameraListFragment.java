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
import com.example.allinone.entity.CameraEntity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class CameraListFragment extends BaseFragment<FragmentCamerasBinding, CameraListViewModel> {

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
                ToastUtils.showShort("You Changed search text " + newText);
                return false;
            }
        });

        binding.areasList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                    long id) {
                CameraEntity cam = adapter.getChild(groupPosition, childPosition);
                if (cam.isOnline()) {
                    ItemCameraDevBinding binding = (ItemCameraDevBinding) v.getTag();
                    cam.setChecked(!cam.isChecked());
                    binding.setModel(cam);
                } else {
                    ToastUtils.showShort("Camera is offline, please retry later.");
                }
                return true;
            }
        });
    }
}