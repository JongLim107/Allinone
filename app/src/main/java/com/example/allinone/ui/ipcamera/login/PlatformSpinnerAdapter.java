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

    private List<PlatformEntity> mPlatforms;

    private LifecycleOwner mLifecycleOwner;

    public PlatformSpinnerAdapter(@NonNull Context context, LoginViewModel viewModel, @NonNull List<PlatformEntity> platforms) {
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
        PlatformEntity platform = mPlatforms.get(position);
        tv.setText(platform.getTitle());
        return tv;
    }

    public View initView(int position, View view, ViewGroup viewGroup) {
        ItemPlatformBinding binding;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = ItemPlatformBinding.inflate(inflater, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }

        binding.setPlatform(mPlatforms.get(position));
        binding.setLifecycleOwner(mLifecycleOwner);

        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void setList(List<PlatformEntity> tasks) {
        mPlatforms = tasks;
        notifyDataSetChanged();
    }
}
