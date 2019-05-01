package me.goldze.mvvmhabit.base;

import android.app.Application;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2017/6/15.
 * Modify: JongLim
 *      移除不必要的事件监听，并且添加 INavigator,
 *      然后把Navigate操作移到（同层级的）Activity和Fragment里面去实现.
 */
public class BaseViewModel<M extends BaseModel, N> extends AndroidViewModel implements IBaseViewModel {
    protected M model;
    private WeakReference<LifecycleProvider> lifecycle;
    private WeakReference<N> mNavigator;
    private UIChangeLiveData uc;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        model = null;
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
    }

    protected void addSubscribe(Disposable disposable) {
        if (model == null) {
            throw new NullPointerException("Create ViewModel with ViewModelFactory with Model");
        }
        model.addSubscribe(disposable);
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle.get();
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void registerRxBus() {
    }

    @Override
    public void removeRxBus() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
    }

    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        uc.showDialogEvent.postValue(title);
    }

    public void dismissDialog() {
        uc.dismissDialogEvent.call();
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent showDialogEvent;
        private SingleLiveEvent dismissDialogEvent;

        SingleLiveEvent getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        SingleLiveEvent getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        private SingleLiveEvent createLiveData(SingleLiveEvent liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent();
            }
            return liveData;
        }
    }

}
