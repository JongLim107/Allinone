package com.example.allinone.ui.ipcamera.devices.cameras;

import android.app.Application;

import com.example.allinone.ui.ipcamera.devices.DevicesNavigator;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 4/5/19.
 */
public class CameraListViewModel extends BaseViewModel<BaseModel, DevicesNavigator> {

    //    public final ObservableField<String> title = new ObservableField<>();

    private final int MAX_OPEN_COUNT = 16;

    private int[] selectedCameras;
    public BindingCommand onStartPlay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (selectedCameras.length < 1) {
                ToastUtils.showShort("Please choosing camera first.");
            } else {
                getNavigator().openCameraActivity();
                //2015-4-28 Login Multiple Camera
                //                Intent intent = new Intent(getActivity(), CameraActivity.class);
                //                intent.putIntegerArrayListExtra("SELECTINDEX", selectedCameras);
                //                intent.putExtra("GUSTATUSARRAY", mCamStatusArray);
                //                intent.putExtra("GUIDARRAY", mArrayCamId);
                //                intent.putExtra("GUNAMEARRAY", mArrayCamName);
                //                startActivity(intent);
            }
        }
    });

    public CameraListViewModel(@NonNull Application application) {
        super(application);

    }

    //    @Override
    //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //        JLLog.LOGI(TAG, "Have click the searchlist iterm!");
    //        String str = parent.getItemAtPosition(position)
    //                .toString();
    //        int indexNil = str.indexOf(' ');
    //        String subStr = str.substring(0, indexNil);
    //        Integer iCameraIndex = Integer.valueOf(subStr);
    //        if (mCamStatusArray[iCameraIndex] != 0x1002) {
    //            TextView tv = (TextView) view;
    //            if (selectedCameras.contains(iCameraIndex)) {
    //                tv.setTextColor(Color.BLACK);
    //                cameras.get(iCameraIndex)
    //                        .setChecked(false);
    //                selectedCameras.remove(iCameraIndex);
    //            } else {
    //                tv.setTextColor(Color.GREEN);
    //                cameras.get(iCameraIndex)
    //                        .setChecked(true);
    //                selectedCameras.add(iCameraIndex);
    //            }
    //
    //            if (selectedCameras.size() > MAX_OPEN_COUNT)
    //                JLLog.showToast(getActivity(), getResources().getString(R.string.msg_maxselect));
    //            mStartPrv.setText(getResources().getString(R.string.btn_play_cam) + "(" + selectedCameras.size() + ")");
    //        }
    //    }

}
