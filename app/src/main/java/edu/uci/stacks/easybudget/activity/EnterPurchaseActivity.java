package edu.uci.stacks.easybudget.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.activity.fragment.DatePickerFragment;
import edu.uci.stacks.easybudget.data.category.Category;
import edu.uci.stacks.easybudget.data.category.CategoryData;
import edu.uci.stacks.easybudget.data.category.CategorySpinnerAdapater;
import edu.uci.stacks.easybudget.data.transaction.MoneyOutTransaction;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransaction;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionData;

public class EnterPurchaseActivity extends BudgetActivity
        implements DatePickerDialog.OnDateSetListener  {

    @Inject
    CategoryData categoryData;

    @Inject
    MoneyTransactionData moneyTransactionData;

    public int moneyTransactionId = -1;
    private EditText editAmount;
    private Spinner categorySpinner;
    private CategorySpinnerAdapater adapter;
    private int year;
    private int month;
    private int day;
    private Button datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BudgetApplication.getComponent().inject(this);
        MoneyTransaction moneyTransaction = null;
        if (getIntent().hasExtra("money_transaction_id")) {
            moneyTransactionId = getIntent().getIntExtra("money_transaction_id", -1);
            moneyTransaction = moneyTransactionData.getMoneyTransaction(moneyTransactionId);
        }
        setContentView(R.layout.activity_enter_purchase);
        setupToolbar();

        editAmount = (EditText) findViewById(R.id.input_amount);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        adapter = new CategorySpinnerAdapater(this, categoryData.getCategoriesCursor(), true);
        categorySpinner.setAdapter(adapter);

        if (moneyTransaction != null) {
            Category category = categoryData.getCategory(moneyTransaction.getCategoryId());
            if (category != null) {
                int spinnerPosition = adapter.getPosition(category.getId());
                if (spinnerPosition != -1) {
                    categorySpinner.setSelection(spinnerPosition);
                }
            }
        }

        if (savedInstanceState != null) {
            year = savedInstanceState.getInt("year");
            month = savedInstanceState.getInt("month");
            day = savedInstanceState.getInt("day");
        } else {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        datePicker = (Button) findViewById(R.id.date_picker);
        setDatePickerButtonText();
    }

    private void setDatePickerButtonText() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String dateButtonText = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()).toString();
        datePicker.setText(dateButtonText);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void finished(View view) {
        // save purchase
        String name = "";
        String amountText = editAmount.getText().toString();
        if (TextUtils.isEmpty(amountText)) {
            amountText = "0.0";
        }
        int amount = (int)(Double.parseDouble(amountText)*100);
        int categoryId = adapter.getCategoryId(categorySpinner.getSelectedItemPosition());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        if (moneyTransactionId != -1) {
            moneyTransactionData.updateMoneyTransaction(new MoneyOutTransaction(moneyTransactionId, name, categoryId, date, amount, new Date()));
        } else {
            moneyTransactionData.addMoneyTransaction(new MoneyOutTransaction(name, categoryId, date, amount));
        }
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        setDatePickerButtonText();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("year", year);
        savedInstanceState.putInt("month", month);
        savedInstanceState.putInt("day", day);

        super.onSaveInstanceState(savedInstanceState);
    }
}
