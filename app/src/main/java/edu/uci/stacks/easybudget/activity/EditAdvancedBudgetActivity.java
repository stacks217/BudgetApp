package edu.uci.stacks.easybudget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetConfig;
import edu.uci.stacks.easybudget.data.BudgetMode;
import edu.uci.stacks.easybudget.data.category.CategoryAdapter;
import edu.uci.stacks.easybudget.data.category.CategoryData;

public class EditAdvancedBudgetActivity extends BudgetActivity {

    @Inject
    BudgetConfig budgetConfig;

    @Inject
    CategoryData categoryData;
    private TextView totalMonthlyAmount;

    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BudgetApplication.getComponent().inject(this);
        setContentView(R.layout.activity_edit_budget_advanced);
        setupToolbar();

        totalMonthlyAmount = (TextView) findViewById(R.id.total_monthly_budget_amount);
        totalMonthlyAmount.setText(categoryData.getTotalAmountString());

        mRecyclerView = (RecyclerView) findViewById(R.id.category_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CategoryAdapter(categoryData);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onResume() {
        super.onResume();
        mAdapter.update();
        totalMonthlyAmount.setText(categoryData.getTotalAmountString());
    }

    public void finished(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public void changeToBasic(View view) {
        budgetConfig.setBudgetMode(BudgetMode.BASIC);
        startActivity(new Intent(this, EditBasicBudgetActivity.class));
        finish();
    }
}
