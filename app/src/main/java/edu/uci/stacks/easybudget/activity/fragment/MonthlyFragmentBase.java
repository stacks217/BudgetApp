package edu.uci.stacks.easybudget.activity.fragment;

import android.support.v4.app.Fragment;

import java.util.Calendar;

public class MonthlyFragmentBase extends Fragment {

    public static final String MONTH_OFFSET_KEY = "MONTH_OFFSET_KEY";

    protected Calendar getCalendarForThisMonth() {
        Calendar calendar = Calendar.getInstance();
        int totalMonthOffset = getArguments().getInt(MONTH_OFFSET_KEY);
        int yearsOffset = totalMonthOffset/12;
        int monthOffset = totalMonthOffset%12;
        calendar.set(calendar.get(Calendar.YEAR) + yearsOffset,
                calendar.get(Calendar.MONTH) + monthOffset,
                calendar.get(Calendar.DAY_OF_MONTH));
        return calendar;
    }
}
