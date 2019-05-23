package com.example.allinone.ui.ipcamera.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.allinone.databinding.ItemPlatformBinding;
import com.example.allinone.entity.PlatformEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by Jong Lim on 29/4/19.
 */
public class PlatformSpinnerAdapter extends ArrayAdapter<PlatformEntity> {

    private final List<PlatformEntity> mPlatforms;
    private final LoginViewModel viewModel;

    private LifecycleOwner mLifecycleOwner;

    PlatformSpinnerAdapter(@NonNull Context context, List<PlatformEntity> platforms, LoginViewModel viewModel) {
        super(context, 0, platforms);
        mLifecycleOwner = (LifecycleOwner) context;
        mPlatforms = platforms;
        this.viewModel = viewModel;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemPlatformBinding binding;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemPlatformBinding.inflate(inflater, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }

        binding.setModel(mPlatforms.get(position));
        binding.setLifecycleOwner(mLifecycleOwner);

        binding.executePendingBindings();
        return binding.getRoot();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get SpinnerBar layout view
        TextView tv = new TextView(parent.getContext());
        PlatformEntity platform = mPlatforms.get(position);
        tv.setText(platform.getTitle());
        return tv;
    }

}
