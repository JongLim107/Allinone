package com.example.allinone.ui.ipcamera.devices;

import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;

import com.example.allinone.base.MyExpandableListView;
import com.example.allinone.databinding.ItemCameraAreaBinding;
import com.example.allinone.databinding.ItemCameraDevBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;

import java.util.List;
import java.util.Objects;

import androidx.databinding.DataBindingUtil;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class DevicesListAdapter extends BaseExpandableListAdapter implements MyExpandableListView.HeaderAdapter {

    private static final String TAG = "ExpListAdt";

    private SparseIntArray groupStatusMap = new SparseIntArray();
    private List<AreaEntity> areas;
    private LayoutParams itemLP;

    public DevicesListAdapter(List<AreaEntity> groupArray, LayoutParams itemLP) {
        this.areas = groupArray;
        this.itemLP = itemLP;
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
        return false; // Verify
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ItemCameraAreaBinding binding;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemCameraAreaBinding.inflate(inflater, parent, false);
        } else {  // Recycling view
            convertView.setLayoutParams(itemLP);
            binding = DataBindingUtil.getBinding(convertView);
        }

        Objects.requireNonNull(binding)
                .setArea(areas.get(groupPosition));
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
            ViewGroup parent) {
        ItemCameraDevBinding binding;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemCameraDevBinding.inflate(inflater, parent, false);
        } else {  // Recycling view
            convertView.setLayoutParams(itemLP);
            binding = DataBindingUtil.getBinding(convertView);
        }

        Objects.requireNonNull(binding)
                .setCamera(getChild(groupPosition, childPosition));
        return binding.getRoot();
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
        view.setLayoutParams(itemLP);
        ItemCameraAreaBinding binding = DataBindingUtil.getBinding(view);
        Objects.requireNonNull(binding)
                .setArea(getGroup(groupPosition));
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
