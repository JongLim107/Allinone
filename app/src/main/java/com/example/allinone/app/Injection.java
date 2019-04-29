package com.example.allinone.app;


import android.content.Context;

import com.example.allinone.data.login.LoginRepository;
import com.example.allinone.data.source.HttpDataSource;
import com.example.allinone.data.source.LocalDataSource;
import com.example.allinone.data.source.http.DemoApiService;
import com.example.allinone.data.source.http.HttpDataSourceImpl;
import com.example.allinone.data.source.local.LocalDataSourceImpl;
import com.example.allinone.utils.RetrofitClient;

import androidx.annotation.NonNull;

/**
 * Created by Jong Lim on 21/4/19.
 */
public class Injection {

    public static LoginRepository provideLoginRepository(@NonNull Context context) {

        //网络API服务
        DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return LoginRepository.getInstance(httpDataSource, localDataSource);
    }
}
