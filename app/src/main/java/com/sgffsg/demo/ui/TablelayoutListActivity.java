package com.sgffsg.demo.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import com.sgffsg.demo.R;
import com.sgffsg.demo.base.BaseActivity;
import com.sgffsg.demo.fragment.PullListFragment;

import java.util.ArrayList;

/**
 * Created by sgffsg on 16/9/26.
 */

public class TablelayoutListActivity extends BaseActivity {

    TabLayout tab;
    ViewPager viewPager;

    ArrayList<Fragment> list;


    @Override
    protected int getContentId() {
        return R.layout.activity_tablelayout;
    }

    @Override
    protected void initView() {
        initViewPagerAndTabs();

    }

    @Override
    protected void initData() {

    }

    private void initViewPagerAndTabs() {
        list=new ArrayList<>();
        list.add(PullListFragment.createInstance(40));
        list.add(PullListFragment.createInstance(32));
        TabsAdapter adapter=new TabsAdapter(getSupportFragmentManager(),list);

        viewPager= (ViewPager) findViewById(R.id.viewPager);
        tab= (TabLayout) findViewById(R.id.tab);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        tab.setupWithViewPager(viewPager);
    }

    /**
     * tab的PagerAdapter
     */
    class TabsAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;

        public TabsAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        public Fragment getChildAd(int pos) {
            return fragments.get(pos);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tab1";
                case 1:
                    return "Tab2";
            }
            return "";
        }
    }


}
