package com.example.junze.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.junze.myapplication.*;
import com.example.junze.myapplication.fragment.HotTopicFragment;
import com.example.junze.myapplication.fragment.LatestFragment;
import com.example.junze.myapplication.fragment.NodeFragment;
import com.example.junze.myapplication.fragment.PostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junze on 16-05-04.
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Fragment> fragments;
    private MainFragmentAdapter mAdapter;
    private Toolbar mToolbarbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbarbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarbar.setTitle(R.string.app_name);
        List<String> titles = new ArrayList<>();
        titles.add("热门");
        titles.add("最新");
        titles.add("节点");
        HotTopicFragment hotTopicFragment = new HotTopicFragment();
        LatestFragment latestFragment = new LatestFragment();
        NodeFragment nodeFragment = new NodeFragment();
        fragments = new ArrayList<>();
        fragments.add(hotTopicFragment);
        fragments.add(latestFragment);
        fragments.add(nodeFragment);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mAdapter.setTitles(titles);
        mViewPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(mViewPager);
    }
    private class MainFragmentAdapter extends FragmentPagerAdapter {
        private List<String> titles;

        public void setTitles(List<String> titles) {
            this.titles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
