package com.example.allinone.ui.ipcamera.devices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allinone.base.StickyHeaderExpListAdapter;
import com.example.allinone.databinding.ItemCameraAreaBinding;
import com.example.allinone.databinding.ItemCameraDevBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.DeviceEntity;

import java.util.List;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class DevicesAdapter extends StickyHeaderExpListAdapter<AreaEntity> {

    public DevicesAdapter(Context context, List<AreaEntity> groupArray) {
        super(context, groupArray);
    }

    @Override
    public void configureHeader(View view, int groupPosition, int childPosition, int alpha) {
        ItemCameraAreaBinding binding = (ItemCameraAreaBinding) view.getTag();
        binding.setModel(getGroup(groupPosition));
        binding.executePendingBindings();
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        ItemCameraAreaBinding binding;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemCameraAreaBinding.inflate(inflater);
            convertView = binding.getRoot();
            convertView.setTag(binding);

        } else {  // Recycling view
            binding = (ItemCameraAreaBinding) convertView.getTag();
        }

        AreaEntity area = getGroup(groupPos);
        area.setExpanded(isExpanded);
        binding.setModel(area);
        return convertView;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup parent) {
        //TODO ADD SUPPORT FOR MAKE GROUP EXPANDED BY DEFAULT
        ItemCameraDevBinding binding;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemCameraDevBinding.inflate(inflater);
            convertView = binding.getRoot();
            convertView.setTag(binding);

        } else {  // Recycling view
            binding = (ItemCameraDevBinding) convertView.getTag();
        }

        DeviceEntity device = (DeviceEntity) getChild(groupPos, childPos);
        device.setLastChild(isLastChild);
        binding.setModel(device);
        return convertView;
    }

}
