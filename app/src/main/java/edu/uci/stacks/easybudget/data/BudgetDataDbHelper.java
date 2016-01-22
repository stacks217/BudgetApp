package edu.uci.stacks.easybudget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BudgetDataDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BudgetData.db";


    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BudgetDataContract.Category.TABLE_NAME + " (" +
                    BudgetDataContract.Category._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    BudgetDataContract.Category.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    BudgetDataContract.Category.COLUMN_NAME_AMOUNT + INTEGER_TYPE + COMMA_SEP +
                    BudgetDataContract.Category.COLUMN_NAME_RECURRING + INTEGER_TYPE + COMMA_SEP +
                    BudgetDataContract.Category.COLUMN_NAME_REMINDER + INTEGER_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BudgetDataContract.Category.TABLE_NAME;

    public BudgetDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
