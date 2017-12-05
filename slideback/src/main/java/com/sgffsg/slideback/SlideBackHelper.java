package com.sgffsg.slideback;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

import com.sgffsg.slideback.listener.OnSlideCallBack;
import com.sgffsg.slideback.widget.SlideBackLayout;

/**
 * 滑动返回辅助类
 * Created by sgffsg on 17/11/2.
 */

public class SlideBackHelper {

    public static ViewGroup getDecorView(Activity activity) {
        return (ViewGroup) activity.getWindow().getDecorView();
    }

    public static Drawable getDecorViewDrawable(Activity activity) {
        return getDecorView(activity).getBackground();
    }

    public static View getContentView(Activity activity) {
        return getDecorView(activity).getChildAt(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void attach(final Activity curActivity, final ActivityLifeHelper helper, final SlideConfig config){
        final ViewGroup decorView = getDecorView(curActivity);
        final View contentView = decorView.getChildAt(0);
        decorView.removeViewAt(0);
        View content = contentView.findViewById(android.R.id.content);
        if (content.getBackground() == null) {
            content.setBackground(decorView.getBackground());
        }

        final View mPreviousContentView;
        final Activity mPreviousActivity=ActivityLifeHelper.getPreviousActivity();
        if(mPreviousActivity == null) {
            mPreviousContentView = null;
            return;
        }
        mPreviousContentView=getContentView(mPreviousActivity);
        Drawable preDecorViewDrawable = getDecorViewDrawable(mPreviousActivity);
        content = mPreviousContentView.findViewById(android.R.id.content);

        if (content!=null&&content.getBackground() == null) {
            content.setBackground(preDecorViewDrawable);
        }
        SlideBackLayout slideBackLayout=new SlideBackLayout(curActivity,contentView,mPreviousContentView,config, new OnSlideCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onClose(Boolean finishActivity) {
                if (mPreviousActivity!=null&&mPreviousContentView.getParent()!=getDecorView(mPreviousActivity)){
                    ((ViewGroup)mPreviousContentView.getParent()).removeView(mPreviousContentView);
                    getDecorView(mPreviousActivity).addView(mPreviousContentView,0);
                }

                if (finishActivity != null && finishActivity) {
                    contentView.setVisibility(View.INVISIBLE);
                    curActivity.finish();
                    curActivity.overridePendingTransition(0, R.anim.anim_out_none);
                    helper.postRemoveActivity(curActivity);
                } else if (finishActivity == null) {
                    helper.postRemoveActivity(curActivity);
                }
            }
        });
        decorView.addView(slideBackLayout,0);
    }

}
