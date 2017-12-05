package com.sgffsg.demo;

import android.content.Intent;
import android.view.View;

import com.sgffsg.demo.base.BaseActivity;
import com.sgffsg.demo.ui.BasicJumpActivity;
import com.sgffsg.demo.ui.FullImageActivity;
import com.sgffsg.demo.ui.LoopViewPagerActivity;
import com.sgffsg.demo.ui.TablelayoutListActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_basic).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
        findViewById(R.id.btn_banner).setOnClickListener(this);
        findViewById(R.id.btn_full_image).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isSupportSlideBack() {
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.btn_basic:
                intent=new Intent(MainActivity.this, BasicJumpActivity.class);
                break;
            case R.id.btn_list:
                intent=new Intent(MainActivity.this, TablelayoutListActivity.class);
                break;
            case R.id.btn_banner:
                intent=new Intent(MainActivity.this, LoopViewPagerActivity.class);
                break;
            case R.id.btn_full_image:
                intent=new Intent(MainActivity.this, FullImageActivity.class);
                break;
        }
        startActivity(intent);
    }
}
