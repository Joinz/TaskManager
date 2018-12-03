package com.joinz.taskmanager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    public TabsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TasksFragment.newInstance();
        } else if (position == 1) {
            return ProductivityFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        MainActivity mainActivity = new MainActivity();
        if (position <= mainActivity.tabNames.size()) {
            return mainActivity.tabNames.get(position);
        } else {
            return null;
        }
    }
}
