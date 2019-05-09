package com.example.allinone.ui.ipcamera.devices.cameras;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.allinone.base.StickyExpandableListView;
import com.example.allinone.databinding.ItemCameraAreaBinding;
import com.example.allinone.databinding.ItemCameraDevBinding;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;

import java.util.List;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class CamerasListAdapter extends BaseExpandableListAdapter implements StickyExpandableListView.HeaderAdapter {

    private final Context mContext;
    private List<AreaEntity> mAreas;
    private SparseIntArray groupStatusMap;

    public CamerasListAdapter(Context context, List<AreaEntity> groupArray) {
        this.mContext = context;
        setList(groupArray);
    }

    public void replaceData(List<AreaEntity> areas) {
        setList(areas);
    }

    private void setList(List<AreaEntity> array) {
        mAreas = array;
        groupStatusMap = new SparseIntArray();
        for (int i = 0; i < mAreas.size(); i++) {
            AreaEntity a = mAreas.get(i);
            groupStatusMap.put(i, a.isExpanded() ? 1 : 0);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mAreas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        AreaEntity group = getGroup(groupPosition);
        if (group == null) {
            return 0;
        }
        return group.childSize();
    }

    @Override
    public AreaEntity getGroup(int groupPosition) {
        if (mAreas.size() < groupPosition || groupPosition < 0){
            return null;
        }
        return mAreas.get(groupPosition);
    }

    @Override
    public CameraEntity getChild(int groupPosition, int childPosition) {
        AreaEntity area = getGroup(groupPosition);
        if (area == null) {
            return null;
        }

        List<CameraEntity> cs = area.getCameras();
        if (cs.size() < childPosition || childPosition < 0){
            return null;
        }

        return cs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (childPosition < 0 || childPosition >= getGroup(groupPosition).childSize()) {
            return -1;
        }

        int offset = 0;
        for (int i = 0; i < groupPosition; i++) {
            AreaEntity area = getGroup(i);
            if (area != null) {
                offset += area.childSize();
            } else {
                return -1;
            }
        }
        return offset + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true; // Verify
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
        ItemCameraDevBinding binding;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemCameraDevBinding.inflate(inflater);
            convertView = binding.getRoot();
            convertView.setTag(binding);

        } else {  // Recycling view
            binding = (ItemCameraDevBinding) convertView.getTag();
        }

        CameraEntity camera = getChild(groupPos, childPos);
        binding.setModel(camera);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void configureHeader(View view, int groupPosition, int childPosition, int alpha) {
        ItemCameraAreaBinding binding = (ItemCameraAreaBinding) view.getTag();
        binding.setModel(getGroup(groupPosition));
        binding.executePendingBindings();
    }

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

    @Override
    public void setGroupStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
