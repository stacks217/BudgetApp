package edu.uci.stacks.easybudget.data.transaction;

import java.util.Date;

public class MoneyOutTransaction extends MoneyTransaction {

    public MoneyOutTransaction(int _id, String name, int categoryId, Date date, int amount) {
        super(_id, name, categoryId, date, amount);
    }

    public MoneyOutTransaction(String name, int categoryId, Date date, int amount) {
        super(name, categoryId, date, amount);
    }

    @Override
    public int isIn() {
        return 0;
    }

}