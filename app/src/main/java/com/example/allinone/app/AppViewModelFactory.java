package com.example.allinone.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.allinone.data.login.LoginRepository;
import com.example.allinone.ip_camera.login.LoginViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Jong Lim on 21/4/19.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final LoginRepository mRepository;

    private AppViewModelFactory(Application application, LoginRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    Context context = application.getApplicationContext();
                    LoginRepository repository = Injection.provideLoginRepository(context);
                    INSTANCE = new AppViewModelFactory(application, repository);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}
