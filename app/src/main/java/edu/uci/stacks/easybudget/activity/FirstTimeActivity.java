package edu.uci.stacks.easybudget.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetConfig;
import edu.uci.stacks.easybudget.data.BudgetMode;

public class FirstTimeActivity extends Activity {

    public static final String IS_FTUE_INTENT_KEY = "isFtue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftue);
    }

    public void onFtueChoice(View view) {
        BudgetConfig config = new BudgetConfig(this);
        if (view.getId() == R.id.btn_ftue_advanced) {
            // Advanced mode selected
            config.setBudgetMode(BudgetMode.ADVANCED);
        } else {
            // Basic mode selected
            config.setBudgetMode(BudgetMode.BASIC);
        }
        Intent i = new Intent(this, EditBudgetActivity.class);
        i.putExtra(IS_FTUE_INTENT_KEY, true);
        startActivity(i);
        finish();
    }
}
