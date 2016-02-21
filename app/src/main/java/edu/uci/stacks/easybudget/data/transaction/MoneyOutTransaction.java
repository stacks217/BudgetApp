package edu.uci.stacks.easybudget.data.transaction;

import java.util.Date;

public class MoneyOutTransaction extends MoneyTransaction {

    public MoneyOutTransaction(int _id, String name, int categoryId, Date date, int amount, String receiptFilePath, Date createDate) {
        super(_id, name, categoryId, date, amount, receiptFilePath, createDate);
    }

    public MoneyOutTransaction(String name, int categoryId, Date date, int amount, String receiptFilePath) {
        super(name, categoryId, date, amount, receiptFilePath);
    }

    @Override
    public int isIn() {
        return 0;
    }

}
