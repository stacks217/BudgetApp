package edu.uci.stacks.easybudget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetConfig;

public class EditBudgetActivity extends AppCompatActivity {

    private BudgetConfig budgetConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetConfig = new BudgetConfig(this);
        setContentView(budgetConfig.getBudgetMode().getEditBudgetLayoutId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!getIntent().getBooleanExtra(FirstTimeActivity.IS_FTUE_INTENT_KEY, false)) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        EditText amountEditText = (EditText) findViewById(R.id.budget_amount_edit);
        amountEditText.setText("" + budgetConfig.getBudgetAmount());
    }

    public void finished(View view) {
        EditText amountEditText = (EditText) findViewById(R.id.budget_amount_edit);
        budgetConfig.setBudgetAmount(Float.parseFloat(amountEditText.getText().toString()));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
