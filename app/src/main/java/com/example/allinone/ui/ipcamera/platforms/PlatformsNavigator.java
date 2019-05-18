package com.example.allinone.ui.ipcamera.platforms;

import me.goldze.mvvmhabit.base.IToolbarNavigator;

/**
 * Created by Jong Lim on 18/5/19.
 */
public interface PlatformsNavigator extends IToolbarNavigator {
    void onSelectItem(Integer pos);

    void openEditPlatformActivity(int position);
}
