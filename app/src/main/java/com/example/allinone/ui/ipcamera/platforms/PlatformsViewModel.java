package com.example.allinone.ui.ipcamera.platforms;

import android.app.Application;
import android.view.View;

import com.example.allinone.base.ToolbarViewModel;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class PlatformsViewModel extends ToolbarViewModel<PlatformsNavigator> {

    public BindingCommand onEditIconClick = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer position) {
            getNavigator().openEditPlatformActivity(position);
        }
    });
    public BindingCommand onItemClick = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer position) {
            getNavigator().onSelectItem(position);
        }
    });

    public PlatformsViewModel(@NonNull Application application) {
        super(application);
        setTitleText("Platform List");
        setRightText("Add");
        setRightTextVisible(View.VISIBLE);
    }
}
