package com.example.allinone.ui.ipcamera.devices.videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.allinone.R;
import com.example.allinone.databinding.FragmentVideoBinding;
import com.example.allinone.databinding.ItemCameraAreaBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.DeviceEntity;
import com.example.allinone.ui.ipcamera.devices.DevicesAdapter;
import com.example.allinone.ui.ipcamera.devices.DevicesNavigator;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class VideoListFragment extends BaseFragment<FragmentVideoBinding, VideoListViewModel> {

    private DevicesAdapter adapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_video;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setNavigator((DevicesNavigator) getActivity());
        adapter = new DevicesAdapter(getContext(), viewModel.areas);
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
                DeviceEntity camera = adapter.getChild(groupPosition, childPosition);
                viewModel.getNavigator().openFileListActivity(viewModel.areas, camera);
                return true;
            }
        });
    }
}
