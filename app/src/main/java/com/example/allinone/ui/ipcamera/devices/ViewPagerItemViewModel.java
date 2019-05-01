package com.example.allinone.ui.ipcamera.devices;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by JongLim on 30/4/2019.
 */
public class ViewPagerItemViewModel extends ItemViewModel<ViewPagerViewModel> {
    private String title;

    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //点击之后将逻辑转到activity中处理
            viewModel.itemClickEvent.setValue(title);
        }
    });

    public ViewPagerItemViewModel(@NonNull ViewPagerViewModel viewModel, String title) {
        super(viewModel);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
