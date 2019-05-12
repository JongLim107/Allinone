package com.example.allinone.ui.ipcamera.devices.alarms;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allinone.BR;

import java.util.List;

import androidx.annotation.UiThread;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.transition.TransitionManager;

/**
 * Created by Jong Lim on 10/5/19.
 * <p>
 * Contains a BindingAdapter for assigning a list of items to a ViewGroup.
 */
public class ListBindingAdapters {
    private static final String TAG = "ListBindingAdapters";

    /**
     * Prevent instantiation
     */
    private ListBindingAdapters() {

    }

    /**
     * Assign a list of items to a ViewGroup. This is used with the {@code entries} and
     * {@code layout} attributes in the application namespace. Example Usage:
     * <pre><code>
     *      <LinearLayout
     *          android:layout_width="match_parent"
     *          android:layout_height="wrap_content"
     *          android:orientation="vertical"
     *          app:entries="@{items}"
     *          app:layout="@{@layout/item}"
     *      />
     * </code></pre>
     * <p>
     * In the above, {@code items} is a List or ObservableList. {@code layout} does not
     * need to be hard-coded, but most commonly will be. This BindingAdapter will work
     * with any ViewGroup that only needs addView() and removeView() to manage its Views.
     * <p>
     * The layout, &commat;layout/item for example, must have a single variable named
     * {@code data}.
     */
    @BindingAdapter(value = {"app:entries", "app:layout"}, requireAll = false)
    public static <T> void setEntries(ViewGroup viewGroup, ObservableList<T> oldEntries, int oldLayoutId,
            ObservableList<T> newEntries, int newLayoutId) {
        //TODO UPDATE LIST VIEW WITH NEW ROW BUT DON'T RESET WHOLE LIST
//        if (oldEntries == newEntries && oldLayoutId == newLayoutId) {
//            return; // nothing has changed
//        }
//
//        EntryChangeListener listener = ListenerUtil.getListener(viewGroup, R.id.entryListener);
//        if (oldEntries != newEntries && listener != null && oldEntries != null) {
//            oldEntries.removeOnListChangedCallback(listener);
//        }

        if (newEntries == null) {
            viewGroup.removeAllViews();

        } else {
//            if (listener == null) {
//                listener = new EntryChangeListener(viewGroup, newLayoutId);
//                ListenerUtil.trackListener(viewGroup, listener, R.id.entryListener);
//            } else {
//                listener.setLayoutId(newLayoutId);
//            }
//            if (newEntries != oldEntries) {
//                newEntries.addOnListChangedCallback(listener);
//            }
            resetViews(viewGroup, newLayoutId, newEntries);
        }
    }

    /**
     * Inflates and binds a layout to an entry to the {@code data} variable
     * of the bound layout.
     *
     * @param parent   The ViewGroup containing the list of Views
     * @param layoutId The layout ID to use for the list item
     * @param entry    The data to bind to the inflated View
     * @return A ViewDataBinding, bound to a newly-inflated View with {@code entry}
     * set as the {@code data} variable.
     */
    private static ViewDataBinding bindLayout(ViewGroup parent, int layoutId, Object entry) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        if (!binding.setVariable(BR.model, entry)) {
            String layoutName = parent.getResources().getResourceEntryName(layoutId);
            Log.w(TAG, "There is no variable 'data' in layout " + layoutName);
        }
        return binding;
    }

    /**
     * Clears all Views in {@code parent} and fills it with a View for
     * each item in {@code entries}, bound to the item. If layoutId
     * is 0, no Views will be added.
     *
     * @param parent   The ViewGroup to contain the list of items.
     * @param layoutId The layout ID to inflate for the child Views.
     * @param entries  The list of items to bind to the inflated Views. Each
     *                 item will be bound to a different child View.
     */
    private static void resetViews(ViewGroup parent, int layoutId, List entries) {
        parent.removeAllViews();
        if (layoutId == 0) {
            return;
        }
        for (int i = 0; i < entries.size(); i++) {
            Object entry = entries.get(i);
            ViewDataBinding binding = bindLayout(parent, layoutId, entry);
            parent.addView(binding.getRoot());
        }
    }

    /**
     * Starts a transition only if on KITKAT or higher.
     *
     * @param root The scene root
     */
    private static void startTransition(ViewGroup root) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(root);
        }
    }

    /**
     * A listener to watch for changes in an Observable list and
     * animate the change.
     */
    private static class EntryChangeListener extends ObservableList.OnListChangedCallback {
        private final ViewGroup mTarget;
        private int mLayoutId;

        EntryChangeListener(ViewGroup target, int layoutId) {
            mTarget = target;
            mLayoutId = layoutId;
        }

        void setLayoutId(int layoutId) {
            mLayoutId = layoutId;
        }

        @Override
        public void onChanged(ObservableList observableList) {
            resetViews(mTarget, mLayoutId, observableList);
        }

        @UiThread
        @Override
        public void onItemRangeChanged(ObservableList observableList, int start, int count) {
            if (mLayoutId == 0) {
                return;
            }

            startTransition(mTarget);
            final int end = start + count;
            for (int i = start; i < end; i++) {
                mTarget.removeViewAt(i);
                Object entry = observableList.get(i);
                ViewDataBinding binding = bindLayout(mTarget, mLayoutId, entry);
                mTarget.addView(binding.getRoot(), i);
            }
        }

        @UiThread
        @Override
        public void onItemRangeInserted(ObservableList observableList, int start, int count) {
            if (mLayoutId == 0) {
                return;
            }
            startTransition(mTarget);
            final int end = start + count;
            for (int i = end - 1; i >= start; i--) {
                Object entry = observableList.get(i);
                ViewDataBinding binding = bindLayout(mTarget, mLayoutId, entry);
                mTarget.addView(binding.getRoot(), start);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList observableList, int from, int to, int count) {
            if (mLayoutId == 0) {
                return;
            }
            startTransition(mTarget);
            for (int i = 0; i < count; i++) {
                View view = mTarget.getChildAt(from);
                mTarget.removeViewAt(from);
                int destination = (from > to) ? to + i : to;
                mTarget.addView(view, destination);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList observableList, int start, int count) {
            if (mLayoutId == 0) {
                return;
            }
            startTransition(mTarget);
            for (int i = 0; i < count; i++) {
                mTarget.removeViewAt(start);
            }
        }
    }
}