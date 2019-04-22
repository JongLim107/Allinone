package com.example.allinone.data.login;

import com.example.allinone.data.source.HttpDataSource;
import com.example.allinone.data.source.LocalDataSource;
import com.example.allinone.entity.LoginEntity;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * Created by Jong Lim on 19/4/19.
 */
public class LoginRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static LoginRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;
    private final LocalDataSource mLocalDataSource;

    private LoginRepository(@NonNull HttpDataSource httpDataSource, @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static LoginRepository getInstance(HttpDataSource httpDataSource, LocalDataSource localDataSource) {
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
    public Observable<Object> login() {
        return mHttpDataSource.login();
    }

    @Override
    public Observable<LoginEntity> loadMore() {
        return null;
    }

    @Override
    public Observable<BaseResponse<LoginEntity>> demoPost(String catalog) {
        return null;
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
