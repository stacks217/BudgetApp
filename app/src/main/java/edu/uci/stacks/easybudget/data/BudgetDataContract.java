package edu.uci.stacks.easybudget.data;

import android.provider.BaseColumns;

public class BudgetDataContract {

    /* Inner class that defines the table contents */
    public static abstract class Category implements BaseColumns {

        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_RECURRING = "recurring";
        public static final String COLUMN_NAME_REMINDER = "reminder";
        public static final String[] ALL_COLUMNS = new String[]{
                _ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_AMOUNT,
                COLUMN_NAME_RECURRING,
                COLUMN_NAME_REMINDER};
    }
}
