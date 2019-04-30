package com.example.allinone.data.login;

import com.example.allinone.data.source.IHttpDataSource;
import com.example.allinone.data.source.ILocalDataSource;
import com.example.allinone.entity.AreaEntity;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;

/**
 * Created by Jong Lim on 19/4/19.
 */
public class LoginRepository extends BaseModel implements IHttpDataSource, ILocalDataSource {
    private volatile static LoginRepository INSTANCE = null;
    private final IHttpDataSource mHttpDataSource;
    private final ILocalDataSource mLocalDataSource;

    private LoginRepository(@NonNull IHttpDataSource httpDataSource, @NonNull ILocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static LoginRepository getInstance(IHttpDataSource httpDataSource, ILocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (LoginRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<AreaEntity>> login() {
        return mHttpDataSource.login();
    }

    @Override
    public Observable<List<AreaEntity>> refreshDeviceList() {
        return mHttpDataSource.refreshDeviceList();
    }

    @Override
    public Observable<List<AreaEntity>> loadMoreDevices() {
        return mHttpDataSource.loadMoreDevices();
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
