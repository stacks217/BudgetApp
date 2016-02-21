package edu.uci.stacks.easybudget.activity.fragment;

import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.data.category.CategoryData;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionData;
import edu.uci.stacks.easybudget.util.DisplayUtil;

public class MonthlyViewFragment extends MonthlyFragmentBase {

    @Inject
    CategoryData categoryData;
    @Inject
    MoneyTransactionData transactionData;
    private View root;
    private Date date;

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
        root =  inflater.inflate(R.layout.fragment_month_view, container, false);
        Calendar calendar = getCalendarForThisMonth();
        date = calendar.getTime();
        TextView monthTextView = (TextView) root.findViewById(R.id.current_month);
        Resources res = getResources();
        String[] months = res.getStringArray(R.array.months_array);
        monthTextView.setText(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR));
        return root;
    }

    public void onResume() {
        super.onResume();
        buildTable((ViewGroup) root.findViewById(R.id.tableLayout));
    }

    private void buildTable(ViewGroup tableView) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        tableView.removeAllViews();


        int monthTotal = transactionData.getSumByMonth(date);
        int setCategoriesSum = 0;

        Cursor c = categoryData.getCategoriesCursorWithSumByMonth(date);

        TableRow row = (TableRow) inflater.inflate(R.layout.month_view_text_cell, tableView, false);

        TextView catName = (TextView) row.findViewById(R.id.cell_cat_name);
        TextView sumAmt = (TextView) row.findViewById(R.id.cell_sum_amt);
        TextView totalAmt = (TextView) row.findViewById(R.id.cell_total_amt);
        catName.setText(R.string.category_table_header);
        sumAmt.setText(R.string.sum_table_header);
        totalAmt.setText(R.string.budget_table_header);
        tableView.addView(row);

        int rows = c.getCount();
        List<Entry> categoryEntries = new ArrayList<Entry>(rows+1);
        List<String> categorNames = new ArrayList<String>(rows+1);
        c.moveToFirst();
        for (int i = 0; i < rows; i++) {
            row = (TableRow) inflater.inflate(R.layout.month_view_text_cell, tableView, false);

            catName = (TextView) row.findViewById(R.id.cell_cat_name);
            sumAmt = (TextView) row.findViewById(R.id.cell_sum_amt);
            totalAmt = (TextView) row.findViewById(R.id.cell_total_amt);

            String categoryName = c.getString(c.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_NAME));
            catName.setText(categoryName);
            int sum = c.getInt(c.getColumnIndex("sum"));
            setCategoriesSum += sum;
            sumAmt.setText(DisplayUtil.formatToCurrencyFromCents(sum));
            int total = c.getInt(c.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_AMOUNT));
            totalAmt.setText(DisplayUtil.formatToCurrencyFromCents(total));

            if (sum > total) {
                totalAmt.setTextColor(getResources().getColor(R.color.red));
            }
            c.moveToNext();
            tableView.addView(row);
            categoryEntries.add(new Entry(sum, i));
            categorNames.add(categoryName);
        }
        c.close();

        // 'Other' category
        row = (TableRow) inflater.inflate(R.layout.month_view_text_cell, tableView, false);
        catName = (TextView) row.findViewById(R.id.cell_cat_name);
        sumAmt = (TextView) row.findViewById(R.id.cell_sum_amt);
        totalAmt = (TextView) row.findViewById(R.id.cell_total_amt);

        int otherSum = monthTotal - setCategoriesSum;
        catName.setText(R.string.category_default_label);
        sumAmt.setText(DisplayUtil.formatToCurrencyFromCents(otherSum));
        totalAmt.setText(DisplayUtil.formatToCurrencyFromCents(0));
        if (otherSum > 0) {
            totalAmt.setTextColor(getResources().getColor(R.color.red));
        }

        tableView.addView(row);
        categoryEntries.add(new Entry(otherSum, rows));
        categorNames.add(getResources().getString(R.string.category_default_label));

        ((TextView)root.findViewById(R.id.current_month_total)).setText(DisplayUtil.formatToCurrencyFromCents(monthTotal));

        MonthlyChartFragment frag = (MonthlyChartFragment) getChildFragmentManager().findFragmentByTag(MonthlyChartFragment.TAG);
        if (frag != null) {
            frag.updatePieChart(categorNames, categoryEntries);
        }
    }

}
