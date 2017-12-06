package com.sgffsg.demo.ui;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import com.sgffsg.demo.R;
import com.sgffsg.demo.base.BaseActivity;
import com.sgffsg.demo.view.LoopBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgffsg on 16/6/16.
 */
public class LoopViewPagerActivity extends BaseActivity {

    LoopBanner loopBanner;

    private List<String> urls;
    private Handler handler;


    @Override
    protected int getContentId() {
        return R.layout.activity_auto_scroll_viewpager;
    }

    @Override
    protected void initView() {
        loopBanner= (LoopBanner) findViewById(R.id.loopbanner);
        loopBanner.setOnBannerItemClickListener(new LoopBanner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(LoopViewPagerActivity.this,"faifanf",Toast.LENGTH_SHORT).show();
            }
        });
        urls = new ArrayList<>();
        urls.add("http://p18.qhimg.com/d/_open360/20140506/1342.jpg");
        urls.add("http://p15.qhimg.com/bdr/__85/d/_open360/fengjing0409/27.jpg");
        urls.add("http://p19.qhimg.com/t0143e79e20b10eb531.jpg");
        urls.add("http://p18.qhimg.com/bdr/__85/d/_open360/fengjing0417/8.jpg");
        urls.add("http://p15.qhimg.com/d/_open360/xqx0606/18.jpg");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                loopBanner.setViewUrls(urls);

            }
        };
        handler.sendEmptyMessageDelayed(1,2000);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
