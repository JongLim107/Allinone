package com.example.allinone.ui;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 25/5/19.
 */
public class MainFragment extends BaseFragment<FragmentMainBinding, MainViewModel> {

    private MediaPlayer mediaPlayer;
    private PopupWindow popupWindow;              // 歌曲列表弹出窗口，包含歌曲ListView
    private ArrayList<TrackMeta> popupTracks;      // 当前选中歌曲列表，有以下几种2种列表
    private ArrayList<TrackMeta> localTracks;      // 1.本地歌曲列表数据源
    private ArrayList<TrackMeta> favoriteTracks;   // 2.最爱歌曲列表数据源

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

        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> initialCache())
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.ivPostLocalList.setOnClickListener((view) -> showPopupWindow(localTracks, "本地歌曲列表："));
        binding.ivPostLocalList.setOnClickListener((view) -> playTracks(localTracks, 0));
        binding.tvFavoriteSongs.setOnClickListener((view) -> showPopupWindow(favoriteTracks, "喜欢歌曲列表："));
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
        AppApplication.setACacheDir(file.getAbsolutePath());
        AppApplication.setACache(ACache.get(file));


        initTrackList(localTracks, 0);
        initTrackList(favoriteTracks, 0);

        initPlayListsView();
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
        }
    }

    // 初始化播放列表列的listView
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

    // 初始化声音列表的Popup Window，这个将是动态创建的的view
    private void showPopupWindow(ArrayList<TrackMeta> tracks, String title) {
        if (popupWindow != null && popupWindow.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
            popupWindow.dismiss();
        } else {
            LayoutPlayListBinding popupBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    R.layout.layout_play_list, binding.rlMainContent, false);

            popupBinding.lyPlayActList.setFocusableInTouchMode(true);

            PlayListAdapter popupAdapter = new PlayListAdapter(getContext(), viewModel, tracks);
            popupBinding.tracksView.setAdapter(popupAdapter);
            popupBinding.tracksView.setOnItemClickListener(this::onTracksItemClick);
            popupBinding.tvListTitle.setText(title);

            popupWindow = new PopupWindow(popupBinding.lyPlayActList, ViewGroup.LayoutParams.MATCH_PARENT,
                    getPopupWindowHeight(), true);
            popupWindow.setBackgroundDrawable(null);
            popupWindow.setAnimationStyle(R.style.MenuAnimationFade);

            // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
            popupWindow.showAtLocation(binding.lyLocalMusic, Gravity.BOTTOM, 0, 0);
            popupAdapter.setDataList(tracks);
            dimBehind(popupWindow);

            // set localTracks as default play list
            popupTracks = tracks;
        }
    }

    private void playTracks(ArrayList<TrackMeta> list, int position) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        String MP3_PATH = "/sdcard/Music/Kate Voegele - Sun Will Rise.mp3";
        if (list != null && position < list.size()) {
            MP3_PATH = list.get(position).getUrl();
        }

        try {
            mediaPlayer.setDataSource(MP3_PATH);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.getDuration();
            binding.playStatusBar.setPlayer(mediaPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * onClick
     */
    private void onTracksItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 当 mListView 为 PullToRefreshListView 时，position从1开始，当添加了HeadView时 position从2开始
        initTrackList(popupTracks, position);
        if (popupTracks.equals(localTracks)) {
            ToastUtils.showShort(FileUtils.getLocalListPath(), "Send Local Playlist");
        } else if (popupTracks.equals(favoriteTracks)) {
            ToastUtils.showShort(FileUtils.getFavoriteListPath(), "Send Favorite Playlist");
        }
        showPopupWindow(null, null);
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


    private int getPopupWindowHeight() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return (int) (point.y * 0.6);
    }

    private void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }
}
