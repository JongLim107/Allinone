package com.example.allinone;

import android.app.Application;
import android.content.Context;

import com.example.allinone.entity.AreaEntity;
import com.example.allinone.ui.ipcamera.devices.cameras.CameraListViewModel;
import com.example.allinone.ui.ipcamera.devices.cameras.CamerasListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

/**
 * Created by Jong Lim on 8/5/19.
 */
public class DevicesListAdapterTest {
    @Mock
    private CamerasListAdapter adapter;

    @Mock
    private Context context;

    @Mock
    private Application app;

    private List<AreaEntity> AREAS;

    @Before
    public void runBeforeTestMethod() {
        MockitoAnnotations.initMocks(this);
        CameraListViewModel viewmodel = new CameraListViewModel(app);
        viewmodel.initList();
        AREAS = viewmodel.areas;
        adapter = new CamerasListAdapter(context, AREAS);
        System.out.println("@Before - runBeforeTestMethod ");
        System.out.println("Areas Size = " + AREAS.size());
        StringBuilder countStr = new StringBuilder();
        for (AreaEntity a : AREAS) {
            countStr.append(a.childSize())
                    .append(", ");
        }
        System.out.println(countStr);
    }

    @Test
    public void getChildId() {
        long cid = adapter.getChildId(1, 3);
        System.out.println("@Test - getChildId = " + cid);
    }
}
