package com.example.allinone.utils;

import android.content.Context;
import android.text.Selection;
import android.text.Spannable;
import android.util.TypedValue;

/**
 * Created by JongLim on 6/2/2019.
 */
public class OtherUtils {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 切换后将EditText光标置于末尾
     */
    public static void moveCursorToEnd(CharSequence charSequence) {
        if (charSequence != null) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

}
