package com.example.allinone.data.source.http;

import com.example.allinone.data.source.IHttpDataSource;
import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Jong Lim on 23/4/19.
 */
public class HttpDataSourceImpl implements IHttpDataSource {
    private volatile static HttpDataSourceImpl INSTANCE = null;
    private DemoApiService apiService;

    private HttpDataSourceImpl(DemoApiService apiService) {
        this.apiService = apiService;
    }

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

    @Override
    public Observable<List<AreaEntity>> login() {
        return Observable.just(mockAreaList())
                .delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<List<AreaEntity>> refreshDeviceList() {
        return Observable.just(mockAreaList())
                .delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<List<AreaEntity>> loadMoreDevices() {
        return Observable.just(mockAreaList())
                .delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    private List<AreaEntity> mockAreaList() {
        List<AreaEntity> areas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            int unit = i % 4;
            List<CameraEntity> cameras = new ArrayList<>();
            for (int j = 0; j < unit * unit; j++) {
                cameras.add(new CameraEntity("CAMERA_ID_" + j, "CAMERA_NAME " + j, unit > 1));
            }

            areas.add(new AreaEntity("AREA_ID_" + i, "AREA_NAME " + i, cameras));
        }

        return areas;
    }
}
