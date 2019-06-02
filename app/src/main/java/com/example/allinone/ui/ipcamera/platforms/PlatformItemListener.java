package com.example.allinone.ui.ipcamera.platforms;

import com.example.allinone.entity.PlatformEntity;

/**
 * Created by Jong Lim on 20/5/19.
 */
public interface PlatformItemListener {

    // normally don't binding this to item's layout
    // because this item layout also use in spinner for login activity
    void onItemClick(PlatformEntity platformEntity);

    
    void onEditClick(PlatformEntity platformEntity);

    void onDeleteClick(PlatformEntity platformEntity);
}
