package com.example.allinone.app;

import android.content.Context;

import com.example.allinone.entity.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class ObjectBox {
        private static BoxStore boxStore;

        static void init(Context context) {
            boxStore = MyObjectBox.builder()
                    .androidContext(context.getApplicationContext())
                    .build();
        }

        public static BoxStore get() { return boxStore; }
}
