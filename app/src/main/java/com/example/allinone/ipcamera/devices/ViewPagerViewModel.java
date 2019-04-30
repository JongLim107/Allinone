package com.example.allinone.ipcamera.devices;

import android.app.Application;

import com.example.allinone.BR;
import com.example.allinone.R;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by Jong Lim on 30/4/19.
 */

public class ViewPagerViewModel extends BaseViewModel {

    private final String[] TITLES = new String[]{
            "Camera", "Snapshot Record", "Video Record",
            "Remote Alarm", "Switch In", "Switch Out"
    };

    public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent<>();
    public BindingViewPagerAdapter adapter = new BindingViewPagerAdapter();

    //给ViewPager添加ObservableList
    public ObservableList<ViewPagerItemViewModel> items = new ObservableArrayList<>();

    //给ViewPager添加ItemBinding
    public ItemBinding<ViewPagerItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_viewpager);

    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, ViewPagerItemViewModel item) {
            return TITLES[position];
        }
    };

    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {
//            ToastUtils.showShort("ViewPager切换：" + index);
        }
    });

    public ViewPagerViewModel(@NonNull Application application) {
        super(application);
        items.add(new ViewPagerItemViewModel(this, "Camera 页面"));
        items.add(new ViewPagerItemViewModel(this, "Snapshot 页面"));
        items.add(new ViewPagerItemViewModel(this, "Video 页面"));
        items.add(new ViewPagerItemViewModel(this, "Alarm 页面"));
        items.add(new ViewPagerItemViewModel(this, "Switch In 页面"));
        items.add(new ViewPagerItemViewModel(this, "Switch Out 页面"));
    }

}
