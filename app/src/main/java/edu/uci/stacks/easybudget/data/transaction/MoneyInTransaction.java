package edu.uci.stacks.easybudget.data.transaction;

import java.util.Date;

public class MoneyInTransaction extends MoneyTransaction {

    public MoneyInTransaction(int _id, String name, int categoryId, Date date, int amount, String receiptFilePath, Date createDate)  {
            super(_id, name, categoryId, date, amount, receiptFilePath, createDate);
    }

    public MoneyInTransaction(String name, int categoryId, Date date, int amount, String receiptFilePath) {
        super(name, categoryId, date, amount, receiptFilePath);
    }

    @Override
    public int isIn() {
        return 1;
    }

}
