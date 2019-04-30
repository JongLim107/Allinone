package com.example.allinone.data.source;

import com.example.allinone.entity.AreaEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Jong Lim on 19/4/19.
 */
public interface IHttpDataSource {

    Observable<List<AreaEntity>> login();

    Observable<List<AreaEntity>> refreshDeviceList();

    Observable<List<AreaEntity>> loadMoreDevices();

}
