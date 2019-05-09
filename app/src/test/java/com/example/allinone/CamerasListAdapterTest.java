package com.example.allinone;

import android.app.Application;
import android.content.Context;

import com.example.allinone.entity.AreaEntity;
import com.example.allinone.entity.CameraEntity;
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
public class CamerasListAdapterTest {
    @Mock
    private Context mContext;

    @Mock
    private Application mApp;

    private List<AreaEntity> mAreas;

    private CameraListViewModel mViewModel;

    private CamerasListAdapter mAdapter;


    @Before
    public void runBeforeTestMethod() {
        MockitoAnnotations.initMocks(this);
        System.out.println("@Before - runBeforeTestMethod ");

        mViewModel = new CameraListViewModel(mApp);
        mViewModel.initList();
        System.out.println("Areas Size = " + mViewModel.areas.size());
        mAdapter = new CamerasListAdapter(mContext, mViewModel.areas);


        StringBuilder countStr = new StringBuilder();
        for (AreaEntity a : mViewModel.areas) {
            countStr.append(a.childSize()).append(", ");
        }
        System.out.println(countStr);
    }

    @Test
    public void getChildId() {
        long cid = mAdapter.getChildId(1, 3);
        System.out.println("@Test - getChildId\n" + cid);
    }

    @Test
    public void searchCamera() {
        String keyword = "1-1";
        List<AreaEntity> as = mViewModel.onFilterCamera(keyword);
        StringBuilder asName = new StringBuilder();
        StringBuilder csName = new StringBuilder();
        for (AreaEntity a : as) {
            asName.append(a.getName()).append(", ");
            for (CameraEntity c : a.getCameras()){
                csName.append(c.getName()).append(", ");
            }
        }
        System.out.println("\n" + asName);
        System.out.println("\n" + csName);
    }
}
