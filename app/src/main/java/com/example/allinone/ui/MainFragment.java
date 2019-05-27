package com.example.allinone.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.databinding.FragmentMainBinding;
import com.example.allinone.entity.TrackMeta;
import com.example.allinone.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 25/5/19.
 */
public class MainFragment extends BaseFragment<FragmentMainBinding, MainViewModel> {

    /**
     * 歌曲列表
     */
    private ArrayList<TrackMeta> mTracks;           // 当前选中歌曲列表，有以下几种三种列表
    private ArrayList<TrackMeta> mLocalTracks;      // 1.本地歌曲列表数据源
    private ArrayList<TrackMeta> mFavoriteTracks;   // 2.最爱歌曲列表数据源
    private ArrayList<TrackMeta> mOtherTracks;      // 3.其他歌曲列表数据源
    private PopupWindow mTracksWindow;              // 歌曲列表弹出窗口，包含歌曲ListView

    private PlayListAdapter mTracksAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();

        //listType = 0， 查找并将地音频映射到 local.json
        mLocalTracks = new ArrayList<>();
        //listType = 1， 从本地favorite.json文件获取歌曲列表
        mFavoriteTracks = new ArrayList<>();
        //listType = 2， 从本地已有.json文件获取歌曲列表
        mOtherTracks = new ArrayList<>();
        initTrackList(mLocalTracks, 0);
        initTrackList(mFavoriteTracks, 0);
        mTracks = mLocalTracks;
        //初始化声音列表窗口，这个将是动态创建的的view
        initTrackListWindow(LayoutInflater.from(getContext()));

        //初始化播放列表列
        initPlayListsView();
    }

    private void initTrackList(ArrayList<TrackMeta> tracks, int begin) {
        tracks.clear();
        if (tracks.equals(mLocalTracks)) {
            FileUtils.querySongs(getContext(), tracks);
            viewModel.setFoundSongs("共发现 " + mLocalTracks.size() + " 首歌曲");
            //save JSON To File
            FileUtils.writeTracksToJSONFile(tracks, FileUtils.getLocalListPath(), begin);
        } else if (tracks.equals(mFavoriteTracks)) {
            if (FileUtils.isExists(FileUtils.getFavoriteListPath())) {
                FileUtils.readTracksFromJSONFile(tracks, FileUtils.getFavoriteListPath());
                FileUtils.writeTracksToJSONFile(tracks, FileUtils.getFavoriteListPath(), begin);
            } else {
                //创建空的喜爱列表
                FileUtils.writeTracksToJSONFile(null, FileUtils.getFavoriteListPath(), begin);
            }
        } else if (tracks.equals(mOtherTracks)) {
            File[] files = FileUtils.queryJSONFiles(FileUtils.getJSONFilePath());
            if (files != null && files.length > 0) {
                FileUtils.readTracksFromJSONFile(tracks, FileUtils.getFavoriteListPath());
            } else {
                //创建空的喜爱列表
                FileUtils.writeTracksToJSONFile(null, FileUtils.getFavoriteListPath(), begin);
            }
        }
    }


    private void initTrackListWindow(LayoutInflater inflater) {
        View listLayout = inflater.inflate(R.layout.layout_play_list, null);
        listLayout.setFocusableInTouchMode(true);
        mTracksAdapter = new PlayListAdapter(getContext(), viewModel, mLocalTracks);

        // 歌曲列表
        ListView playListView = listLayout.findViewById(R.id.tracksView);

        // 当 mListView 为 PullToRefreshListView 时，position从1开始，当添加了HeadView时 position从2开始
        playListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initTrackList(mTracks, position);
                if (mTracks.equals(mLocalTracks)) {
                    ToastUtils.showShort(FileUtils.getLocalListPath(), "Send Local Playlist");
                } else if (mTracks.equals(mFavoriteTracks)) {
                    ToastUtils.showShort(FileUtils.getFavoriteListPath(), "Send Favorite Playlist");
                }
                changeListWindowState();
            }
        });
        playListView.setAdapter(mTracksAdapter);

        mTracksWindow = new PopupWindow(listLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mTracksWindow.setAnimationStyle(R.style.MenuAnimationFade);
        mTracksWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initPlayListsView() {
        /**
         * 播放列表
         */
        ArrayList<HashMap<String, String>> mPlayLists = new ArrayList<>();
        File[] files = FileUtils.queryJSONFiles(FileUtils.getJSONFilePath());
        if (files != null && files.length > 0) {
            for (File f : files) {
                String name = f.getName();
                HashMap<String, String> map = new HashMap<>();
                map.put("playListName", name.substring(0, name.lastIndexOf(".")));
                map.put("itemPath", f.getAbsolutePath());
                mPlayLists.add(map);
            }
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), mPlayLists, R.layout.item_play_list,
                new String[]{"playListName"}, new int[]{R.id.itemTextView});
        binding.lvPlaylists.setAdapter(simpleAdapter);

    }

    /**
     * 开关播放列表
     */
    private void changeListWindowState() {
        if (mTracksWindow.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
            mTracksWindow.dismiss();
        } else {
            // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
            mTracksAdapter.setDataList(mTracks);
            mTracksWindow.showAtLocation(binding.lyLocalMusic, Gravity.BOTTOM, 0, 0);
        }
    }
}
