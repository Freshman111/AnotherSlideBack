package com.sgffsg.demo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.sgffsg.demo.R;


/**
 * TitleBar的工具类
 */
public class TitleBarUtils {

    @TargetApi(19)
    public static void setTranslucentStatus(Window window) {
        Window win = window;
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            win.setStatusBarColor(Color.TRANSPARENT);
        }
        win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        win.setAttributes(winParams);
    }

    /**
     * 设置TitleBarPadding
     *
     * @param context
     * @param mTintManager
     */
    public static void setTitleBarFromPadding(Activity context, SystemBarTintManager mTintManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = context.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int statusBarHeight = mTintManager.getConfig().getStatusBarHeight();
            //改变titlebar的高度
//            ViewGroup.LayoutParams lp = view.getLayoutParams();
//            lp.height += statusBarHeight;
//            view.setLayoutParams(lp);
            //设置paddingtop
//            view.setPaddingRelative(0, statusBarHeight, 0, 0);
            View bar = new View(context);
            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            bar.setLayoutParams(layoutParams);
            bar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            ViewGroup contentView = ((ViewGroup) window.getDecorView()).findViewById(android.R.id.content);
            if (contentView!=null) {
                contentView.addView(bar);
            }
        }
    }

    /**
     * 设置TitleBarPadding
     *
     * @param titleBar
     * @param topPadding
     */
    public static void setTitleBarFromPadding(View titleBar, int topPadding) {

        if (titleBar == null)
            return;
        titleBar.setPadding(0, topPadding, 0, 0);
        titleBar.setLayoutParams(titleBar.getLayoutParams());
    }
}
