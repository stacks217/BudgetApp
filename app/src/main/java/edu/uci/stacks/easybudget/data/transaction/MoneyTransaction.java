package edu.uci.stacks.easybudget.data.transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.util.DisplayUtil;

abstract public class MoneyTransaction {
    private final int _id;
    private final String name;
    private final int categoryId;
    private final int amount;
    private final Date date;

    public MoneyTransaction(String name, int categoryId, Date date, int amount) {
        this(-1, name, categoryId, date, amount);
    }

    public MoneyTransaction(int _id, String name, int categoryId, Date date, int amount) {
        this._id = _id;
        this.name = name;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return _id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getInputDisplayAmount() {
        return DisplayUtil.formatToCurrencyFromCents(amount);
    }

    abstract public int isIn();

    public String getDateString() {
        return new SimpleDateFormat(BudgetDataContract.MoneyTransaction.DATE_STRING).format(date).toString();
    }
}
