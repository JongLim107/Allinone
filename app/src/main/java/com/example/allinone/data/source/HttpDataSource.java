package com.example.allinone.data.source;

import com.example.allinone.entity.LoginEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * Created by Jong Lim on 19/4/19.
 */
public interface HttpDataSource {

    //模拟登录
    Observable<Object> login();

    //模拟上拉加载
    Observable<LoginEntity> loadMore();

    Observable<BaseResponse<LoginEntity>> demoPost(String catalog);

}
