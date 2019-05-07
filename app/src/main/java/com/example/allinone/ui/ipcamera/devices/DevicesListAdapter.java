package com.example.allinone.ui.ipcamera.devices;

import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.allinone.base.MyExpandableListView;
import com.example.allinone.databinding.ItemCameraAreaBinding;
import com.example.allinone.databinding.ItemCameraDevBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;

import java.util.List;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class DevicesListAdapter extends BaseExpandableListAdapter implements MyExpandableListView.HeaderAdapter {

    private SparseIntArray groupStatusMap = new SparseIntArray();
    private List<AreaEntity> areas;

    public DevicesListAdapter(List<AreaEntity> groupArray) {
        this.areas = groupArray;
    }

    @Override
    public int getGroupCount() {
        return areas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition >= areas.size() || groupPosition < 0) {
            return 0;
        }
        AreaEntity group = getGroup(groupPosition);
        return group.getCameraLen();
    }

    @Override
    public AreaEntity getGroup(int groupPosition) {
        if (areas != null && groupPosition < areas.size()) {
            return areas.get(groupPosition);
        } else {
            return null;
        }
    }

    @Override
    public CameraEntity getChild(int groupPosition, int childPosition) {
        AreaEntity area = getGroup(groupPosition);
        return area.getCameras().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        long offset = 0;
        for (int i = 0; i < groupPosition; i++) {
            AreaEntity group = areas.get(i);
            if (group != null) {
                offset += group.getCameraLen();
            }
        }
        return offset + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true; // Verify
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ItemCameraAreaBinding binding;
        if (convertView == null || convertView.getTag() == null) {
            binding = ItemCameraAreaBinding.inflate(LayoutInflater.from(parent.getContext()));
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {  // Recycling view
            if (convertView.getTag() instanceof ItemCameraAreaBinding) {
                binding = (ItemCameraAreaBinding) convertView.getTag();
            } else {
                return null;
            }
        }

        binding.setArea(areas.get(groupPosition));
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public View getChildView(int gPos, int cPos, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemCameraDevBinding binding;
        if (convertView == null || convertView.getTag() == null) {
            binding = ItemCameraDevBinding.inflate(LayoutInflater.from(parent.getContext()));
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {  // Recycling view
            if (convertView.getTag() instanceof ItemCameraDevBinding) {
                binding = (ItemCameraDevBinding) convertView.getTag();
            } else {
                return null;
            }
        }

        binding.setCamera(getChild(gPos, cPos));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    @Override
    public int getHeaderStatus(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1 && !getGroup(groupPosition).isExpanded()) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    @Override
    public void configureHeader(View view, int groupPosition, int childPosition, int alpha) {
        //        ItemCameraAreaBinding binding = DataBindingUtil.getBinding(view);
        //        if (binding != null) {
        //            binding.setArea(getGroup(groupPosition));
        //        }
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
