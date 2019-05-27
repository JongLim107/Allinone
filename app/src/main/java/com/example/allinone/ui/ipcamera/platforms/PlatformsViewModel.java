package com.example.allinone.ui.ipcamera.platforms;

import android.app.Application;
import android.view.View;

import com.example.allinone.base.ToolbarViewModel;
import com.example.allinone.entity.PlatformEntity;

import androidx.annotation.NonNull;
import io.objectbox.Box;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class PlatformsViewModel extends ToolbarViewModel<PlatformsNavigator> implements PlatformItemListener {

    public BindingCommand onItemClick = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer position) {
            getNavigator().onSelectItem(position);
        }
    });

    private Box<PlatformEntity> platforms;
    private PlatformsAdapter adapter;

    public PlatformsViewModel(@NonNull Application application) {
        super(application);
        setTitleText("Platform List");
        setRightText("Add");
        setRightTextVisible(View.VISIBLE);
    }

    @Override
    public void onItemClick(PlatformEntity item) {
    }

    @Override
    public void onEditClick(PlatformEntity item) {
        getNavigator().openEditPlatformActivity(item);
    }

    @Override
    public void onDeleteClick(PlatformEntity item) {
        this.platforms.remove(item);
        this.adapter.remove(item);
    }

    public void setPlatforms(Box<PlatformEntity> platforms) {
        this.platforms = platforms;
    }

    public void setAdapter(PlatformsAdapter adapter) {
        this.adapter = adapter;
    }
}
