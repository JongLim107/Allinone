package com.example.allinone.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by JongLim on 6/2/2019.
 */
public class TimeUtils {

    private static SimpleDateFormat formatterHHmmss = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat formatterMMSS = new SimpleDateFormat("mm:ss");

    public static String formatHHmmSS(int paramInt) {
        formatterHHmmss.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatterHHmmss.format(paramInt);
    }

    /**
     * 格式化时间为 Human-Readable
     */
    public static String formatMediaTime(int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        int hours = minutes / 60;
        minutes %= 60;
        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static int fromHHmmSS(String paramString) {
        formatterHHmmss.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        try {
            long l = formatterHHmmss.parse(paramString).getTime() / 1000L;
            return (int) l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
