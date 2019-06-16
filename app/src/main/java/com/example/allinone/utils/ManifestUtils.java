package com.example.allinone.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

/**
 * Created by JongLim on 6/2/2019.
 */
public class ManifestUtils {

    /**
     * 获取 AndroidManifest.xml 里面各种 meta-data 的方法
     * meta-data 之所在位置不同有不懂得调用方法
     */
    public static String getAppMetaData(Activity activity, String name) {
        String msg = null;
        try {
            ApplicationInfo info = activity.getPackageManager().getApplicationInfo(activity.getPackageName(),
                    PackageManager.GET_META_DATA);
            msg = info.metaData.getString(name);
            //System.out.println(name + ":" + msg);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getActivityMetaData(Activity activity, String name) {
        String msg = null;
        try {
            ActivityInfo info = activity.getPackageManager().getActivityInfo(
                    activity.getComponentName(), PackageManager.GET_META_DATA);
            msg = info.metaData.getString(name);
            //System.out.println(name + ":" + msg);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getServiveMetaData(Activity activity, Class<?> svrcls, String name) {
        String msg = null;
        try {
            ComponentName cn = new ComponentName(activity, svrcls);
            ServiceInfo info = activity.getPackageManager().getServiceInfo(cn, PackageManager.GET_META_DATA);
            msg = info.metaData.getString(name);
            //System.out.println(name + ":" + msg);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getRecevierMetaData(Activity activity, Class<?> reccls, String name) {
        String msg = null;
        try {
            ComponentName cn = new ComponentName(activity, reccls);
            ActivityInfo info = activity.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
            msg = info.metaData.getString(name);
            //System.out.println(name + ":" + msg);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
