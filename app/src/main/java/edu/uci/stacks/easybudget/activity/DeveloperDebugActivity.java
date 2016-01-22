package edu.uci.stacks.easybudget.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import edu.uci.stacks.easybudget.R;

public class DeveloperDebugActivity extends BudgetActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_debug);
    }

    public void clearBudgetMode(View view) {
        SharedPreferences prefs = getSharedPreferences("BudgetConfig", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
        finish();
    }
}
