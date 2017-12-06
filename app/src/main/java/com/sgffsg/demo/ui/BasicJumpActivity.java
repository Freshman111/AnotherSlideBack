package com.sgffsg.demo.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sgffsg.demo.R;
import com.sgffsg.demo.base.BaseActivity;

import java.util.Random;

/**
 * Created by sgffsg on 17/12/5.
 */

public class BasicJumpActivity extends BaseActivity{
    int[] colors=new int[]{Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN,Color.WHITE,Color.DKGRAY,Color.CYAN,Color.MAGENTA};

    @Override
    protected int getContentId() {
        return R.layout.activity_basic_jump;
    }

    @Override
    protected void initView() {
        RelativeLayout layout= (RelativeLayout) findViewById(R.id.root_layout);
        int n= new Random().nextInt(7);
        layout.setBackgroundColor(colors[n]);
        Button btn_jump_next= (Button) findViewById(R.id.btn_jump_next);
        btn_jump_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BasicJumpActivity.this,BasicJumpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
