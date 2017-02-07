package com.fxly.monkeytest.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.fxly.monkeytest.R;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class SettingsFragment extends AppCompatActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabsPagerAdapter mTabsPagerAdapter;

    private String[] tabs ={"Parameters","Email","TEST APP List"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_fragment);

        //获取viewpager
        viewPager = (ViewPager) findViewById(R.id.pager);
        //实例化pageradapter
        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mTabsPagerAdapter);
        //获取适配的actionbar
        actionBar = getSupportActionBar();
        //设置home按钮不可点击
        actionBar.setHomeButtonEnabled(false);
        //设置顶部导航的模式  -tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //添加标签
        for(String tab:tabs)
        {
            actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
        }
        //设置ViewPager切换时候的监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //页面滑动，顶部标签跟着改变
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }



    @Override
    public void onTabReselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //tab选中，切换viewpager
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }
}
