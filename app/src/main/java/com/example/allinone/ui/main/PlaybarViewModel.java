package com.example.allinone.ui.main;

import android.app.Application;
import android.media.MediaPlayer;
import android.widget.SeekBar;

import com.example.allinone.entity.TrackMeta;
import com.example.allinone.utils.TimeUtils;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by JongLim on 6/2/2019.
 */
public class PlaybarViewModel extends BaseViewModel {
    public ObservableField<String> trackTitle = new ObservableField<>("");
    public ObservableField<Integer> curProgress = new ObservableField<>(0);
    public ObservableField<String> curTime = new ObservableField<>("00:00");
    public ObservableField<String> duration = new ObservableField<>("00:00");
    public ObservableBoolean isPlaying = new ObservableBoolean(false);
    private MediaPlayer player = null;
    private List<TrackMeta> playList = null;
    private int curPosition = 0;
    private Thread mUpdateThread = null;

    public PlaybarViewModel(@NonNull Application application) {
        super(application);
    }

    public void setPlayer(MediaPlayer player, List<TrackMeta> list, int position) {
        if (player != null) {
            this.player = player;
            this.playList = list;
            this.curPosition = position;

            String MP3_PATH = "/sdcard/Music/Kate Voegele - Sandcastles.mp3";
            String title = MP3_PATH.substring(MP3_PATH.lastIndexOf("/"));
            if (list != null && list.size() > 0) {
                position = position % list.size();
                TrackMeta meta = list.get(position);
                MP3_PATH = meta.getUrl();
                title = meta.getArtist() + " - " + meta.getName();
            }

            try {
                player.setDataSource(MP3_PATH);
                player.prepare();
                player.start();
                isPlaying.set(player.isPlaying());
                startLooper(player, title);
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtils.showLong("Can't find the default file named Music/Kate Voegele - Sandcastles.mp3");
            }
        }
    }

    private void startLooper(MediaPlayer player, String title) {
        if (title != null) {
            trackTitle.set(title);
        }

        if (mUpdateThread != null && mUpdateThread.isAlive()) {
            try {
                mUpdateThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int total = player.getDuration();
        duration.set(TimeUtils.formatMediaTime(total / 1000));
        isPlaying.set(true);
        mUpdateThread = new Thread(() -> {
            while (player.isPlaying()) {
                int cur = player.getCurrentPosition();
                curProgress.set(Math.round(100 * cur / total));
                curTime.set(TimeUtils.formatMediaTime(cur / 1000));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isPlaying.set(false);
        });
        mUpdateThread.start();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            int total = player.getDuration();
            int cur = progress * total / 100;
            player.seekTo(cur);
            curTime.set(TimeUtils.formatMediaTime(cur / 1000));
        }
    }

    public void onPlayButtonClick() {
        if (player != null) {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
                startLooper(player, null);
            }
        }
    }

    public void onPlayNextClick() {
        if (player != null) {
            player.stop();
            player.reset();
            mUpdateThread.interrupt();
            setPlayer(player, playList, curPosition + 1);

        } else {
            player = new MediaPlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()) {
            player.stop();
        }
    }
}
