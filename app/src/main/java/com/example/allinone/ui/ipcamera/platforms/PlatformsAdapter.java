package com.example.allinone.ui.ipcamera.platforms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.allinone.R;
import com.example.allinone.databinding.ItemPlatformBinding;
import com.example.allinone.entity.PlatformEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class PlatformsAdapter extends ArrayAdapter<PlatformEntity> {
    private PlatformsViewModel viewModel;

    PlatformsAdapter(@NonNull Context context, PlatformsViewModel vm, @NonNull List<PlatformEntity> objects) {
        super(context, R.layout.item_platform, objects);
        this.viewModel = vm;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemPlatformBinding binding;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemPlatformBinding.inflate(inflater);
            convertView = binding.getRoot();
            convertView.setTag(binding);

        } else {  // Recycling view
            binding = (ItemPlatformBinding) convertView.getTag();
        }

        PlatformEntity plat = getItem(position);
        binding.setModel(plat);
        binding.setHandler(viewModel);
        return convertView;
    }
}
