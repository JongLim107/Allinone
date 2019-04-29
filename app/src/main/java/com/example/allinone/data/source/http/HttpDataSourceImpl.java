package com.example.allinone.data.source.http;

import com.example.allinone.data.source.HttpDataSource;
import com.example.allinone.entity.LoginEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * Created by Jong Lim on 23/4/19.
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private DemoApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(DemoApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(DemoApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Object> login() {
        return Observable.just(new Object()).delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<LoginEntity> loadMore() {
        return null;
    }

    @Override
    public Observable<BaseResponse<LoginEntity>> demoPost(String catalog) {
        return apiService.demoPost(catalog);
    }
}
