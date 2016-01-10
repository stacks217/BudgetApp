package edu.uci.stacks.easybudget.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class BudgetConfig {

    private static final String BUDGET_AMOUNT_KEY = "BUDGET_AMOUNT_KEY";
    private final String MODE_KEY = "MODE_KEY";
    private final SharedPreferences sharedPrefs;

    public BudgetConfig(Context context) {
        this.sharedPrefs = context.getSharedPreferences("BudgetConfig", Context.MODE_PRIVATE);
    }

    public void setBudgetMode(BudgetMode mode) {
        sharedPrefs.edit().putString(MODE_KEY, mode.toString()).apply();
    }

    public BudgetMode getBudgetMode() {
        return BudgetMode.fromString(sharedPrefs.getString(MODE_KEY, ""));
    }

    public boolean isFtue() {
        return TextUtils.isEmpty(sharedPrefs.getString(MODE_KEY, ""));
    }

    public void setBudgetAmount(float amount) {
        sharedPrefs.edit().putFloat(BUDGET_AMOUNT_KEY, amount).apply();
    }

    public float getBudgetAmount() {
        return sharedPrefs.getFloat(BUDGET_AMOUNT_KEY, 0);
    }
}
