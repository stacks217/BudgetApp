package edu.uci.stacks.easybudget.data;

public enum BudgetMode {
    BASIC("BASIC"), ADVANCED("ADVANCED");

    private final String text;

    /**
     * @param text
     */
    private BudgetMode(final String text) {
        this.text = text;
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

}
