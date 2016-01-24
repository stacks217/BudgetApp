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

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.data.category.CategoryData;
import edu.uci.stacks.easybudget.util.DisplayUtil;

public class MonthlyViewFragment extends Fragment {

    public static final String MONTH_OFFSET_KEY = "MONTH_OFFSET_KEY";
    @Inject
    CategoryData categoryData;
    private View root;
    private Date date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        BudgetApplication.getComponent().inject(this);
        root =  inflater.inflate(R.layout.fragment_month_view, container, false);

        Calendar calendar = Calendar.getInstance();
        int totalMonthOffset = getArguments().getInt(MONTH_OFFSET_KEY);
        int yearsOffset = totalMonthOffset/12;
        int monthOffset = totalMonthOffset%12;
        calendar.set(calendar.get(Calendar.YEAR) + yearsOffset,
                calendar.get(Calendar.MONTH) + monthOffset,
                calendar.get(Calendar.DAY_OF_MONTH));
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
        Cursor c = categoryData.getCategoriesCursorWithSumByMonth(date);

        TableRow row = (TableRow) inflater.inflate(R.layout.month_view_text_cell, tableView, false);

        TextView catName = (TextView) row.findViewById(R.id.cell_cat_name);
        TextView sumAmt = (TextView) row.findViewById(R.id.cell_sum_amt);
        TextView totalAmt = (TextView) row.findViewById(R.id.cell_total_amt);
        catName.setText(R.string.category_table_header);
        sumAmt.setText(R.string.sum_table_header);
        totalAmt.setText(R.string.total_table_header);
        tableView.addView(row);

        int rows = c.getCount();
        c.moveToFirst();
        for (int i = 0; i < rows; i++) {
            row = (TableRow) inflater.inflate(R.layout.month_view_text_cell, tableView, false);

            catName = (TextView) row.findViewById(R.id.cell_cat_name);
            sumAmt = (TextView) row.findViewById(R.id.cell_sum_amt);
            totalAmt = (TextView) row.findViewById(R.id.cell_total_amt);

            catName.setText(c.getString(c.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_NAME)));
            int sum = c.getInt(c.getColumnIndex("sum"));
            sumAmt.setText(DisplayUtil.formatToCurrencyFromCents(sum));
            int total = c.getInt(c.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_AMOUNT));
            totalAmt.setText(DisplayUtil.formatToCurrencyFromCents(total));

            if (sum > total) {
                totalAmt.setTextColor(getResources().getColor(R.color.red));
            }
            c.moveToNext();
            tableView.addView(row);

        }
        c.close();
    }

}
