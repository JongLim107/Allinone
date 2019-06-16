package com.example.allinone.base;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

/**
 * Created by JongLim on 5/4/19.
 */
public class StickyHeaderExpListView extends ExpandableListView implements OnScrollListener, OnGroupClickListener {
    private static final String TAG = "StickyExpLV";

    private static final int MAX_ALPHA = 255;

    // 用于在列表头显示的 View
    private View mHeaderView;

    // 列表头item 是否可见
    private boolean mHeaderViewVisible;

    private int mHeaderViewWidth;

    private int mHeaderViewHeight;

    private HeaderAdapter mHeaderAdapter;

    private float mTouchDownX;

    private float mTouchDownY;

    private boolean offsetForTouch;

    private int mOldStatus = -1;


    /**
     * Constructor
     */
    public StickyHeaderExpListView(Context context) {
        super(context);
    }

    public StickyHeaderExpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyHeaderExpListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeaderView(View view) {
        mHeaderView = view;
        if (mHeaderView != null) {
            setFadingEdgeLength(0); // Verify
        }
        // Log.v(TAG, "setHeaderView()");
        requestLayout();
    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        // Log.v(TAG, "setAdapter()");
        mHeaderAdapter = (HeaderAdapter) adapter;
        setOnScrollListener(this);
        setOnGroupClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v(TAG, "onMeasure()");
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    /**
     * 列表界面更新时调用该方法(如滚动时)
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // Log.v(TAG, "dispatchDraw()");
        if (mHeaderViewVisible) {
            //分组栏是直接绘制到界面中，而不是加入到ViewGroup中
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.v(TAG, "onLayout()");
        super.onLayout(changed, left, top, right, bottom);
        final long flatPos = getExpandableListPosition(getFirstVisiblePosition());
        final int groupPos = ExpandableListView.getPackedPositionGroup(flatPos);
        final int childPos = ExpandableListView.getPackedPositionChild(flatPos);
        if (mHeaderAdapter != null) {
            int status = mHeaderAdapter.getHeaderStatus(groupPos, childPos);
            if (mHeaderView != null && status != mOldStatus) {
                mOldStatus = status;
                mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
            }
        }

        configureHeaderView(groupPos, childPos);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Log.v(TAG, "onScroll()");
        final long flatPos = getExpandableListPosition(firstVisibleItem);
        final int groupPos = ExpandableListView.getPackedPositionGroup(flatPos);
        final int childPos = ExpandableListView.getPackedPositionChild(flatPos);
        configureHeaderView(groupPos, childPos);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Log.v(TAG, "onScrollStateChanged()");
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * 如果 HeaderView 是可见的 , 此函数用于判断是否点击了 HeaderView, 并对做相应的处理 ,
     * 因为 HeaderView 是画上去的 , 所以设置事件监听是无效的 , 只有自行控制 .
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Log.v(TAG, "onTouchEvent()");
        if (mHeaderViewVisible) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchDownX = ev.getX();
                    mTouchDownY = ev.getY();
                    if (mTouchDownX <= mHeaderViewWidth - 60 && mTouchDownY <= mHeaderViewHeight) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float x = ev.getX();
                    float y = ev.getY();
                    float offsetX = Math.abs(x - mTouchDownX);
                    float offsetY = Math.abs(y - mTouchDownY);
                    // 如果 HeaderView 是可见的 , 点击在 HeaderView 内 , 那么触发 headerClick()
                    if (x <= mHeaderViewWidth && y <= mHeaderViewHeight && offsetX <= 32 && offsetY <= 32) {
                        if (mHeaderView != null) {
                            headerViewClick(offsetForTouch ? 1 : 0);
                        }
                        return true;
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * Catch TouchEvent for current View first, otherwise this ListView may not be able to catch {@link #onTouchEvent}
     * because if children have implementing the {@link #onTouchEvent}
     *
     * @param ev {@link MotionEvent}
     * @return ? true: children can not get this touchDown event. false: pass to children
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mHeaderView != null && mHeaderViewVisible) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                offsetForTouch = false;
                float x = ev.getX();
                float y = ev.getY();
                if (x <= mHeaderViewWidth && y <= mHeaderViewHeight) {
                    offsetForTouch = y > mHeaderView.getBottom();
                    return true;
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 点击了 Group 触发的事件 , 要根据根据当前点击 Group 的状态来
     */
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        // Log.v(TAG, "onGroupClick(" + groupPosition + ")");
        if (mHeaderAdapter.getGroupStatus(groupPosition) == 0) {
            mHeaderAdapter.setGroupStatus(groupPosition, 1);
            parent.expandGroup(groupPosition);
            //Header自动置顶
            //parent.setSelectedGroup(groupPosition);
        } else if (mHeaderAdapter.getGroupStatus(groupPosition) == 1) {
            mHeaderAdapter.setGroupStatus(groupPosition, 0);
            parent.collapseGroup(groupPosition);
        }
        // 返回 true 才可以弹回第一行 , 不知道为什么
        return true;
    }

    public View getGroupView(int groupPosition) {
        long packedPosition = getPackedPositionForGroup(groupPosition);
        int flatPosition = getFlatListPosition(packedPosition);
        int first = getFirstVisiblePosition();
        return getChildAt(flatPosition - first);
    }

    /**
     * 点击 HeaderView 触发的事件
     */
    private void headerViewClick(int offset) {
        // Log.v(TAG, "headerViewClick()");
        long packedPosition = getExpandableListPosition(this.getFirstVisiblePosition());
        int groupPosition = offset + ExpandableListView.getPackedPositionGroup(packedPosition);

        if (mHeaderAdapter.getGroupStatus(groupPosition) == 1) {
            this.collapseGroup(groupPosition);
            mHeaderAdapter.setGroupStatus(groupPosition, 0);
        } else {
            this.expandGroup(groupPosition);
            mHeaderAdapter.setGroupStatus(groupPosition, 1);
        }

        this.setSelectedGroup(groupPosition);
    }

    public void configureHeaderView(int groupPosition, int childPosition) {
        if (mHeaderView == null || mHeaderAdapter == null ||
                ((ExpandableListAdapter) mHeaderAdapter).getGroupCount() == 0) {
            return;
        }

        int state = mHeaderAdapter.getHeaderStatus(groupPosition, childPosition);
        // Log.v(TAG, String.format("Header state %d, %d, %d", state, groupPosition, childPosition));

        switch (state) {
            case HeaderAdapter.PINNED_HEADER_GONE: {
                mHeaderViewVisible = false;
                break;
            }
            case HeaderAdapter.PINNED_HEADER_VISIBLE: {
                int y = 0;
                View nextGroup = getGroupView(groupPosition + 1);
                if (nextGroup != null) {
                    int top = nextGroup.getTop();
                    int headerHeight = mHeaderView.getHeight();
                    if (top >= headerHeight) {
                        y = 0;
                    } else {
                        y = (top - headerHeight);
                    }
                }

                mHeaderAdapter.configureHeader(mHeaderView, groupPosition, childPosition, MAX_ALPHA);
                if (mHeaderView.getTop() != y) {
                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
                }
                mHeaderViewVisible = true;
                break;
            }
            case HeaderAdapter.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                int bottom = firstView.getBottom();
                // intitemHeight = firstView.getHeight();
                int headerHeight = mHeaderView.getHeight();
                int y, alpha;
                if (bottom < headerHeight) {
                    y = (bottom - headerHeight);
                    alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }

                mHeaderAdapter.configureHeader(mHeaderView, groupPosition, childPosition, alpha);
                if (mHeaderView.getTop() != y) {
                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
                }

                mHeaderViewVisible = true;
                break;
            }
        }
    }

    /**
     * Adapter 接口 . 列表必须实现此接口 .
     */
    public interface HeaderAdapter {
        /**
         * Header Status
         */
        int PINNED_HEADER_GONE = 0;
        int PINNED_HEADER_VISIBLE = 1;
        int PINNED_HEADER_PUSHED_UP = 2;

        /**
         * 获取 Header 的状态
         */
        int getHeaderStatus(int groupPosition, int childPosition);

        /**
         * 配置 Header, 让 Header 知道显示的内容
         */
        void configureHeader(View view, int groupPosition, int childPosition, int alpha);

        /**
         * 设置组展开的状态
         */
        void setGroupStatus(int groupPosition, int status);

        /**
         * 获取组展开的状态
         */
        int getGroupStatus(int groupPosition);
    }

}