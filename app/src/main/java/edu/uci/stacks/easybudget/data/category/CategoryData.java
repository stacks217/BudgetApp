package edu.uci.stacks.easybudget.data.category;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.data.BudgetDataDbHelper;

public class CategoryData {

    private final BudgetDataDbHelper budgetDataDbHelper;
    private final SQLiteDatabase db;

    @Inject
    public CategoryData(BudgetDataDbHelper budgetDataDbHelper) {
        this.budgetDataDbHelper = budgetDataDbHelper;
        db = budgetDataDbHelper.getWritableDatabase();
    }

    public String getTotalAmountString() {
        Cursor cur = db.rawQuery("SELECT SUM(" + BudgetDataContract.Category.COLUMN_NAME_AMOUNT + ") FROM " + BudgetDataContract.Category.TABLE_NAME, null);
        if(cur.moveToFirst())
        {
            return String.format("$%.2f", cur.getInt(0)/100.0);
        }
        return "$0.00";
    }

    public Cursor getCategoriesCursor() {
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor c = db.query(BudgetDataContract.Category.TABLE_NAME,
                BudgetDataContract.Category.ALL_COLUMNS,
                null, null, null, null, null);
        return c;
    }

    public Cursor getCategoriesCursorWithSumByMonth(Date date) {
        String monthStr = new SimpleDateFormat("MM").format(date);
        String yearStr = new SimpleDateFormat("yyyy").format(date);
        Cursor c = db.rawQuery("SELECT _id, name, amount, " +
                "(SELECT SUM(amount) " +
                "FROM money_transaction " +
                "WHERE money_transaction.categoryId = category._id AND " +
                "strftime('%m', date) = '" + monthStr + "' AND " +
                "strftime('%Y', date) = '" + yearStr + "' " +
                ") AS sum " +
                "FROM category", null);
        // strftime('%m', transactionDate) = '05'
        return c;
    }

    public Category getCategory(int id) {
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor c = db.query(BudgetDataContract.Category.TABLE_NAME,
                BudgetDataContract.Category.ALL_COLUMNS,
                "_id = ?", new String[]{id+""}, null, null, null);
        Category category;
        if (c.getCount() > 0 && c.moveToFirst()) {
            int _id = c.getInt(c.getColumnIndex(BudgetDataContract.Category._ID));
            String name = c.getString(c.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_NAME));
            int amount = c.getInt(c.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_AMOUNT));
            category = new Category(_id, name, amount);
        } else {
            category = null;
        }

        c.close();
        return category;
    }

    public void updateCategory(Category category) {
        // public int update (String table, ContentValues values, String whereClause, String[] whereArgs)
        ContentValues cv = new ContentValues();
        cv.put(BudgetDataContract.Category.COLUMN_NAME_NAME, category.getName());
        cv.put(BudgetDataContract.Category.COLUMN_NAME_AMOUNT, category.getAmount());
        db.update(BudgetDataContract.Category.TABLE_NAME, cv, "_id = ?", new String[]{category.getId() + ""});
    }

    public void addCategory(Category category) {
        // public int update (String table, ContentValues values, String whereClause, String[] whereArgs)
        ContentValues cv = new ContentValues();
        cv.put(BudgetDataContract.Category.COLUMN_NAME_NAME, category.getName());
        cv.put(BudgetDataContract.Category.COLUMN_NAME_AMOUNT, category.getAmount());
        db.insert(BudgetDataContract.Category.TABLE_NAME, null, cv);
    }

    public void removeCategoryById(int id) {
        // public int delete (String table, String whereClause, String[] whereArgs)
        db.delete(BudgetDataContract.Category.TABLE_NAME, "_id = ?", new String[]{id + ""});
    }
}
