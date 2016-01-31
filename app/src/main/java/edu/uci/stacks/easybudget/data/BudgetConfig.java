package edu.uci.stacks.easybudget.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.List;

import edu.uci.stacks.easybudget.data.transaction.MoneyTransaction;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionData;

public class BudgetConfig {

    private static final String BUDGET_AMOUNT_KEY = "BUDGET_AMOUNT_KEY";
    private final String MODE_KEY = "MODE_KEY";
    public static final String NUM_OF_DAYS_REMINDER_KEY = "NUM_OF_DAYS_REMINDER_KEY";
    private final String DID_REMIND_KEY = "DID_REMIND_KEY";
    private final SharedPreferences sharedPrefs;
    private final MoneyTransactionData transactionData;
    private final Context context;

    public BudgetConfig(Context context, MoneyTransactionData transactionData) {
        this.sharedPrefs = context.getSharedPreferences("BudgetConfig", Context.MODE_PRIVATE);
        this.context = context;
        this.transactionData = transactionData;
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

    public boolean shouldRemindNoEntries() {
        boolean result = false;
        if (!sharedPrefs.getBoolean(DID_REMIND_KEY, false) ){
            int numOfDays = getReminderNumDays();
            if (numOfDays > 0) {
                List<MoneyTransaction> transactions = transactionData.getLastTransactions(1);
                if (transactions.size() > 0) {
                    MoneyTransaction transaction = transactions.get(0);
                    long offset = System.currentTimeMillis() - transaction.getCreateDate().getTime();
                    if (offset > (numOfDays * 24 * 60 * 60 * 1000)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public int getReminderNumDays() {
        return Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(NUM_OF_DAYS_REMINDER_KEY, "5"));
    }

    public void setReminded() {
        sharedPrefs.edit().putBoolean(DID_REMIND_KEY, true).apply();
    }

    public void clearReminded() {
        sharedPrefs.edit().putBoolean(DID_REMIND_KEY, false).apply();
    }
}
