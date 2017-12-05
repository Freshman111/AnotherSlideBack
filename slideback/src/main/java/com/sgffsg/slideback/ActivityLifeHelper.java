package com.sgffsg.slideback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * Activity生命周期帮助类
 * Created by sgffsg on 17/11/2.
 */

public class ActivityLifeHelper implements Application.ActivityLifecycleCallbacks{

    private static ActivityLifeHelper singleton;
    private static final Object lockObj = new Object();
    private static Stack<Activity> mActivityStack;

    private ActivityLifeHelper(){
        mActivityStack=new Stack<>();
    }

    public static ActivityLifeHelper build() {
        synchronized (lockObj) {
            if (singleton == null) {
                singleton = new ActivityLifeHelper();
            }
            return singleton;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    /**
     * 强制删掉activity，用于用户快速滑动页面的时候，因为页面还没来得及destroy导致的问题
     *
     * @param activity 删掉的activity
     */
    void postRemoveActivity(Activity activity) {
        if (mActivityStack != null) {
            mActivityStack.remove(activity);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (mActivityStack!=null){
            if(mActivityStack.contains(activity)) {
                mActivityStack.remove(activity);
            }
            if(mActivityStack.size() == 0) {
                mActivityStack = null;
            }
        }
    }

    /**
     * 获取集合中上一个Activity
     * @return 上一个Activity
     */
    public static Activity getPreviousActivity(){
        if (mActivityStack==null){
            return null;
        }
        int count = mActivityStack.size();
        if (count < 2) {
            return null;
        }
        return mActivityStack.get(count - 2);
    }
}
