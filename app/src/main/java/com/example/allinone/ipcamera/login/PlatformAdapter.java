package com.example.allinone.ipcamera.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.allinone.data.login.Platform;
import com.example.allinone.databinding.PlatformItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by Jong Lim on 29/4/19.
 */
public class PlatformAdapter extends ArrayAdapter<Platform> {

    private List<Platform> mPlatforms;

    private LifecycleOwner mLifecycleOwner;

    public PlatformAdapter(@NonNull Context context, LoginViewModel viewModel, @NonNull List<Platform> platforms) {
        super(context, 0, platforms);
        mLifecycleOwner = (LifecycleOwner) context;
        setList(platforms);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initDropDownView(position, convertView, parent);
    }

    public View initDropDownView(int position, View view, ViewGroup viewGroup) {
        TextView tv = new TextView(viewGroup.getContext());
        Platform platform = mPlatforms.get(position);
        tv.setText(platform.getTitle());
        return tv;
    }

    public View initView(int position, View view, ViewGroup viewGroup) {
        PlatformItemBinding binding;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = PlatformItemBinding.inflate(inflater, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }

        binding.setPlatform(mPlatforms.get(position));
        binding.setLifecycleOwner(mLifecycleOwner);

        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void setList(List<Platform> tasks) {
        mPlatforms = tasks;
        notifyDataSetChanged();
    }
}
