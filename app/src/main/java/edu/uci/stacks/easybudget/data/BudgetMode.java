package edu.uci.stacks.easybudget.data;

import edu.uci.stacks.easybudget.R;

public enum BudgetMode {
    BASIC("BASIC", R.layout.activity_edit_budget_basic), ADVANCED("ADVANCED", R.layout.activity_edit_budget_advanced);

    private final String text;
    private final int editBudgetLayoutId;

    /**
     * @param text
     */
    private BudgetMode(final String text, final int editBudgetLayoutId) {
        this.text = text;
        this.editBudgetLayoutId = editBudgetLayoutId;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

    public static BudgetMode fromString(String string) {
        if (BudgetMode.ADVANCED.toString().equals(string)) {
            return BudgetMode.ADVANCED;
        } else {
            return BudgetMode.BASIC;
        }
    }

    public int getEditBudgetLayoutId() {
        return editBudgetLayoutId;
    }
}
