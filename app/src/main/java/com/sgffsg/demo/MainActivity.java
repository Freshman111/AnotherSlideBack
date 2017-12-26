package com.sgffsg.demo;

import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;

import com.sgffsg.demo.base.BaseActivity;
import com.sgffsg.demo.base.MyApplication;
import com.sgffsg.demo.ui.BasicJumpActivity;
import com.sgffsg.demo.ui.FullImageActivity;
import com.sgffsg.demo.ui.LoopViewPagerActivity;
import com.sgffsg.demo.ui.TablelayoutListActivity;
import com.sgffsg.slideback.SlideConfig;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    RadioGroup radioGroup;

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
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_nomal:
                        MyApplication.setShadowType(SlideConfig.SHADOW_STYLE_NOMAL);
                        break;
                    case R.id.rb_wechat:
                        MyApplication.setShadowType(SlideConfig.SHADOW_STYLE_WECHAT);
                        break;
                    case R.id.rb_sb:
                        MyApplication.setShadowType(SlideConfig.SHADOW_STYLE_SNOW_BALL);
                        break;
                    case R.id.rb_byte_jump:
                        MyApplication.setShadowType(SlideConfig.SHADOW_STYLE_BYTE_JUMP);
                        break;
                }
            }
        });
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
