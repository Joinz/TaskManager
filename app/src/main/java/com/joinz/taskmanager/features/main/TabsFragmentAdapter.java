package com.joinz.taskmanager.features.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.joinz.taskmanager.features.productivity.ProductivityFragment;
import com.joinz.taskmanager.R;
import com.joinz.taskmanager.features.tasklist.TasksFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private final SparseArray<String> tabNames = new SparseArray<>();
    private Context context;
    private ProductivityFragment productivityFragment;

    public TabsFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        initTabNames();
    }

    private void initTabNames() {
        tabNames.put(0, context.getString(R.string.tl_tasks));
        tabNames.put(1, context.getString(R.string.tl_productivity));
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TasksFragment.newInstance();
        } else if (position == 1) {
            if (productivityFragment == null) {
                productivityFragment = ProductivityFragment.newInstance();
            }
            return productivityFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabNames.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
