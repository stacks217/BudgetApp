package edu.uci.stacks.easybudget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetConfig;
import edu.uci.stacks.easybudget.data.BudgetMode;

public class FirstTimeActivity extends BudgetActivity {

    @Inject
    BudgetConfig budgetConfig;

    public static final String IS_FTUE_INTENT_KEY = "isFtue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BudgetApplication.getComponent().inject(this);
        setContentView(R.layout.activity_ftue);

    }

    public void onFtueChoice(View view) {
        Intent i = new Intent();
        if (view.getId() == R.id.btn_ftue_advanced) {
            // Advanced mode selected
            i.setClass(this, EditAdvancedBudgetActivity.class);
            budgetConfig.setBudgetMode(BudgetMode.ADVANCED);
        } else {
            // Basic mode selected
            i.setClass(this, EditBasicBudgetActivity.class);
            budgetConfig.setBudgetMode(BudgetMode.BASIC);
        }
        i.putExtra(IS_FTUE_INTENT_KEY, true);
        startActivity(i);
        finish();
    }
}
