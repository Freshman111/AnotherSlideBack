package com.sgffsg.demo.base;

import android.app.Application;
import android.content.Context;

import com.sgffsg.slideback.ActivityLifeHelper;

/**
 * Application
 * Created by sgffsg on 16/5/31.
 */
public class MyApplication extends Application{
    private static MyApplication sInstance;

    private static int shadowType=0;

    public static Context getAppContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        registerActivityLifecycleCallbacks(ActivityLifeHelper.build());
    }

    public static int getShadowType() {
        return shadowType;
    }

    public static void setShadowType(int shadowType) {
        MyApplication.shadowType = shadowType;
    }
}
