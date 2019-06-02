package com.example.allinone.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Created by Jong Lim on 25/5/19.
 */
public class MainViewModel extends BaseViewModel {
    ObservableField<String> foundSongs = new ObservableField<>("共发现...首歌");

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> getFoundSongs() {
        return foundSongs;
    }

    public void setFoundSongs(String foundSongs) {
        this.foundSongs.set(foundSongs);
    }
}
