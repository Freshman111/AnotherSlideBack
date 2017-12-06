package com.sgffsg.demo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class LoopPagerAdapter extends PagerAdapter {

    private List<View> viewList;
    private int count = Integer.MAX_VALUE;


    public LoopPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int size = viewList.size();
        if (size > 0) {
            //position%size取余表示position会在0~size之间循环
            View view = viewList.get(position % size);
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }

        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


}
