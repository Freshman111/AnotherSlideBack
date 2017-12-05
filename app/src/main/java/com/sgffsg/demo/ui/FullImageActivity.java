package com.sgffsg.demo.ui;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.sgffsg.demo.R;
import com.sgffsg.demo.base.BaseActivity;


/**
 *
 * Created by sgffsg on 17/4/25.
 */

public class FullImageActivity extends BaseActivity {

    @Override
    protected int getContentId() {
        return R.layout.activity_full_image;
    }

    @Override
    protected void initView() {
        initTitleBar();
    }

    @Override
    protected void initData() {

    }

    private void initTitleBar() {
        Window w = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                w.setStatusBarColor(Color.TRANSPARENT);
            }else{
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    @Override
    protected void setTitleBarPadding(boolean isDarkMode) {
    }

}
