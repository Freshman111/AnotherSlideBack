package com.sgffsg.demo.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.sgffsg.slideback.ActivityLifeHelper;
import com.sgffsg.slideback.SlideBackHelper;
import com.sgffsg.slideback.SlideConfig;
import com.sgffsg.demo.utils.SystemBarTintManager;
import com.sgffsg.demo.utils.TitleBarUtils;
import com.sgffsg.slideback.widget.ShadowView;

/**
 * BaseActivity
 * Created by sgffsg on 16/5/18.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    private int layoutId;//布局id
    protected Toolbar mToolbar;
    //透明状态栏
    protected SystemBarTintManager mTintManager;
    //是否支持透明状态栏
    protected boolean isSystemBarTint;
    //是否未miui6及以上系统
    protected boolean isMiuiV6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置沉浸式状态栏
        TitleBarUtils.setTranslucentStatus(getWindow());
        mTintManager = new SystemBarTintManager(this);
        isMiuiV6 = mTintManager.isSystemBarTint();
        isSystemBarTint = mTintManager.ismStatusBarAvailable();
        setContentView(getContentId());
        if (isSupportSlideBack()){
            SlideBackHelper.attach(this, ActivityLifeHelper.build(),
                    new SlideConfig.Builder()
                            .slideOutPercent(0.48f)
                            .shadowStyle(MyApplication.getShadowType())
                            .create());
        }
        setTitleBarPadding(false);
        initToolbar();
        initView();
        initData();
    }

    protected abstract int getContentId();

    protected abstract void initView();

    protected abstract void initData();

    protected boolean isSupportSlideBack(){
        return true;
    }

    public void onCreated(){

    }

    protected void initToolbar(){
        if (mToolbar!=null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTransparentForWindow(this);
        }
    }

    /**
     * 设置透明
     */
    private static void setTransparentForWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置TitleBarPadding并且设置状态栏字体颜色
     *
     * @param isDarkMode 是否为DarkMode
     */
    protected void setTitleBarPadding(boolean isDarkMode) {
        TitleBarUtils.setTitleBarFromPadding(this, mTintManager);
        //设置透明状态来为白色字体
        mTintManager.setStatusBarDarkMode(isDarkMode, this);
    }

    /**
     * 显示短的吐司
     *
     * @param content
     */
    protected void showShortToast(String content) {
        Toast.makeText(this, "" + content, Toast.LENGTH_SHORT).show();
    }

}
