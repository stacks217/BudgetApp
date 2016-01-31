package edu.uci.stacks.easybudget.data.transaction;

import java.util.Date;

public class MoneyInTransaction extends MoneyTransaction {

    public MoneyInTransaction(int _id, String name, int categoryId, Date date, int amount, Date createDate) {
        super(_id, name, categoryId, date, amount, createDate);
    }

    public MoneyInTransaction(String name, int categoryId, Date date, int amount) {
        super(name, categoryId, date, amount);
    }

    @Override
    public int isIn() {
        return 1;
    }

}
