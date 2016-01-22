package edu.uci.stacks.easybudget.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.uci.stacks.easybudget.R;

public class BudgetActivity extends AppCompatActivity {

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!getIntent().getBooleanExtra(FirstTimeActivity.IS_FTUE_INTENT_KEY, false)) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
