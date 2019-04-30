package com.example.allinone.data.source.http;

import com.example.allinone.entity.AreaEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Jong Lim on 23/4/19.
 */
public interface DemoApiService {

    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<AreaEntity>> refreshDeviceList();

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResponse<AreaEntity>> loadMoreDevices(@Field("catalog") String catalog);

}

