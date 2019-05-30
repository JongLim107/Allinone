package com.example.allinone.ui;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.app.AppApplication;
import com.example.allinone.databinding.FragmentMainBinding;
import com.example.allinone.databinding.LayoutPlayListBinding;
import com.example.allinone.entity.TrackMeta;
import com.example.allinone.utils.ACache;
import com.example.allinone.utils.FileUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
    private ArrayList<TrackMeta> localTracks;      // 1.本地歌曲列表数据源
    private ArrayList<TrackMeta> favoriteTracks;   // 2.最爱歌曲列表数据源
    private ArrayList<TrackMeta> otherTracks;      // 3.其他歌曲列表数据源
    
    private PopupWindow mTracksWindow;              // 歌曲列表弹出窗口，包含歌曲ListView
    private PlayListAdapter mTracksAdapter;
    private LayoutPlayListBinding playListWindowBinding;

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
        localTracks = new ArrayList<>();
        //listType = 1， 从本地favorite.json文件获取歌曲列表
        favoriteTracks = new ArrayList<>();
        //listType = 2， 从本地已有.json文件获取歌曲列表
        otherTracks = new ArrayList<>();

        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> initialCache())
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();
    }

    private void initialCache() {
        String path[] = FileUtils.getStoragePath(getContext());
        String gACacheDir;
        if (path != null) {
            gACacheDir = path[0];
        } else {
            gACacheDir = FileUtils.getStoragePath();
        }
        assert gACacheDir != null;
        File file = new File(gACacheDir, "Allinone");
        AppApplication.setACacheDir(gACacheDir);
        AppApplication.setACache(ACache.get(file));


        initTrackList(localTracks, 0);
        initTrackList(favoriteTracks, 0);

        initPlayListsView();
        initTrackListWindow(localTracks);
    }

    // create or query local music file to json format play list.
    private void initTrackList(ArrayList<TrackMeta> tracks, int begin) {
        tracks.clear();
        if (tracks.equals(localTracks)) {
            FileUtils.querySongs(getContext(), tracks);
            viewModel.setFoundSongs("共发现 " + localTracks.size() + " 首歌曲");
            //save JSON To File
            FileUtils.writeTracksToJSONFile(tracks, FileUtils.getLocalListPath(), begin);
        } else if (tracks.equals(favoriteTracks)) {
            if (FileUtils.isExists(FileUtils.getFavoriteListPath())) {
                FileUtils.readTracksFromJSONFile(tracks, FileUtils.getFavoriteListPath());
                FileUtils.writeTracksToJSONFile(tracks, FileUtils.getFavoriteListPath(), begin);
            } else {
                //创建空的喜爱列表
                FileUtils.writeTracksToJSONFile(null, FileUtils.getFavoriteListPath(), begin);
            }
        } else if (tracks.equals(otherTracks)) {
            File[] files = FileUtils.queryJSONFiles(FileUtils.getJSONFilePath());
            if (files != null && files.length > 0) {
                FileUtils.readTracksFromJSONFile(tracks, FileUtils.getFavoriteListPath());
            } else {
                //创建空的喜爱列表
                FileUtils.writeTracksToJSONFile(null, FileUtils.getFavoriteListPath(), begin);
            }
        }
    }

    // 初始化播放列表列
    private void initPlayListsView() {
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

    // 初始化声音列表窗口，这个将是动态创建的的view
    private void initTrackListWindow(ArrayList<TrackMeta> localTracks) {
        mTracks = localTracks; // set localTracks as default play list

        playListWindowBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_play_list, binding.rlMainContent,
                false);

        playListWindowBinding.lyPlayActList.setFocusableInTouchMode(true);

        mTracksAdapter = new PlayListAdapter(getContext(), viewModel, localTracks);
        playListWindowBinding.tracksView.setAdapter(mTracksAdapter);
        playListWindowBinding.tracksView.setOnItemClickListener((parent, view, position, id) -> {
            // 当 mListView 为 PullToRefreshListView 时，position从1开始，当添加了HeadView时 position从2开始
            initTrackList(mTracks, position);
            if (mTracks.equals(localTracks)) {
                ToastUtils.showShort(FileUtils.getLocalListPath(), "Send Local Playlist");
            } else if (mTracks.equals(favoriteTracks)) {
                ToastUtils.showShort(FileUtils.getFavoriteListPath(), "Send Favorite Playlist");
            }
            changeListWindowState(null);
        });


        mTracksWindow = new PopupWindow(playListWindowBinding.lyPlayActList, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mTracksWindow.setAnimationStyle(R.style.MenuAnimationFade);
        mTracksWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.ivPostLocalList.setOnClickListener(this::onOpenList);
        binding.tvFavoriteSongs.setOnClickListener(this::onOpenFavorite);
    }

    /**
     * onClick
     */
    public void onOpenList(View view) {
        playListWindowBinding.tvListTitle.setText("本地歌曲列表：");
        playListWindowBinding.tvListTitle.setTextColor(Color.BLACK);
        changeListWindowState(localTracks);
    }

    public void onOpenFavorite(View view) {
        playListWindowBinding.tvListTitle.setText("喜欢歌曲列表：");
        playListWindowBinding.tvListTitle.setTextColor(Color.RED);
        initTrackList(favoriteTracks, 0);
        changeListWindowState(favoriteTracks);
    }

//    public void onOpenNetSong(View view) {
//        mBoxControler.setSyncing(false);
//        Intent intent = new Intent(mContext, ProductCartActivity.class);
//        startActivity(intent);
//    }
//
//    public void onOpenRadio(View view) {
//        mBoxControler.setSyncing(false);
//        Intent intent = new Intent(mContext, MainFragmentActivity.class);
//        startActivity(intent);
//    }

//    public void onCreateList(View view) {
//        View layout = getLayoutInflater().inflate(R.layout.dialog_build_playlist, (ViewGroup) findViewById(R.id.buildPlDialog));
//        final EditText etName = (EditText) layout.findViewById(R.id.etname);
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("创建播放列表").setView(layout);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String name = etName.getText().toString();
//                if (!mPlayLists.isEmpty()) {
//                    for (Map<String, String> tmp : mPlayLists) {
//                        if (tmp.get("itemTextView").equals(name)) {
//                            JLLog.showToast(mContext, "改名字已存在，请重新输入！");
//                            return;
//                        }
//                    }
//                }
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("itemTextView", name);
//                mPlayLists.add(map);
//                FileUtils.writeTracksToJSONFile(null, FileUtils.newJSONFilePath(name), 1);
//                mPlayListsView.refreshDrawableState();
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.show();
//    }

    /**
     * 开关播放列表
     */
    private void changeListWindowState(ArrayList<TrackMeta> tracks) {
        if (mTracksWindow.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
            mTracksWindow.dismiss();
        } else {
            mTracks = tracks;
            // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
            mTracksAdapter.setDataList(tracks);
            mTracksWindow.showAtLocation(binding.lyLocalMusic, Gravity.BOTTOM, 0, 0);
        }
    }
}
