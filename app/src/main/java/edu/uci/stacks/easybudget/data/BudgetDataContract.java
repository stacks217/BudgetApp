package edu.uci.stacks.easybudget.data;

import android.provider.BaseColumns;

public class BudgetDataContract {

    /* Inner class that defines the table contents for a Category */
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

    /* Inner class that defines the table contents for a transaction */
    public static abstract class MoneyTransaction implements BaseColumns {

        public static final String TABLE_NAME = "money_transaction";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_RECEIPT_FILE_PATH = "receipt_file_path";
        public static final String COLUMN_NAME_CATEGORY_ID = "categoryId";
        /* If this money is money coming in then it is 1 otherwise 0 */
        public static final String COLUMN_NAME_IN = "money_in";
        // YYYY-MM-DD
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CREATE_DATE = "create_date";
        public static final String DATE_STRING = "yyyy-MM-dd HH:mm:ss.SSS";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String[] ALL_COLUMNS = new String[]{
                _ID,
                COLUMN_NAME_CATEGORY_ID,
                COLUMN_NAME_IN,
                COLUMN_NAME_NAME,
                COLUMN_NAME_DATE,
                COLUMN_NAME_CREATE_DATE,
                COLUMN_NAME_RECEIPT_FILE_PATH,
                COLUMN_NAME_AMOUNT};
    }
}
