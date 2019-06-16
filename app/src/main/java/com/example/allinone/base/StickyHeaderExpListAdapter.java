package com.example.allinone.base;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;


/**
 * Created by JongLim on 6/15/2019.
 */
public class StickyHeaderExpListAdapter<T extends StickyHeaderGroupItem> extends BaseExpandableListAdapter
        implements StickyHeaderExpListView.HeaderAdapter {
    private static final String TAG = "ExpListAdt";

    protected Context mContext;
    protected LayoutInflater mInflater;

    private SparseIntArray groupStatusMap = new SparseIntArray();
    private List<T> mGroupArrayData;

    public StickyHeaderExpListAdapter(Context context, List<T> groupArray) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        setList(groupArray);
    }

    public void replaceData(List<T> areas) {
        setList(areas);
    }

    private void setList(List<T> array) {
        mGroupArrayData = array;
        groupStatusMap = new SparseIntArray();
        for (int i = 0; i < mGroupArrayData.size(); i++) {
            T a = mGroupArrayData.get(i);
            groupStatusMap.put(i, a.isExpanded() ? 1 : 0);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mGroupArrayData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        T group = getGroup(groupPosition);
        if (group == null) {
            return 0;
        }
        return group.childrenSize();
    }

    @Override
    public T getGroup(int groupPosition) {
        if (mGroupArrayData != null && groupPosition < mGroupArrayData.size()) {
            return mGroupArrayData.get(groupPosition);
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        T group = getGroup(groupPosition);
        if (group == null) {
            return null;
        }

        List<Object> cs = group.getChildren();
        if (cs.size() < childPosition || childPosition < 0) {
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
        if (childPosition < 0 || childPosition >= getGroup(groupPosition).childrenSize()) {
            return -1;
        }

        int offset = 0;
        for (int i = 0; i < groupPosition; i++) {
            T group = getGroup(i);
            if (group != null) {
                offset += group.childrenSize();
            } else {
                return -1;
            }
        }
        return offset + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        View view = (convertView != null) ? convertView : mInflater.inflate(R.layout.cell_header_3, null);
//
//        MyGroupModel groupItem = getGroup(groupPosition);
//        MyGroupHolder holder = (MyGroupHolder) view.getTag();
//        if (holder == null) {
//            holder = new MyGroupHolder(mContext, view, groupItem, isExpanded);
//            view.setTag(holder);
//        } else {
//            holder.setTitle(groupItem.getTitle());
//            holder.setBtnMore(isExpanded);
//        }
//        return view;
        return null;
    }

    @Override
    public View getChildView(
            int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        View view = (convertView != null) ? convertView : mInflater.inflate(R.layout.cell_item_3, null);
//
//        MyChildHolder holder = (MyChildHolder) view.getTag();
//        if (holder == null) {
//            holder = new MyChildHolder(mContext, view);
//            view.setTag(holder);
//        }
//
//        final int index = (int) getChildId(groupPosition, childPosition);
//        Item child = (Item) getChild(groupPosition, childPosition);
//        if (child != null) {
//            holder.setTitle(child.getName());
//            holder.setId(index);
//        }
//        return view;
        return null;
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
        } else if (childPosition == -1 && getGroupStatus(groupPosition) == 0) {
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
//        T groupItem = getGroup(groupPosition);
//        StickyHeaderGroupHolder holder = new StickyHeaderGroupHolder(view, groupItem, true);
//        view.setTag(holder);
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    @Override
    public void setGroupStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    @Override
    public int getGroupStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
