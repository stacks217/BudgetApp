package edu.uci.stacks.easybudget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetConfig;
import edu.uci.stacks.easybudget.data.BudgetMode;

public class EditBasicBudgetActivity extends BudgetActivity {

    @Inject
    BudgetConfig budgetConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BudgetApplication.getComponent().inject(this);
        setContentView(R.layout.activity_edit_budget_basic);

        setupToolbar();
        EditText amountEditText = (EditText) findViewById(R.id.budget_amount_edit);
        amountEditText.setText("" + budgetConfig.getBudgetAmount());
    }

    public void finished(View view) {
        EditText amountEditText = (EditText) findViewById(R.id.budget_amount_edit);
        budgetConfig.setBudgetAmount(Float.parseFloat(amountEditText.getText().toString()));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void changeToAdvanced(View view) {
        budgetConfig.setBudgetMode(BudgetMode.ADVANCED);
        startActivity(new Intent(this, EditAdvancedBudgetActivity.class));
        finish();
    }
}
