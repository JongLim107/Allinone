package com.example.allinone.binding;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.allinone.BR;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;

/**
 * Created by Jong Lim on 10/5/19.
 */
public class TableListBindingAdapters {
    private static final String TAG = "ListBindingAdapters";

    /**
     * Prevent instantiation
     */
    private TableListBindingAdapters() {

    }

    @BindingAdapter(value = {"dataList", "itemLayout"}, requireAll = false)
    public static <T> void setEntries(ViewGroup viewGroup, ObservableList<T> oldDataList, int oldItemLayoutId,
            ObservableList<T> newDataList, int newItemLayoutId) {
        //TODO UPDATE LIST VIEW WITH NEW ROW BUT DON'T RESET WHOLE LIST

        if (newDataList == null) {
            viewGroup.removeAllViews();
        } else {
            resetViews(viewGroup, newItemLayoutId, newDataList);
        }
    }

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

}