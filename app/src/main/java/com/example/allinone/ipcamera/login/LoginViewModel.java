package com.example.allinone.ipcamera.login;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import com.example.allinone.data.login.LoginRepository;
import com.example.allinone.data.login.Platform;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginViewModel extends BaseViewModel<LoginRepository> {
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> account = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final UIChangeObservable uc = new UIChangeObservable(); //封装一个界面发生改变的观察者

    private final ObservableInt clearBtnVisibility = new ObservableInt();

    public BindingCommand onSelectPlatformClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.platform.setValue(null);
        }
    });

    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });

    public BindingCommand onShowPwdSwitchOn = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean on) {
            uc.pwdSwitchEvent.setValue(on);
        }
    });

    public BindingCommand onRememberPwdChange = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean check) {
        }
    });

    public BindingCommand onForgetPwdClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
        }
    });

    public BindingCommand onLoginClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    public LoginViewModel(@NonNull Application application, LoginRepository model) {
        super(application, model);
        uc.platform.setValue(new Platform());
    }

    /**
     * 网络模拟一个登陆操作
     **/
    private void login() {
        if (TextUtils.isEmpty(title.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }

        //RaJava模拟登录
        addSubscribe(model.login().compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        dismissDialog();
                        //保存账号密码
                        model.saveUserName(title.get());
                        model.savePassword(password.get());
                        // tartActivity(DemoActivity.class);
                        //关闭页面
                        finish();
                    }
                }));

    }

    public void onSelectPlatform(@NonNull Platform plat) {
        title.set(plat.getTitle());
        account.set(plat.getAccount());
        password.set(plat.getPassword());
    }

    public class UIChangeObservable {
        //密码开关观察者
        SingleLiveEvent<Boolean> pwdSwitchEvent = new SingleLiveEvent<>();
        SingleLiveEvent<Platform> platform = new SingleLiveEvent<>();
    }

}