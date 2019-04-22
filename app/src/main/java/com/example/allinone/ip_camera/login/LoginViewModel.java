package com.example.allinone.ip_camera.login;

import android.app.Application;

import com.example.allinone.data.login.LoginRepository;
import com.example.allinone.data.login.Platform;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<LoginRepository> {
    private final MutableLiveData<List<Platform>> mItems = new MutableLiveData<>();
    private final MutableLiveData<Platform> mPlatform = new MutableLiveData<>();
    private final MutableLiveData<String> mTitle = new MutableLiveData<>();
    private final MutableLiveData<String> mAccount = new MutableLiveData<>();
    private final MutableLiveData<String> mPassWord = new MutableLiveData<>();
    private List<Platform> array = null;

    public LoginViewModel(@NonNull Application application, LoginRepository model) {
        super(application, model);
        this.array = new ArrayList<>();
        array.add(new Platform("Jong", "123", "My Room", "123"));
        array.add(new Platform("Lin", "123", "My House", "123"));
        array.add(new Platform("Guangxi", "123", "My HOME", "123"));
        this.mTitle.setValue("Select Platform");
        this.mPlatform.setValue(new Platform());
    }


    public MutableLiveData<Platform> getPlatform() {
        return mPlatform;
    }

    public MutableLiveData<String> getAccount() {
        return mAccount;
    }

    public MutableLiveData<String> getPassWord() {
        return mPassWord;
    }

    public MutableLiveData<String> getTitle() {
        return mTitle;
    }

    public void selectPlatform() {
        this.mItems.setValue(this.array);
    }
}
