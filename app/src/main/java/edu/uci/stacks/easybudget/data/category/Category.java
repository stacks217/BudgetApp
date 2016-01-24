package edu.uci.stacks.easybudget.data.category;

import edu.uci.stacks.easybudget.util.DisplayUtil;

public class Category {
    private final int _id;
    private final String name;
    private final int amount;

    public Category(String name, int amount) {
        this(-1, name, amount);
    }

    public Category(int _id, String name, int amount) {
        this._id = _id;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getInputDisplayAmount() {
        return DisplayUtil.formatToCurrencyFromCents(amount);
    }
}
