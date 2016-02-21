package edu.uci.stacks.easybudget.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;

public class MonthlyChartFragment extends MonthlyFragmentBase {

    public static final String TAG = "MonthlyChartFragment";
    private View root;
    private PieChart categoryPieChart;

    public static Fragment getInstance(int position) {
        MonthlyViewFragment monthlyViewFragment = new MonthlyViewFragment();
        Bundle args = new Bundle();
        args.putInt(MONTH_OFFSET_KEY, position);
        monthlyViewFragment.setArguments(args);
        return monthlyViewFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        BudgetApplication.getComponent().inject(this);
        root =  inflater.inflate(R.layout.fragment_month_chart, container, false);
        categoryPieChart = (PieChart) root.findViewById(R.id.pie_chart_categories);
        categoryPieChart.setUsePercentValues(true);
        return root;
    }

    public void updatePieChart(List<String> categoryNames, List<Entry> categoryEntries) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // create pie data set
        PieDataSet dataSet = new PieDataSet(categoryEntries, "Categories");
        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        dataSet.setValueTextSize(15f);

        // instantiate pie data object now
        PieData data = new PieData(categoryNames, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.GRAY);

        categoryPieChart.setData(data);

        // undo all highlights
        categoryPieChart.highlightValues(null);

        // update pie chart
        categoryPieChart.invalidate();

        categoryPieChart.notifyDataSetChanged();
    }
}
