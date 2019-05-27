package com.example.allinone.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.daimajia.swipe.SwipeLayout;
import com.example.allinone.R;
import com.example.allinone.databinding.ItemMusicBinding;
import com.example.allinone.entity.TrackMeta;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Jong Lim on 25/5/19.
 */
public class PlayListAdapter extends ArrayAdapter<TrackMeta> {

    public PlayListAdapter(@NonNull Context context, MainViewModel viewModel, @NonNull List<TrackMeta> objects) {
        super(context, R.layout.item_music, objects);
    }

    @Override
    public void remove(@Nullable TrackMeta object) {
        super.remove(object);
    }

    public void setDataList(List objects){
        clear();
        addAll(objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemMusicBinding binding;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemMusicBinding.inflate(inflater);
            convertView = binding.getRoot();
            convertView.setTag(binding);

        } else {  // Recycling view
            binding = (ItemMusicBinding) convertView.getTag();
        }

        TrackMeta music = getItem(position);
        binding.setModel(music);
//        binding.setHandler(viewModel);
//        binding.slPlatform.setShowMode(SwipeLayout.ShowMode.PullOut);
        return convertView;
    }
}
