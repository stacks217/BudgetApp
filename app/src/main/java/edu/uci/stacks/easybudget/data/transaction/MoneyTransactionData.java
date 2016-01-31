package edu.uci.stacks.easybudget.data.transaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.data.BudgetDataDbHelper;

public class MoneyTransactionData {
    private final BudgetDataDbHelper budgetDataDbHelper;
    private final SQLiteDatabase db;

    @Inject
    public MoneyTransactionData(BudgetDataDbHelper budgetDataDbHelper) {
        this.budgetDataDbHelper = budgetDataDbHelper;
        db = budgetDataDbHelper.getWritableDatabase();
    }

    public Cursor getMoneyTransactionsCursor() {
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor c = db.query(BudgetDataContract.MoneyTransaction.TABLE_NAME,
                BudgetDataContract.MoneyTransaction.ALL_COLUMNS,
                null, null, null, null, null);
        return c;
    }

    public MoneyTransaction getMoneyTransaction(int id) {
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor c = db.query(BudgetDataContract.MoneyTransaction.TABLE_NAME,
                BudgetDataContract.MoneyTransaction.ALL_COLUMNS,
                "_id = ?", new String[]{id+""}, null, null, null);
        MoneyTransaction moneyTransaction;
        if (c.getCount() > 0 && c.moveToFirst()) {
            moneyTransaction = getMoneyTransactionFromCursor(c);
        } else {
            moneyTransaction = null;
        }

        c.close();
        return moneyTransaction;
    }

    @Nullable
    private MoneyTransaction getMoneyTransactionFromCursor(Cursor c) {
        MoneyTransaction moneyTransaction;
        int _id = c.getInt(c.getColumnIndex(BudgetDataContract.MoneyTransaction._ID));
        String name = c.getString(c.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_NAME));
        int categoryId = c.getInt(c.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_CATEGORY_ID));
        int in = c.getInt(c.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_IN));
        int amount = c.getInt(c.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_AMOUNT));
        String dateString = c.getString(c.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_DATE));
        Date date = null;
        try {
             date = new SimpleDateFormat(BudgetDataContract.MoneyTransaction.DATE_STRING).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String createDateString = c.getString(c.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_CREATE_DATE));
        Date createDate = null;
        try {
            createDate = new SimpleDateFormat(BudgetDataContract.MoneyTransaction.DATE_STRING).parse(createDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (in == 0) {
            moneyTransaction = new MoneyOutTransaction(_id, name, categoryId, date, amount, createDate);
        } else {
            moneyTransaction = new MoneyInTransaction(_id, name, categoryId, date, amount, createDate);
        }
        return moneyTransaction;
    }

    public void updateMoneyTransaction(MoneyTransaction tx) {
        // public int update (String table, ContentValues values, String whereClause, String[] whereArgs)
        ContentValues cv = new ContentValues();
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_NAME, tx.getName());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_CATEGORY_ID, tx.getCategoryId());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_IN, tx.isIn());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_DATE, tx.getDateString());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_AMOUNT, tx.getAmount());
        db.update(BudgetDataContract.MoneyTransaction.TABLE_NAME, cv, "_id = ?", new String[]{tx.getId() + ""});
    }

    public void addMoneyTransaction(MoneyTransaction tx) {
        // public int update (String table, ContentValues values, String whereClause, String[] whereArgs)
        ContentValues cv = new ContentValues();
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_NAME, tx.getName());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_CATEGORY_ID, tx.getCategoryId());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_IN, tx.isIn());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_DATE, tx.getDateString());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_AMOUNT, tx.getAmount());
        cv.put(BudgetDataContract.MoneyTransaction.COLUMN_NAME_CREATE_DATE, tx.getCreateDateString());
        db.insert(BudgetDataContract.MoneyTransaction.TABLE_NAME, null, cv);
    }

    public void removeMoneyTransactionById(int id) {
        // public int delete (String table, String whereClause, String[] whereArgs)
        db.delete(BudgetDataContract.MoneyTransaction.TABLE_NAME, "_id = ?", new String[]{id + ""});
    }

    public List<MoneyTransaction> getLastTransactions(int i) {
        // public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor c = db.query(BudgetDataContract.MoneyTransaction.TABLE_NAME,
                BudgetDataContract.MoneyTransaction.ALL_COLUMNS,
                null, null, null, null, BudgetDataContract.MoneyTransaction.COLUMN_NAME_CREATE_DATE + " DESC", String.valueOf(i));
        List<MoneyTransaction> transactions = new ArrayList<MoneyTransaction>();
        if (c.getCount() > 0 && c.moveToFirst()) {
            do {
                transactions.add(getMoneyTransactionFromCursor(c));
            } while (!c.isLast() && c.moveToNext());
        }
        c.close();
        return transactions;
    }
}
