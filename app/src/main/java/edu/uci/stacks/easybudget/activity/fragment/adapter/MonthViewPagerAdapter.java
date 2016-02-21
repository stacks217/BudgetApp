package edu.uci.stacks.easybudget.activity.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.uci.stacks.easybudget.activity.fragment.MonthlyViewFragment;

public class MonthViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int MAX_ADAPATER_SIZE = 10;

    public MonthViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MonthlyViewFragment.getInstance(position - (MAX_ADAPATER_SIZE/2));
    }

    @Override
    public int getCount() {
        return MAX_ADAPATER_SIZE;
    }
}
