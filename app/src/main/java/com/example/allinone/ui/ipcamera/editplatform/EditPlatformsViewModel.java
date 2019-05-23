package com.example.allinone.ui.ipcamera.editplatform;

import android.app.Application;
import android.view.View;

import com.example.allinone.base.ToolbarViewModel;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class EditPlatformsViewModel extends ToolbarViewModel {

    public final EditPlatformsViewModel.UIChangeObservable uiObs = new EditPlatformsViewModel.UIChangeObservable();

    public BindingCommand onShowPwdSwitchOn = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean on) {
            uiObs.pwdSwitchEvent.setValue(on);
        }
    });

    public EditPlatformsViewModel(@NonNull Application application) {
        super(application);
        setTitleText("Add/Edit Platform");
        setRightText("Save");
        setRightTextVisible(View.VISIBLE);
    }

    public void onSaveInnerClick(PlatformSaveType type) {
        if (PlatformSaveType.INNER_CARD == type) {
            uiObs.saveInnerEvent.setValue(true);
        } else {
            uiObs.saveInnerEvent.setValue(false);
        }
    }

    public class UIChangeObservable {
        // must be SingleLiveEvent because we need to observe this in Activity
        SingleLiveEvent<Boolean> pwdSwitchEvent = new SingleLiveEvent<>();
        SingleLiveEvent<Boolean> saveInnerEvent = new SingleLiveEvent<>();
    }
}
