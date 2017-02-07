package com.fxly.monkeytest.settings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fxly.monkeytest.settings.fragment.EmailSenttingFragment;
import com.fxly.monkeytest.settings.fragment.AppListFragment;
import com.fxly.monkeytest.settings.fragment.ParametersSettingsFragment;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new ParametersSettingsFragment();

            case 1:
                return new EmailSenttingFragment();
            case 2:
                return new AppListFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

}
