package com.example.allinone.ipcamera.platform;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.viewpager.widget.PagerAdapter;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Created by Jong Lim on 30/4/19.
 */
public class PlatformsViewModel extends BaseViewModel {
    private PagerAdapter adapter;

    public PlatformsViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    @Bindable
    public PagerAdapter getPagerAdapter() {
        return adapter;
    }

    public void setPagerAdapter(PagerAdapter adapter){
        this.adapter = adapter;
    }
}
