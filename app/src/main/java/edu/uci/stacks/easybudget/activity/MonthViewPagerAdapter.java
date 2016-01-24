package edu.uci.stacks.easybudget.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.uci.stacks.easybudget.activity.fragment.MonthlyViewFragment;

public class MonthViewPagerAdapter extends FragmentStatePagerAdapter {
    public MonthViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        MonthlyViewFragment monthlyViewFragment = new MonthlyViewFragment();
        Bundle args = new Bundle();
        args.putInt(MonthlyViewFragment.MONTH_OFFSET_KEY, position);
        monthlyViewFragment.setArguments(args);
        return monthlyViewFragment;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
